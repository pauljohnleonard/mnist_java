/*
*    FeedForwardNetBP  --  Feed forward type network with back propagation learning.
*
*    Copyright (C) 1999-2002 by Joseph A. Huwaldt <jhuwaldt@knology.net>.  All rights reserved.
*
*    This library is free software; you can redistribute it and/or
*    modify it under the terms of the GNU Library General Public
*    License as published by the Free Software Foundation; either
*    version 2 of the License, or (at your option) any later version.
*
*    This library is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
*    Library General Public License for more details.
*/
package jahuwaldt.tools.NeuralNets;


/**
*  Feed Forward type network with standard back propagation learning (without momentum).
*  This training method is not robust and is famous for it's sluggishness, but it is also
*  very simple and easy to understand.  That is why it is included here.
*
*  <p>  Modified by:  Joseph A. Huwaldt    </p>
*
*  @author    Joseph A. Huwaldt    Date:  May 30, 1999
*  @version   August 10, 2002
**/
public class FeedForwardNetBP extends FeedForwardNet {

	// Debug flag
	private static final  boolean DEBUG = false;

	// Learning factor.
	protected  float learningFactor =.8F;

	
	/**
	*  Constructor for a feed forward network with back propagation
	*  learning where the weights are initially set to random values
	*  (with a gaussian distribution between -5 and +5).
	*
	*  @param  numInputs   Number of input nodes or neurons.
	*  @param  numOutputs  Number of output nodes.
	*  @param  numHLayers  Number of hidden layers in the network.
	*  @param  neuronsPerHLayer  Number of neurons per hidden layer.
	*  @param  factory     Factory object used to generate neurons of
	*                      a user specified type.
	**/
	public FeedForwardNetBP( int numInputs, int numOutputs, int numHLayers, 
									int neuronsPerHLayer, NeuronFactory factory ) {
		super( numInputs, numOutputs, numHLayers, neuronsPerHLayer, factory );
	}

	/**
	*  Return the current value of the learning factor used for
	*  network training.  For best results, the learning factor
	*  should change with increasing iterations.  The train()
	*  routines below make not attempt to do this as there is no
	*  general rule for how it should be done.
	*
	*  @return  The current value of the learning factor.
	**/
	public float getLearningFactor() {
		return learningFactor;
	}

	/**
	*  Sets the current value of the learning factor used for
	*  network training.  For best results, the learning factor
	*  should change with increasing iterations.  The train()
	*  routines below make not attempt to do this as there is no
	*  general rule for how it should be done.
	*
	*  @param  factor  The value to set the learning factor to.
	**/
	public void setLearningFactor( float factor ) {
		learningFactor = factor;
	}

	/**
	*  Train a network to match a set of input patterns to a set
	*  of output patterns. Uses the classic method of
	*  backpropogation (without momentum) for learning.
	*
	*  @param  testGenerator   Training instance generator that
	*          generates combinations of inputs and corresponding
	*          outputs to train the network to reproduce.
	*  @param  iterations  Number of training iterations to run
	*          (number of training instances).
	*  @return The average squared error accross the output
	*          nodes after "iteration" training iterations.
	**/
	public float train( TrInstGenerator testGenerator, int iterations ) {
		
		// Loop over all the training iterations, using a new training instance for each.
		for ( int i = 0; i < iterations; ++i ) {
			testGenerator.nextInstance();
			float[] tInputs = testGenerator.getTestInputs();
			float[] tOutputs = testGenerator.getTestOutputs();
			setInputs( tInputs );
			backpropagate( tOutputs );
		}
		
		// Return the overall average squared error.
		return MSError( testGenerator );
	}

	/**
	*  Train a network to match a set of input patterns to a set
	*  of output patterns.  Uses the classic method of
	*  backpropagation (without momentum) for learning.  This
	*  version trains until a certain tolerance or max number of
	*  iterations is reached.
	*
	*  @param  testGenerator   Training instance generator that
	*          generates combinations of inputs and corresponding
	*          outputs to train the network to reproduce.
	*  @param  tol   Tolerance to train network to (mean error across
	*          all the outputs accross a training set).
	*  @param  maxIterations   Max number of iterations allowed for
	*          training.
	*  @return Number of training interations completed.  If this
	*          number is >= maxIterations, then the network was
	*          not able to be trained.  You might check MSError() to
	*          see if it is close and just needs a few more (hundred)
	*          iterations.
	**/
	public int train( TrInstGenerator testGenerator, float tol, int maxIterations ) {
		int iter = 1;
		float E2min = Float.MAX_VALUE;
		float failcounter = 0;
		float E2 = Float.MAX_VALUE;
		int setSize = testGenerator.numberInstances();
		
		// Make tol equal the squared error tolerance.
		tol *= tol;
		
		// Loop over all the training iterations, using a new training instance for each.
		while ( E2 >= tol && iter <= maxIterations ) {
			for ( int j = 0; j < setSize; ++j, ++iter ) {
				testGenerator.nextInstance();
				float[] tInputs = testGenerator.getTestInputs();
				float[] tOutputs = testGenerator.getTestOutputs();
				setInputs( tInputs );
				backpropagate( tOutputs );
			}
			
			// Determine the average squared error of the network after training.
			E2 = MSError( testGenerator );
		}
		
		return iter;
	}

	/**
	*  Adjust the strengths of the connections (weights) between
	*  the neurons in this network to reduce errors at the output
	*  nodes using the method of back propagation without momentum.
	*  This is called by the train() method.
	*
	*  @param  targetOutputs  Array of "correct" output values for
	*          the current set of input values.
	**/
	protected void backpropagate( float[] targetOutputs ) {
		int numOutputs = outputs.length;
		
		if ( targetOutputs.length != numOutputs )
			throw new IndexOutOfBoundsException( "Require same number of target outputs as output nodes." );
		
		int i, j;
		int numHLayers = hLayers.length;
		int numNpHL;
		
		// First clear all the current error values stored in the neurons.
		for ( i = 0; i < numHLayers; ++i ) {
			numNpHL = hLayers[i].length;
			for ( j = 0; j < numNpHL; ++j )
				hLayers[i][j].clearError();
		}
		j = inputs.length;
		for ( i = 0; i < j; ++i )
			inputs[i].clearError();
		
		// Calculate the error signal at each output node and adjust the weights between
		// the output nodes and the last hidden layer.
		for ( i = 0; i < numOutputs; ++i ) {
			float value = outputs[i].getOutput();
			float error = targetOutputs[i] - value;
			outputs[i].setError( error );
			adjustWeights( outputs[i], learningFactor );
		}
		
		// Adjust the weights between each hidden layer and between the 1st hidden layer
		// and the input layer.
		for ( i = numHLayers - 1; i >= 0; --i ) {
			numNpHL = hLayers[i].length;
			for ( j = 0; j < numNpHL; ++j )
				adjustWeights( hLayers[i][j], learningFactor );
		}
	}

	//-----------------------------------------------------------------------------
	/**
	*  Adjust the weights going into a particular node in such
	*  a way as to reduce the total mean error of all the output
	*  nodes.  Used by the "backprogagate()" method.
	*
	*  @param  theNeuron  Neuron to have the input weights adjusted for.
	*  @param  leanringFactor  The learning factor to use for adjusting
	*          this neuron's input connection strengths.
	**/
	protected static void adjustWeights( Neuron theNeuron, float learningFactor ) {
		Neuron[] inputNeurons = theNeuron.getInputNeurons();
		
		// If no inputs, it's probably a bias node, just return.
		if ( inputNeurons == null )
			return ;
		
		// Determine how much we should adjust the input weights:
		float output = theNeuron.getOutput();
		float error = theNeuron.getError();
		// Delta = error * dr/dq
		float delta = error * theNeuron.getSlope();
		
		// If the node is WAY off, then try something drastic (this is an "engineering" solution).
		if ( Math.abs( error ) > 0.9 && Math.abs( delta ) < 0.1 )
			delta = error * 0.1F;
		
		// Loop over all the input nodes adjusting the weights
		// to them proportional to their contribution.
		int numInputs = inputNeurons.length;
		for ( int i = 0; i < numInputs; ++i ) {
			float weight = theNeuron.getWeight( i );
			float input = inputNeurons[i].getOutput();
			float increment = learningFactor * delta * input;
			weight += increment;
			theNeuron.setWeight( i, weight );
			inputNeurons[i].addErrorIncrement( delta * weight );
		}
		
		// Tell theNeuron to update it's value now that it has changed.
		theNeuron.setUpdate();
	}


}



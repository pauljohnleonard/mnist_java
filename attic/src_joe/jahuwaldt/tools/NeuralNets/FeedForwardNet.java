/*
*    FeedForwardNet  --  Basic feed forward type network without learning.
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

import java.io.*;


/**
*  <p>  Basic Feed Forward type network with no built
*       in learning scheme.  This class can be used by
*       itself if you provide a working set of connection
*       weights between the nodes.  However, normally
*       this class is sub-classed in order to provide a
*       training mechanism.
*  </p>
*
*  <p>  The basic implementation used here sets up all
*       hidden layers with the same number of nodes in
*       each.  Also, each node is connected with all the
*       nodes in the layer immediatly preceeding it only.
*       However, subclasses are free to change the
*       network architecture as this connectivity pattern
*       and number of nodes per layer is not assumed
*       in any support routines.
*  </p>
*
*  <p>  Modified by:  Joseph A. Huwaldt    </p>
*
*  @author    Joseph A. Huwaldt    Date:  May 18, 1999
*  @version   August 10, 2002
**/
public class FeedForwardNet implements Serializable {

	// Vector of inputs to this neuron.
	protected InputNeuron[] inputs;

	// Array of neurons found in the hidden layers.
	protected Neuron[][] hLayers;

	// Vector of output neurons.
	protected Neuron[] outputs;

	// Bias node (always outputs 1) that inputs into each computational layer.
	protected InputNeuron biasNode = new InputNeuron( 1 );

	// The number of connections between all neurons including bias nodes.
	protected int numConnections = 0;

	/**
	*  Constructor for a feed forward network where the weights
	*  are set to random values (with a gaussian distribution
	*  between -5 and +5).
	*
	*  @param  numInputs   Number of input nodes or neurons.
	*  @param  numOutputs  Number of output nodes.
	*  @param  numHLayers  Number of hidden layers in the network.
	*  @param  neuronsPerHLayer  Number of neurons per hidden layer.
	*  @param  factory     Factory object used to generate neurons of
	*                      a user specified type.
	**/
	public FeedForwardNet( int numInputs, int numOutputs, int numHLayers,
							int neuronsPerHLayer, NeuronFactory factory ) {

		int i, j;
		
		// Create a vector of input neurons + 1 bias node.
		inputs = new InputNeuron [ numInputs + 1 ];
		for ( i = 0; i < numInputs; ++i )
			inputs[i] = new InputNeuron();
		
		// Add the bias node onto the end.
		inputs[numInputs] = biasNode;
	
		// Create the array of hidden layers.
		hLayers = new Neuron [ numHLayers ] [];
		for ( i = 0; i < numHLayers; ++i ) {
			// Create vector of nodes in each layer + 1 bias node.
			hLayers[i] = new Neuron [ neuronsPerHLayer + 1 ];
			for ( j = 0; j < neuronsPerHLayer; ++j ) {
				if ( i == 0 )
					// Link 1st hidden layer to input layer.
					hLayers[i][j] = factory.newNeuron( inputs );
				else
					// Link each hidden layer to the one before it.
					hLayers[i][j] = factory.newNeuron( hLayers[i - 1] );
			}
			// Add the bias node onto the end.
			hLayers[i][neuronsPerHLayer] = biasNode;
		}
		
		// Create vector of output neurons.
		outputs = new Neuron [ numOutputs ];
		for ( i = 0; i < numOutputs; ++i )
			// Link the output layer to the last hidden layer.
			outputs[i] = factory.newNeuron( hLayers[numHLayers - 1] );
		
		// Calculate the number of connections between nodes.
		numConnections = calcNumConnections();
	}

	//-----------------------------------------------------------------------------
	/**
	*  Returns the number of input nodes in this network (not
	*  counting the "input" bias node).
	*
	*  @return  The number of input nodes in this network.
	**/
	public int getNumInputs() {
		// Remember not to report the hidden bias node!
		return inputs.length - 1;
	}

	/**
	*  Set a specified input node to a specified value.
	*
	*  @param  inputNum Index of the input node to be set.
	*  @param  value    Value to set input node to.
	**/
	public void setInput( int inputNum, float value ) {
		inputs[inputNum].setOutput( value );
	}

	/**
	*  Set all input nodes at once using a vector of input values.
	*
	*  @param  values   Array of values to assign to input nodes.
	**/
	public void setInputs( float[] values ) {
	
		// Remember, inputs[] includes a bias node but the values[] vector does not!
		if ( values.length + 1 == inputs.length ) {
			int length = values.length;
			for ( int i = 0; i < length; ++i )
				inputs[i].setOutput( values[i] );
			
		} else
			throw new IndexOutOfBoundsException( "Length of input values array does not mach number of input nodes." );
	}

	/**
	*  Get the current value of a specified input node.
	*
	*  @param  inputNum  Index of the input node to get the value of.
	*  @return The value of the specified input node.
	**/
	public float getInput( int inputNum ) {
		return (inputs[inputNum].getOutput());
	}

	/**
	*  Get values of all the input nodes at once returned as an array.
	*
	*  @return 1D array of all input node values.
	**/
	public float[] getInputs() {
	
		// Remember, inputs[] includes a bias node but values[] does not!
		int length = inputs.length - 1;
		float[] values = new float[ length ];
		for ( int i = 0; i < length; ++i )
			values[i] = inputs[i].getOutput();
		
		return values;
	}

	/**
	*  Returns the number of output nodes in this network.
	*
	*  @return The number of output neurons in this network.
	**/
	public int getNumOutputs() {
		return outputs.length;
	}

	/**
	*  Get the output value of a specified output node.
	*
	*  @param  outputNum  Index of the output node to get the value of.
	*  @return The value of the specified output node.
	**/
	public float getOutput( int outputNum ) {
		return (outputs[outputNum].getOutput());
	}

	/**
	*  Get output values of all the output nodes at once returned as an array.
	*
	*  @return 1D array of output node values.
	**/
	public float[] getOutputs() {
		int length = outputs.length;
		float[] values = new float[ length ];
		for ( int i = 0; i < length; ++i )
			values[i] = outputs[i].getOutput();
		
		return values;
	}

	/**
	*  Returns the number of hidden layers in this network.
	*
	*  @return The number of hidden layers in this network.
	**/
	public int getNumHiddenLayers() {
		return hLayers.length;
	}

	/**
	*  Returns the number of neurons in a given hidden layer in this network.
	*  The default implementation has the same number of neurons in each
	*  hidden layer, however, subclasses may have different architectures where this
	*  is not the case.  It is best to check each layer and not assume.
	*  Includes one bias node that is part of every hidden layer.
	*  This is different than getNumInputs()!
	*
	*  @param  The hidden layer to return the number of neurons for.
	*  @return The number of nodes in a given hidden layer
	*          in this network.
	**/
	public int getNumHLNeurons( int layer ) {
		return hLayers[layer].length;
	}

	/**
	*  Returns the total number of neurons or nodes in the
	*  entire network, including input nodes.  Includes the bias
	*  node that is a part of every hidden and input layer.
	*  This is different than getNumInputs()!
	*
	*  @return The number of neurons in this network.
	**/
	public int getNumNeurons() {
		int num = inputs.length;
		
		int numHLayers = hLayers.length;
		for ( int i = 0; i < numHLayers; ++i )
			num += hLayers[i].length;
		
		num += outputs.length;
		
		return num;
	}

	/**
	*  Return the total number of connections between all
	*  neurons in this network.  Includes connections to the
	*  bias node in the input and each of the hidden layers!
	*
	*  @return The number of connections between nodes  in this network.
	**/
	public int getNumConnections() {
		return numConnections;
	}

	/**
	*  <p>  Return the weight values associated with every
	*       connection between every neuron in this network (including
	*       bias nodes).  The values are returned as a single vector
	*       (1D array) where the values are ordered as follows:
	*  </p>
	*  <p>     1. Weights between 1st hidden layer and input nodes (including bias node).  </p>
	*  <p>     2. Weights between each hidden layer and the one before it.     </p>
	*  <p>     3. Weights between the output nodes and the last hidden layer.  </p>
	*
	*  @return The weights between all neurons in this network.
	**/
	public float[] getAllWeights() {
		int i, j;
		int numWpN;
		int numHLayers = hLayers.length;
		int numOutputs = outputs.length;
		int weightNum = 0;
		float[] allWeights = new float[ numConnections ];
		
		// Extract weights from the hidden layer nodes.
		for ( i = 0; i < numHLayers; ++i ) {
			int numNpHL = hLayers[i].length;
			for ( j = 0; j < numNpHL; ++j ) {
				numWpN = hLayers[i][j].getNumInputs();
				for ( int k = 0; k < numWpN; ++k, ++weightNum ) {
					allWeights[weightNum] = hLayers[i][j].getWeight( k );
				}
			}
		}
		
		// Extract weights from the output layer nodes.
		for ( i = 0; i < numOutputs; ++i ) {
			numWpN = outputs[i].getNumInputs();
			for ( j = 0; j < numWpN; ++j, ++weightNum ) {
				allWeights[weightNum] = outputs[i].getWeight( j );
			}
		}
		
		return allWeights;
	}

        /**
         * <p> get the weights of neuron in a hidden layer 
         * @param layer
         * @param index
         * @return
         */
        public float [] getWeights(int layer,int index) {
            return hLayers[layer][index].getWeights();            
        }
	/**
	*  <p>  Return the weight values associated with every
	*       connection between every neuron in this network
	*       (including bias nodes).  The values are returned as a 3D
	*       array (not square!) where the values are ordered as
	*       follows:
	*  </p>
	*  <p>  weights[i][j][k]  where:  </p>
	*  <p>      i = Indicates layer:  0 <= i < number hidden layers + output layer    </p>
	*  <p>      j = Indicates node in layer:  0 <= j < number of nodes in layer       </p>
	*  <p>      k = Indicates connection in each node:  0 <= j < num of connections per node </p>
	*
	*  @return The weights between all neurons in this network.
	**/
	public float[][][] getAllWeights2() {
		int i, j;
		int numOutputs = outputs.length;
		int numHLayers = hLayers.length;
		float[][][] allWeights = new float [ numHLayers + 1 ] [] [];
		
		// Extract weights from the hidden layer nodes.
		for ( i = 0; i < numHLayers; ++i ) {
			int numNpHL = hLayers[i].length;
			allWeights[i] = new float[ numNpHL ][];
			
			for ( j = 0; j < numNpHL; ++j )
				allWeights[i][j] = hLayers[i][j].getWeights();
		}
		
		// Extract weights from the output layer nodes.
		allWeights[numHLayers] = new float[ numOutputs ][];
		for ( j = 0; j < numOutputs; ++j )
			allWeights[numHLayers][j] = outputs[j].getWeights();
		
		return allWeights;
	}

	/**
	*  <p>  Set the weight values associated with every connection
	*       between every neuron in this network.  The values are
	*       passed in as a single vector (1D array) where the values
	*       are ordered as follows:
	*  </p>
	*  <p>  1. Weights between 1st hidden layer and input nodes (including bias node).  </p>
	*  <p>  2. Weights between each hidden layer and the one before it.     </p>
	*  <p>  3. Weights between the output nodes and the last hidden layer.  </p>
	*
	*  @param  allWeights  The weights between all neurons in this network.
	**/
	public void setAllWeights( float[] allWeights ) {
		if ( allWeights.length != numConnections )
			throw new IndexOutOfBoundsException( "Length of weights array does not mach number of connections in network." );
		
		else {
			int i, j;
			int numWpN;
			int numHLayers = hLayers.length;
			int numOutputs = outputs.length;
			int weightNum = 0;
			
			// Set the weights in the hidden layer nodes.
			for ( i = 0; i < numHLayers; ++i ) {
				int numNpHL = hLayers[i].length;
				for ( j = 0; j < numNpHL; ++j ) {
					numWpN = hLayers[i][j].getNumInputs();
					for ( int k = 0; k < numWpN; ++k, ++weightNum )
						hLayers[i][j].setWeight( k, allWeights[weightNum] );
					
					hLayers[i][j].setUpdate();
				}
			}
			
			// Set the weights in the output layer nodes.
			for ( i = 0; i < numOutputs; ++i ) {
				numWpN = outputs[i].getNumInputs();
				for ( j = 0; j < numWpN; ++j, ++weightNum )
					outputs[i].setWeight( j, allWeights[weightNum] );
				
				outputs[i].setUpdate();
			}
			
		}
		
	}

	/**
	*  <p>  Sets the weight values associated with every connection
	*       between every neuron in this network.  The values are
	*       passed in as a 3D array where the values are ordered
	*       as follows:
	*  </p>
	*  <p>  weights[i][j][k]  where:  </p>
	*  <p>      i = Indicates layer:  0 <= i < number hidden layers + output layer    </p>
	*  <p>      j = Indicates node in layer:  0 <= j < number of nodes in layer       </p>
	*  <p>      k = Indicates connection in each node:  0 <= j < num of connections per node </p>
	*
	*  @param  allWeights   Array of weights between all neurons in this network.
	**/
	public void setAllWeights( float[][][] allWeights ) {
		int i, j;
		int numNpHL;
		int numHLayers = hLayers.length;
		int numOutputs = outputs.length;
		
		// Check inputs for consistancy with the current network.
		if ( allWeights.length != hLayers.length + 1 )
			throw new IndexOutOfBoundsException( "Number of layers in input array does not match network." );
		
		for ( i = 0; i < numHLayers; ++i ) {
			numNpHL = hLayers[i].length;
			if ( allWeights[i].length != numNpHL )
				throw new IndexOutOfBoundsException( "Number of nodes in hidden layer " + i +
															" does not match network." );
			
			for ( j = 0; j < numNpHL; ++j ) {
				if ( allWeights[i][j].length != hLayers[i][j].getNumInputs() )
					throw new IndexOutOfBoundsException( "Number of connections in hidden layer " + i +
									", node " + j + " does not match network." );
			}
		}
		
		// Set weights between hidden layer nodes.
		for ( i = 0; i < numHLayers; ++i ) {
			numNpHL = hLayers[i].length;
			for ( j = 0; j < numNpHL; ++j ) {
				hLayers[i][j].setWeights( allWeights[i][j] );
				hLayers[i][j].setUpdate();
			}
		}
		
		// Set weights for the output nodes.
		for ( j = 0; j < numOutputs; ++j ) {
			outputs[j].setWeights( allWeights[numHLayers][j] );
			outputs[j].setUpdate();
		}
		
	}

	/**
	*  Sets the weight values associated with every connection
	*  between every neuron in this network to random values.
	**/
	public void setRandomWeights() {
		int i;
		int numHLayers = hLayers.length;
		int numOutputs = outputs.length;
		
		// Set the weights in the hidden layer nodes.
		for ( i = 0; i < numHLayers; ++i ) {
			int numNpHL = hLayers[i].length;
			for ( int j = 0; j < numNpHL; ++j )
				hLayers[i][j].setRandomWeights();
		}
		
		// Set the weights in the output layer nodes.
		for ( i = 0; i < numOutputs; ++i )
			outputs[i].setRandomWeights();
	}

	/**
	*  Calculates the mean squared error of the network across
	*  all the supplied training instances.
	*
	*  @param  testGenerator  Reference to a class that can provide training instances.
	*  @return The mean squared error of this network.
	**/
	public float MSError( TrInstGenerator testGenerator ) {
		float MSE = 0;
		
		// Get the number of training patterns.
		int Np = testGenerator.numberInstances();
		
		// Loop over all the training patterns.
		for ( int n = 0; n < Np; ++n ) {
			float E2i = 0;
			float[] tInputs, tOutputs;
			
			// Get the test inputs and target outputs.
			testGenerator.nextInstance();
			tInputs = testGenerator.getTestInputs();
			tOutputs = testGenerator.getTestOutputs();
			
			setInputs( tInputs );
			
			// Determine the squared error of the network for a single pattern.
			int length = outputs.length;
			for ( int k = 0; k < length; ++k ) {
				float error = tOutputs[k] - outputs[k].getOutput();
				E2i += error * error;
			}
			
			// Add in the average error across the outputs for this case.
			MSE += E2i/length;
		}
		
		// Output the mean squared error for the network.
		MSE /= Np;
		
		return MSE;
	}

	/**
	*  Calculates the number of connections between neurons
	*  in this network.  Includes connections made to bias nodes
	*  in each input and hidden layer!
	*
	*  @return The number of connections between nodes in this network.
	**/
	protected final int calcNumConnections() {
		int i;
		int numHLayers = hLayers.length;
		int numConnections = 0;
		int numOutputs = outputs.length;
		
		// Add up connections going into hidden layer neurons.
		for ( i = 0; i < numHLayers; ++i ) {
			int numNpL = hLayers[i].length;
			for ( int j = 0; j < numNpL; ++j )
				numConnections += hLayers[i][j].getNumInputs();
		}
		
		// Add the connections going into the output layer neurons.
		for ( i = 0; i < numOutputs; ++i )
			numConnections += outputs[i].getNumInputs();
		
		return numConnections;
	}


}



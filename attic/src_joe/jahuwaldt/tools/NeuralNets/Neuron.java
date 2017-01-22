/*
*    Neuron   --  Base class for all neuron types used in neural networks
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

import java.util.*;
import java.io.*;


/**
*  Base class for all neuron types used in Artificial
*  Neural Network (ANN) simulations.
*
*  <p>  Modified by:  Joseph A. Huwaldt    </p>
*
*  @author    Joseph A. Huwaldt    Date:  May 18, 1999
*  @version   August 10, 2002
**/
public abstract class Neuron implements Observer, Cloneable, Serializable {
	
	//	A random number generator.
	private static Random ranNumGen = new Random();
	
	// Output value of this neuron or node.
	protected float value = 0;

	// Gradient of this nodes outputs with respect to it's inputs.
	protected float gradient = 0;

	// Error assigned to this neuron for training.
	protected float error = 0;

	// Identifies if this neuron needs to have it's value calculated.
	protected boolean needsUpdating = true;

	// List of neurons that input to this one.
	protected Neuron[] inputs = null;

	// Weights (strengths) of each link between this neuron and it's inputs.
	protected float[] weights = null;

	// The notifier is transient so that it won't be serialized (it can't be).
	private transient Observable notifier = null;

	//-----------------------------------------------------------------------------
	/**
	*  Returns the output value of this network node or neuron.
	*
	*  @return The output value of this neuron.
	**/
	public float getOutput() {
		return value;
	}

	/**
	*  Returns the gradient of this node or neuron's activation
	*  function with respect to the weighted neuron inputs.
	*       Example:
	*           If the neuron output "r" is the exp simoid function:
	*                 r = 1/(1 + e^q), 
	*           then this function should return:
	*                 dr/dq = r*(1 - r).
	*
	*  @return  The gradient of this neuron's activation function.
	**/
	public float getSlope() {
		return gradient;
	}

	/**
	*  Sets the output value of this neuron.  Notifies neurons
	*  dependent on this one that they need to be updated.
	*
	*  @param  newValue  The value to force this neuron to output.
	**/
	public void setOutput( float newValue ) {
		value = newValue;
		updateChildren();
	}

	/**
	*  Sets the error value associated with this neuron.
	*
	*  @param  errorValue  The error value to associate with this neuron.
	**/
	public  void setError( float errorValue ) {
		error = errorValue;
	}

	/**
	*  Returns the error value associated with this neuron.
	*
	*  @return The error value associated with this neuron.
	**/
	public float getError() {
		return error;
	}

	/**
	*  Sets the error value associated with this neuron to zero.
	**/
	public void clearError() {
		error = 0;
	}

	/**
	*  Add an incremental value onto the error associated with this neuron.
	*
	*  @param  increment  The incremental error value to add to this
	*          neuron's error value.
	**/
	public void addErrorIncrement( float increment ) {
		error += increment;
	}

	/**
	*  Return the number of inputs to this neuron (also the number
	*  of weights for this neuron).
	*
	*  @return The number of inputs to this neuron.
	**/
	public int getNumInputs() {
		int length = 0;
		
		if ( inputs != null )
			length = inputs.length;
		
		return length;
	}

	/**
	*  Return the list of neurons that input into this one.
	*
	*  @return The list of neurons that input into this one
	*          as an array.
	**/
	public Neuron[] getInputNeurons() {
		return inputs;
	}

	/**
	*  Set the weight associated with one of the inputs to this neuron.
	*
	*  @param  inputNum  Input weight to be changed.
	*  @param  newWeight New weight value.
	**/
	public void setWeight( int inputNum, float newWeight ) {
		weights[inputNum] = newWeight;
	}

	/**
	*  Set the weights associated with all of the inputs to this neuron.
	*
	*  @param  newWeights  1D array of weight values to replace the
	*          ones used by this node.
	**/
	public void setWeights( float[] newWeights ) {
		int numWeights = newWeights.length;

		if ( numWeights != weights.length ) {
			throw new IndexOutOfBoundsException( "Length of weights vector does not mach number of inputs to this node." );
		}

		// Replace the old weights array with the new one.
		for ( int i = 0; i < numWeights; ++i )
			weights[i] = newWeights[i];
	}

	/**
	*  Set the weights associated with all of the inputs to this
	*  neuron to random values with a gaussian distribution
	*  between -5 and +5.
	**/
	public final void setRandomWeights() {
		if ( weights != null ) {
			
			// Replace the old weight values with new random ones.
			int numWeights = weights.length;
			for ( int i = 0; i < numWeights; ++i )
				weights[i] = randomWeight();
		}
	}

	/**
	*  Returns the value of weight associated with an input to this neuron.
	*
	*  @param  inputNum  Input weight to return.
	*  @return Value of the specified input weight.
	**/
	public float getWeight( int inputNum ) {
		return weights[inputNum];
	}

	/**
	*  Returns the list of weights associated with the inputs to this neuron.
	*
	*  @return A list of weights associated with the inputs to this neuron.
	**/
	public float[] getWeights() {
		return weights;
	}

	/**
	*  Called automatically by the Observable notification system
	*  when one of the neurons this object is dependent on has changed.
	*
	*	@param  o    The object being observed (this object)
	*	@param  arg  The object that this one depends on that has changed.
	**/
	public void update( Observable o, Object arg ) {
		if ( needsUpdating == false ) {
			// Tell this neuron that it needs updating.
			needsUpdating = true;
			updateChildren();
		}
	}

	/**
	*  Explicitly tell this neuron that it needs to update itself.
	**/
	public void setUpdate() {
		needsUpdating = true;
		updateChildren();
	}

	/**
	*  Method to return the output of this neuron as a string.
	*
	*  @return  A string representation of the output of this neuron.
	**/
	public String toString() {
		String s = Float.toString( getOutput() );
		return s;
	}

	/**
	*  Add a node to the list of nodes that are dependent on this neuron.
	*
	*  @param  observer	 The object that is dependent on (is a
	*                    child of -- observes) this one.
	**/
	protected void addChild( Neuron observer ) {
		if ( notifier == null )
			notifier = new NeuronObservable();
		
		notifier.addObserver( observer );
	}

	/**
	*  Removes a neuron from the list of neurons that are dependent on this neuron.
	*
	*  @param  observer  The object that is no longer dependent on this one.
	**/
	protected void removeChild( Neuron observer ) {
		if ( notifier != null )
			notifier.deleteObserver( observer );
	}

	/**
	*  Tell each neuron that this one depends on (each parent)
	*  that this neuron needs updating when they change value.
	*  This is normally called to link a neuron up with all of it's
	*  inputs.
	**/
	protected final void linkToInputs() {
		if ( inputs != null && inputs.length > 0 ) {
			//	Link to the current node's inputs.
			//	Add this node as a child to (dependent on) all the input nodes.
			int length = inputs.length;
			for ( int i = 0; i < length; ++i ) {
				Neuron theNode = inputs[i];
				theNode.addChild( this );
			}
		}
	}

	/**
	*  Break the link between this neuron and all the neurons that
	*  this one depends on (parent or input neurons).
	**/
	protected void removeInputLinks() {
		if ( inputs != null && inputs.length > 0 ) {
			//	Break links to current input nodes.
			//	Remove this node from the child lists of all the input nodes.
			int length = inputs.length;
			for ( int i = 0; i < length; ++i ) {
				Neuron theNode = inputs[i];
				theNode.removeChild( this );
			}
		}
	}

	/**
	*  Tell each neuron that is dependent on this one to update  themselves.
	**/
	protected void updateChildren() {
		if ( notifier != null )
			notifier.notifyObservers( this );
	}

	/**
	*  Returns a gaussian distributed deviate between -5 and
	*  +5 centered on zero.  This number is used as an initial
	*  guess at a weight value.
	*
	*  @return A normally distributed deviate between -5 and +5 centered on zero.
	**/
	private static final float randomWeight() {
		double number = ranNumGen.nextGaussian() * 5;
		return (float)number;
	}

	//-----------------------------------------------------------------------------
	/**
	*  Make a copy of this neuron (the copy will continue to refer
	*  to the same input neurons as the original, but will have
	*  it's own set of weights).
	*
	*	@return  A clone of this neuron.
	**/
	public Object clone() {
		Neuron newObject = null;
		
		try  {
			
			// Make a shallow copy of this object.
			newObject = (Neuron)super.clone();
			
			// Now make deep copy of the data contained in this object.
			// (keep reference to the same input neurons)
			if ( weights != null ) {
				int length = weights.length;
				newObject.weights = new float[ length ];
				System.arraycopy( this.weights, 0, newObject.weights, 0, length );
			}
			
			// Now link the new object up with the input neurons.
			newObject.linkToInputs();
			
		} catch( CloneNotSupportedException e ) {
			// Can't happen if this object implements Clonable.
			e.printStackTrace();
		}
		
		// Output the new cloned neuron.
		return newObject;
	}

	/**
	*  During serialization, this will read back in the serialized
	*  neuron and reconstitute it (re-creating links to input nodes
	*  etc).  This is automatically called by Java's serialization
	*  mechanism.
	**/
	private void readObject( ObjectInputStream stream ) throws IOException, ClassNotFoundException {
	
		// Read in this neuron and all it's data.
		stream.defaultReadObject();
		
		// Recreate links to input nodes (if there are any).
		linkToInputs();
	}


}

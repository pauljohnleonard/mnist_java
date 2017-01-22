/*
*    BasicNeuron  --  Basic neuron with an exponential sigmoid activation function.
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

/**
*  Basic calculating neuron type with standard
*  exponential sigmoid activation function.
*  Ouptut value: r = 1/(1 + exp(-q))
*                q = sum( input[i]*weight[i] )
*
*  <p>  Modified by:  Joseph A. Huwaldt    </p>
*
*  @author    Joseph A. Huwaldt    Date:  May 18, 1999
*  @version   August 10, 2002
**/
public class BasicNeuron extends Neuron {

	// Does the gradient value need updating?
	protected boolean updateGradient = true;

	/**
	*  Constructor that takes an array of input neurons.  Links
	*  are established between this neuron and all the input
	*  neurons.
	*
	*  @param  An array of input neurons to link this neuron to.
	**/
	public BasicNeuron( Neuron[] inputNodes ) {
		// Store off references to input neurons and create weights vector.
		inputs = inputNodes;
		linkToInputs();

		//  Fill in weights vector with a gaussian distribution of initial values.
		weights = new float [ inputNodes.length ];
		setRandomWeights();
	}

	//-----------------------------------------------------------------------------
	/**
	*  Returns the output value of this network node or neuron.
	*  This function returns the output of the standard
	*  exponential sigmoid function:  r = 1/(1 + e^q) where
	*  "q" is the weighted sum of the neuron inputs.
	*
	*  @return  The output value of this neuron.
	**/
	public float getOutput() {
	
		// Calculate the value of this neuron if it needs updating.
		if ( needsUpdating == true ) {
			calcValue();
			updateGradient = true;
			needsUpdating = false;
		}
		
		// Return the value of this neuron.
		return value;
	}

	/**
	*  Returns the gradient of this node or neuron's activation
	*  function with respect to the weighted neuron inputs.
	*  This function returns:  
	*           dr/dq = r*(1 - r).
	*
	*  @return  The gradient of this neuron's activation function.
	**/
	public float getSlope() {
		// Calculate the value of this neuron if it needs updating.
		if ( needsUpdating == true ) {
			calcValue();
			updateGradient = true;
			needsUpdating = false;
		}
		
		// Update the gradient if needed.
		if ( updateGradient == true ) {
			// Calculate the gradient of this neuron's acitvation function.
			gradient = value * (1F - value);
			
			updateGradient = false;
		}
		
		// Return the gradient dr/dq of this neuron.
		return gradient;
	}

	/**
	*  Calculates the value of this neuron based on a weighted
	*  sum of the outputs of all the neurons that input into this
	*  one processed by the "sigmoid" function.
	**/
	protected void calcValue() {
		float weightedSum = 0;
		
		// Loop over all the input neurons creating a weighted sum of their outputs.
		int length = inputs.length;
		for ( int i = 0; i < length; ++i ) {
			weightedSum += inputs[i].getOutput() * weights[i];
		}
		
		// Set the output of this neuron to the weighted sum processed by the sigmoid function.
		value = sigmoid( weightedSum );
	}

	//-----------------------------------------------------------------------------
	/**
	*  "S" shaped function used to transition this neuron from
	*  "on" to "off" depending on the value input to it.  This
	*  version uses the exponential sigmoid function:
	*        S = 1/(1 + exp(-inputValue))
	*               Approx. 1 for any x greater than 5.  Approx. 0 for
	*               any x more negative than -5.
	*               Equals 0.5 for x = 0.  "S" shaped transition.
	*
	*  @param  inputValue  Independent value used by sigmoid function.
	*  @return The value of the sigmoid function at "inputValue".
	**/
	protected static final float sigmoid( float inputValue ) {
		return (1 / (1 + (float)Math.exp( -inputValue )));
	}


}



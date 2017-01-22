/*
*    HypTangentNeuron  --  Neuron with a tanh (hyperbolic tangent) activation function.
*
*    Copyright (C) 1999 by Joseph A. Huwaldt <jhuwaldt@gte.net>.  All rights reserved.
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
*  <p> Basic calculating neuron type with a hyperbolic tangent activation function.
*      <br>
*      The advantage of the hyperbolic tangent function compared to sigmoid function is 
*      the ability to model negative values (tanh(x) returns values in the range [-1,1], 
*      whereas the values returned by the standard sigmoid function lie in the range [0,1]).
*      <br><br>
*      Output value: r = tanh(q)<br>
*					 tanh(x) = (exp(x) - exp(-x))/(exp(x)+exp(-x))       <br>
*					 exp(x) = e^x (Natural logarithm to the power of x)  <br>
*                    q = sum( input[i]*weight[i] )                       <br><br>
*  </p>
*
*  <p>  Written by:  Dimitri PISSARENKO   </p>
*  <p>  Modified by:  Joseph A. Huwaldt   </p>
*
*  @author    Dimitri PISSARENKO    Date:  December 1, 2001
*  @version   August 11, 2002
**/
public class HypTangentNeuron extends Neuron {

	// Does the gradient value need updating?
	protected  boolean updateGradient = true;

	/**
	*  Constructor that takes an array of input neurons.  Links
	*  are established between this neuron and all the input
	*  neurons.
	*
	*  @param inputNodes An array of input neurons to link this neuron to.
	**/
	public HypTangentNeuron( Neuron[] inputNodes ) {

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
	*  This function returns the output of the hyperbolic
	*  tangent function  r = tanh(q) where
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
	*  This function returns:   <br>
	*           dr/dq =  1 - tanh(r)^2  <br>
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
			gradient = tanhDerivative(value);
			
			updateGradient = false;
		}
		
		// Return the gradient dr/dq of this neuron.
		return gradient;
	}

	/**
	*  Calculates the value of this neuron based on a weighted
	*  sum of the outputs of all the neurons that input into this
	*  one processed by the "tanh" function.
	**/
	protected void calcValue() {
		float weightedSum = 0;
		
		// Loop over all the input neurons creating a weighted sum of their outputs.
		int length = inputs.length;
		for ( int i = 0; i < length; ++i )
			weightedSum += inputs[i].getOutput()*weights[i];
		
		// Set the output of this neuron to the weighted sum processed by the sigmoid function.
		value = tanh( weightedSum );
	}

    /**
    *  Returns the hyperbolic tangent of the specified argument
    *  in the range -5 to +5.
    *  The hyperbolic tangent is defined as:  <br>
    *      tanh(x) = sinh(x)/cosh(x) = 1 - 2/(exp(2*x) + 1)  <br>
	*      where: exp(x) is the number "e" = 2.718... raised to the power x.
    *
    *  @param  x  Value to determine the hyperbolic tangent of.
	*  @return Value of the hyperbolic tangent function at the point x.
    **/
    protected static final float tanh(float x) {
	
        if (Float.isNaN(x))	return Float.NaN;
        if (x == 0)	return 0;
        if (x < -5)
			return -1;
		else if (x > 5)
			return 1;
		
        double z = Math.abs(x);
        double s = Math.exp(2*z);
        z = 1.0 - 2.0/(s + 1.0);
        if (x < 0)
            z = -z;
        
        return (float)z;
	}
    
	/**
	*  Calculates the derivative of the hyperbolic tangent function.
	*  It is defined as:
	*  <br><br>
	*       tanh'(x) = 1 - tanh(x)^2
	*
	*  <p>  Written by:  Dimitri PISSARENKO    Date: December 1, 2001 </p>
	*
	*  @return Value of the derivative of hyperbolic tangent function at the point fValue.
	**/
	protected final float tanhDerivative(float fValue) {
		float fTemp = tanh(fValue);
		float fRc = 1F - fTemp*fTemp;
		return fRc;
	}
	
}


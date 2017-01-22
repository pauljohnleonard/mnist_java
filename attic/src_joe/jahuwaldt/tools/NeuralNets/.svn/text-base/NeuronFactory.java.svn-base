/*
*    NeuronFactory  --  Interface for an object that generates neurons.
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
*  Abstract factory interface that, when subclassed,
*  can generate a specific type of neuron for use in
*  various types of neural networks.
*
*  <p>  Modified by:  Joseph A. Huwaldt    </p>
*
*  @author    Joseph A. Huwaldt    Date:  May 18, 1999
*  @version   August 10, 2002
**/
public interface NeuronFactory {

	/**
	*  Override this function with one that will generate
	*  a neuron of a specific type that has the given array
	*  of neurons as inputs.
	**/
	public Neuron newNeuron( Neuron[] inputNodes );

}



/*
*    TrInstGenerator  --  Abstract class for a training instance generator object.
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
*  Abstract training instance generator (or factory, except it returns
*  combinations of inputs and associated outputs rather than objects)
*  interface.  Subclass this object to create a class capable of
*  passing training information on to a neural network that requires
*  supervised training (such as a feed forward network).  For example,
*  the subclass could generate a random set of inputs and correct set of
*  outputs that goes with it when instructed to create the "nextInstance()".
*
* <p> Modified by:  Joseph A. Huwaldt   </p>
*
*  @author    Joseph A. Huwaldt    Date:  May 19, 1999
*  @version   August 10, 2002
**/
public interface TrInstGenerator {

	/**
	*  Returns the number of training instances to use per training
	*  session.  If doing offline training this should return the
	*  total number of training patterns.  If doing on-line
	*  training, return the number of training instances to
	*  process at a time before evaluating the mean squared error.
	*
	*  @return Returns the number of training instances to use.
	**/
	int numberInstances();

	/**
	*  Instructs the training object to create or move on to
	*  a new training instance (combination of inputs and
	*  the associated "correct" or target outputs).
	**/
	void nextInstance();

	/**
	*  Returns a 1D array (or vector) of the values associated
	*  with each input node of a neural network for the current
	*  training instance.
	*
	*  @return The current set of input values.
	**/
	float[] getTestInputs();

	/**
	*  Returns a 1D array (or vector) of the values associated
	*  with each output node of a neural network for the current
	*  training instance.  These are the correct values that the
	*  network will be trained to produce using the inputs
	*  returned from getTestInputs().
	*
	*  @return The current set of output values.
	**/
	float[] getTestOutputs();

}



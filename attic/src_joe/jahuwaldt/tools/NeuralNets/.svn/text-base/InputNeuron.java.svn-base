/*
*    InputNeuron  --  A type of neuron that serves as a network input.
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
*   Type of neuron used for network input only (does
*   no calculations, it simply uses the given input
*   value as it's output value).
*
* <p> Modified by:  Joseph A. Huwaldt    </p>
*
*  @author    Joseph A. Huwaldt    Date:  May 18, 1999
*  @version   August 10, 2002
**/
public class InputNeuron extends Neuron {

	/**
	*  Default constructor for InputNeurons.
	**/
	public InputNeuron() {
		needsUpdating = false;
	}
	
	/**
	*  Constructor for an InputNeuron that takes a value for the output of the
	*  input neuron.
	**/
	public InputNeuron( float inputValue ) {
		value = inputValue;
		needsUpdating = false;
	}

	//-----------------------------------------------------------------------------
	/**
	*  Input neurons can not update themselves, so
	*  this function in the base class is overridden and
	*  does nothing here.
	**/
	public void setUpdate() {}


}



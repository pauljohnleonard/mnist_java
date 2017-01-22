/*
*    NeuronObservable   --  Allows Neurons to implement the Observable interface
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
*  This object allows us to implement the observable interface
*  in our Neuron objects.
*
*  <p>  Modified by:  Joseph A. Huwaldt  </p>
*
*  @author   Joseph A. Huwaldt  Date:  May 16, 1999
*  @version  August 10, 2002
**/
public class NeuronObservable extends Observable {

	public void notifyObservers( Object b ) {
		setChanged();
		super.notifyObservers( b );
	}

}


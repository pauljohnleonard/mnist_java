/**
*   Please feel free to use any fragment of the code in this file that you need
*   in your own work. As far as I am concerned, it's in the public domain. No
*   permission is necessary or required. Credit is always appreciated if you
*   use a large chunk or base a significant product on one of my examples,
*   but that's not required either.
*
*   This code is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
*
*      --- Joseph A. Huwaldt
**/
import java.util.*;
import jahuwaldt.tools.NeuralNets.*;


/**
*  This class provides combinations of inputs and
*  outputs that are used to train a neural network
*  to emulate the XOR logical function.
*
*  <p>  Modified by:  Joseph A. Huwaldt    </p>
*
*  @author    Joseph A. Huwaldt    Date:  May 19, 1999
*  @version   August 10, 2002
**/
public class XORTrainer implements TrInstGenerator {

	private static final Random ranNumGen = new Random();

	// Array containing the current instance's input pattern.
	private float[] inputs = new float[ 2 ];

	// Array containing the current instance target output values.
	private float[] outputs = new float[ 1 ];

	// Arrays containing all possible input patterns.
	private final float[] allInputs0 = { 0, 1, 0, 1 };

	private final float[] allInputs1 = { 0, 0, 1, 1 };

	// Array of flags indicating if a particular pattern has been used or not.
	private boolean[] chosen = new boolean[ 4 ];

	// Determines if we have used all the patterns yet or not.
	private static int counter = 0;


	/**
	*  Returns the number of training instances to use per training
	*  session.  If doing offline training this should return the
	*  total number of training patterns.  If doing on-line
	*  training, return the number of training instances to
	*  process at a time before evaluating the mean squared error.
	*
	*  @return The number of training instances available.
	**/
	public int numberInstances() {
		return 4;
	}

	/**
	*  Creates a new XOR training instance (two inputs and a
	*  corresponding output) by randomly choosing the inputs
	*  and calculating the correct output.
	**/
	public void nextInstance() {
		int pick;
		
		// Pick a test case that hasn't been chosen yet.
		do {
			pick = (int)(ranNumGen.nextFloat()*4);
			
		} while ( chosen[pick] == true );
		
		// Mark this one as having been picked.
		chosen[pick] = true;
		
		++counter;
		if ( counter == 4 ) {
			counter = 0;
			for ( int i = 0; i < 4; ++i )
				chosen[i] = false;
		}
		
		inputs[0] = allInputs0[pick];
		inputs[1] = allInputs1[pick];
		
		// Evaluate the XOR function.
		outputs[0] = inputs[0] + inputs[1] - 2*inputs[0]*inputs[1];
		
		// Make inputs range from -1 to +1.
		if ( inputs[0] == 0 )
			inputs[0] = - 1;
		
		if ( inputs[1] == 0 )
			inputs[1] = - 1;
		
	}

	/**
	*  Returns a 1D array (or vector) of the values associated
	*  with each input node of a neural network for the current
	*  training instance.
	*
	*  @return Returns the current set of input values.
	**/
	public float[] getTestInputs() {
		return inputs;
	}

	/**
	*  Returns a 1D array (or vector) of the values associated
	*  with each output node of a neural network for the current
	*  training instance.  These are the correct values that the
	*  network will be trained to produce using the inputs
	*  returned from getTestInputs().
	*
	*  @return Returns the current set of output values.
	**/
	public float[] getTestOutputs() {
		return outputs;
	}


}



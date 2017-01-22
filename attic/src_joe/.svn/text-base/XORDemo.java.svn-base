/**
*   Please feel free to use any fragment of the code IN THIS FILE that you need
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
import java.io.*;
import jahuwaldt.tools.NeuralNets.*;


/**
*  A simple application to test feed forward neural network
*  routines using the standard XOR problem.
*
*  <p>  Modified by:  Joseph A. Huwaldt    </p>
*
*  @author    Joseph A. Huwaldt    Date:  May 19, 1999
*  @version   August 11, 2002
**/
public class XORDemo {

	/**
	*  A simple example for demonstration and testing purposes.
	**/
	public static void main( String[] args ) {
		System.out.println( "Start..." );
		
		// Create a object that can provide training instances for the XOR problem.
		TrInstGenerator myTrainer = new XORTrainer();
		
		// Create our feedforward network with backpropogation learning using BasicNeurons.
		FeedForwardNetBP myFFNet = new FeedForwardNetBP( 2, 1, 2, 3, new BasicNeuronFactory() );
		
		// Save off the initial set of weights to make a fair comparison with
		// other training methods.
		float[] weights = myFFNet.getAllWeights();
		
		// Train the network to an average tolerance of 0.05,
		// and limit to a maximum of 10000 instances.
		System.out.println( "Training using Backpropogation..." );
		int iterations = myFFNet.train( myTrainer, 0.05F, 10000 );
		System.out.println( "I = " + iterations );
		System.out.println( "   MSE=" + myFFNet.MSError( myTrainer ) );
		
		// Try test cases.
		testCases( myFFNet, myTrainer );
		
		// Create a feedforward network with scaled conjugate gradient training using BasicNeurons.
		FeedForwardNetSCG myFFNet2 = new FeedForwardNetSCG( 2, 1, 2, 3, new BasicNeuronFactory() );
		
		// Set the initial weights in this network to be the same as the previous one.
		myFFNet2.setAllWeights( weights );
		
		// Train the network to an average tolerance of 0.05,
		System.out.println( "\nTraining using Scaled Conjugate Gradient..." );
		iterations = myFFNet2.train( myTrainer, 0.05F, 200 );
		System.out.println( "I = " + iterations );
		System.out.println( "   MSE=" + myFFNet2.MSError( myTrainer ) );
		
		// Try test cases.
		testCases( myFFNet2, myTrainer );
		
		// Try serializing the network.
		try  {
			String filename = "network.out";
			
			// Create an object stream to the file.
			ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( filename ) );
			
			// Serialize the network.
			System.out.println( "\nSerializing the network to a disk file..." );
			out.writeObject( myFFNet2 );
			out.close();
			
			// Remove our reference to the existing table.
			myFFNet2 = null;
			
			// Create an object stream from the file.
			ObjectInputStream in = new ObjectInputStream( new FileInputStream( filename ) );
			
			// Reconstitute the table.
			System.out.println( "Restoring the newtwork from disk file..." );
			myFFNet2 = (FeedForwardNetSCG) in.readObject();
			in.close();
			
			// Try test cases.
			testCases( myFFNet2, myTrainer );
			
		} catch( Exception e ) {
			e.printStackTrace();
		}
		
		System.out.println( "Done..." );
	}

	/**
	* Generates some test cases for evaluating our neural networks.
	**/
	static void testCases( FeedForwardNet myFFNet, TrInstGenerator myTrainer ) {
		float[] inputs = new float [ 2 ];
		int numCases = myTrainer.numberInstances();
		
		for ( int i = 0; i < numCases; ++i ) {
			
			// Use our training instance generator to generate some testing instances.
			myTrainer.nextInstance();
			inputs = myTrainer.getTestInputs();
			myFFNet.setInputs( inputs );
			float output = myFFNet.getOutput( 0 );
			
			// Scale inputs to the range 0 to +1 rather than -1 to +1.
			if ( inputs[0] == - 1 )
				inputs[0] = 0;
			
			if ( inputs[1] == - 1 )
				inputs[1] = 0;
			
			System.out.println( "In=" + inputs[0] + "    " + inputs[1] + " Out=" + output );
		}
	}


}



package uk.ac.bath.eeg.fir;

/**
 * First hack at FIR-MLP...
 * @author bwm23
 */

import uk.ac.bath.ai.util.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;
import uk.ac.bath.util.Tweakable;
import uk.ac.bath.util.TweakableDouble;

public class FIRF  implements Serializable {
    private static final int filterOrder = 5;

	//	output of each neuron
    float out[][];
    
    //	delta error value for each neuron
    float delta[][];
    
    //	vector of weights for each neuron
    //float weight[][][];
    
    // Vector of filters for each neuron
    FIRFilter filter[][][];
    
    //	no of layers in net
    //	including input layer
    int numlayer;
    
    //	vector of numl elements for size 
    //	of each layer
    int layersizes[];
    
    //	learning rate
    double beta;
    
	private double[] filterCoefficients;
	
    transient Vector<Tweakable> tweaks = new Vector<Tweakable>();
    transient private TweakableDouble betaTweak;
    

    
    FIRF(int sz[], double b, Random rand) {


        beta = b;
        
        // Create memory for filterCoefficients
        filterCoefficients = new double[filterOrder];

        //	set no of layers and their sizes
        numlayer = sz.length;
        layersizes = new int[numlayer];

        for (int i = 0; i < numlayer; i++) {
            layersizes[i] = sz[i];
        }

        //	allocate memory for output of each neuron
        out = new float[numlayer][];

        for (int i = 0; i < numlayer; i++) {
            out[i] = new float[layersizes[i]];
        }

        //	allocate memory for delta
        delta = new float[numlayer][];

        for (int i = 1; i < numlayer; i++) {
            delta[i] = new float[layersizes[i]];
        }

        //	allocate memory for filters
        filter = new FIRFilter[numlayer][][];

        for (int i = 1; i < numlayer; i++) {
            filter[i] = new FIRFilter[layersizes[i]][];
        }
        for (int i = 1; i < numlayer; i++) {
            for (int j = 0; j < layersizes[i]; j++) {
                filter[i][j] = new FIRFilter[layersizes[i - 1] + 1];
            }
        }

        
        // Set the filter coeffs to a random value to start with.
        for (int i = 1; i < numlayer; i++) {
            for (int j = 0; j < layersizes[i]; j++) {
                for (int k = 0; k < layersizes[i - 1] + 1; k++) {
                	for (int l = 0; l < filterOrder; l++){
                		filterCoefficients[l] = (Double) rand.nextDouble() - 0.5;
                		filter[i][j][k] = new FIRFilter(filterCoefficients);
                	}
                
                //	initialize previous weights to 0 for first iteration
                }
            }
        }


    }
    
    public Vector<Tweakable> getTweaks() {
        return tweaks;
    }


    //	mean square error
    float mse(float tgt[]) {
        float mse = 0;
        for (int i = 0; i < layersizes[numlayer - 1]; i++) {
            mse += (tgt[i] - out[numlayer - 1][i]) * (tgt[i] - out[numlayer - 1][i]);
        }
        return mse / 2;
    }
    //	returns i'th output of the net
    float Out(int i) {
        return out[numlayer - 1][i];
    }

    float[] output() {
        return out[numlayer - 1];
    }

    // feed forward one set of input
    void ffwd(float in[]) {
        float sum;

        //	assign content to input layer
        for (int i = 0; i < layersizes[0]; i++) {
            out[0][i] = in[i];  // output_from_neuron(i,j) Jth neuron in Ith Layer

        //	assign output(activation) value 
        //	to each neuron usng sigmoid func
        }
        for (int i = 1; i < numlayer; i++) {				// For each layer
            for (int j = 0; j < layersizes[i]; j++) {		// For each neuron in current layer
                sum = 0.0f;
                for (int k = 0; k < layersizes[i - 1]; k++) {		// For input from each neuron in preceeding layer
                    // Shove the input into the filter, and get the next output..
                	sum += filter[i][j][k].getOutputSample(out[i - 1][k]);
                	//sum += out[i - 1][k] * weight[i][j][k];	// Apply weight to inputs and add to sum
                }
                //TODO Do we want/need a bias?
                //sum += weight[i][j][layersizes[i - 1]];		// Apply bias
                out[i][j] = Util.sigmoid(sum);				// Apply sigmoid function
            }
        }
    }
   
    /*  Not valid any more, need to use temporal backpropogation
    //	backpropogate errors from output
    //	layer uptill the first hidden layer
    void bpgt(float in[], float tgt[]) {
        float sum;

        //	update output values for each neuron
        ffwd(in);

        //	find delta for output layer
        for (int i = 0; i < layersizes[numlayer - 1]; i++) {
            delta[numlayer - 1][i] = out[numlayer - 1][i] *
                    (1 - out[numlayer - 1][i]) * (tgt[i] - out[numlayer - 1][i]);
        }

        //	find delta for hidden layers	
        for (int i = numlayer - 2; i > 0; i--) {
            for (int j = 0; j < layersizes[i]; j++) {
                sum = 0.0f;
                for (int k = 0; k < layersizes[i + 1]; k++) {
                    sum += delta[i + 1][k] * weight[i + 1][k][j];
                }
                delta[i][j] = out[i][j] * (1 - out[i][j]) * sum;
            }
        }

        //	apply momentum ( does nothing if alpha=0 )
        for (int i = 1; i < numlayer; i++) {
            for (int j = 0; j < layersizes[i]; j++) {
                for (int k = 0; k < layersizes[i - 1]; k++) {
                    weight[i][j][k] += alpha * prevDwt[i][j][k];
                }
                weight[i][j][layersizes[i - 1]] += alpha * prevDwt[i][j][layersizes[i - 1]];
            }
        }

        //	adjust weights usng steepest descent	
        for (int i = 1; i < numlayer; i++) {
            for (int j = 0; j < layersizes[i]; j++) {
                for (int k = 0; k < layersizes[i - 1]; k++) {
                    prevDwt[i][j][k] = (float) (beta * delta[i][j] * out[i - 1][k]);
                    weight[i][j][k] += prevDwt[i][j][k];
                }
                prevDwt[i][j][layersizes[i - 1]] = (float) (beta * delta[i][j]);
                weight[i][j][layersizes[i - 1]] += prevDwt[i][j][layersizes[i - 1]];
            }
        }
    }
    */
    
    
    void dump() {
    for (int i = 0; i < numlayer; i++) {
            System.out.println("Layer "+i);
            
            for (int j = 0; j < layersizes[i]; j++) {
                if ( i>0){
                    System.out.println(" Neuron "+j + " ="+ out[i][j] + "  " + delta[i][j]);
                
                
                    for (int k = 0; k < layersizes[i - 1] + 1; k++) {
                        //TODO fix me, perhaps use a full 4-dimensional array to store the filter coeffs? Or use function in filter to return coeffs ;)
                    	//System.out.println("w prev ["+k+"]"+ weight[i][j][k] + " " + prevDwt[i][j][k]);
                    }
                }else {
                    System.out.println(" Neuron "+j + " ="+ out[i][j]);
                }
            }
        }
    }
    
    
    private void readObject(java.io.ObjectInputStream in)
         throws IOException, ClassNotFoundException
    {
            in.defaultReadObject();
    } 
}


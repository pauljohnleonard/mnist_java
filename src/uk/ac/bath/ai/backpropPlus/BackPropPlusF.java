package uk.ac.bath.ai.backpropPlus;

// converted to java by p.j.leonard
// based  on C++ code found at 
// http://www.codeproject.com/KB/recipes/BP.aspx?msg=2809798#xx2809798xx
//

import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;
import uk.ac.bath.util.Tweakable;
import uk.ac.bath.util.TweakableDouble;

public class BackPropPlusF implements Serializable {
    //	output of each neuron
    float out[][];
    //	delta error value for each neuron
    float delta[][];
    //	vector of weights for each neuron
    float weight[][][];
    //	no of layers in net
    //	including input layer
    int numl;
    //	vector of numl elements for size 
    //	of each layer
    int lsize[];
    //	learning rate
    double beta;
    //	momentum parameter
    double alpha;
    //	storage for weight-change made
    //	in previous epoch
    float prevDwt[][][];
    
    transient Vector<Tweakable> tweaks = new Vector<Tweakable>();
    transient private TweakableDouble betaTweak;
    transient private TweakableDouble alphaTweak;

    
    BackPropPlusF(int sz[], double b, double a) {


        beta = b;
        alpha = a;

        makeTweaks();
        //	set no of layers and their sizes
        numl = sz.length;
        lsize = new int[numl];

        for (int i = 0; i < numl; i++) {
            lsize[i] = sz[i];
        }

        //	allocate memory for output of each neuron
        out = new float[numl][];

        for (int i = 0; i < numl; i++) {
            out[i] = new float[lsize[i]];
        }

        //	allocate memory for delta
        delta = new float[numl][];

        for (int i = 1; i < numl; i++) {
            delta[i] = new float[lsize[i]];
        }

        //	allocate memory for weights
        weight = new float[numl][][];

        for (int i = 1; i < numl; i++) {
            weight[i] = new float[lsize[i]][];
        }
        for (int i = 1; i < numl; i++) {
            for (int j = 0; j < lsize[i]; j++) {
                weight[i][j] = new float[lsize[i - 1] + 1];
            }
        }

        //	allocate memory for previous weights
        prevDwt = new float[numl][][];

        for (int i = 1; i < numl; i++) {
            prevDwt[i] = new float[lsize[i]][];

        }
        for (int i = 1; i < numl; i++) {
            for (int j = 0; j < lsize[i]; j++) {
                prevDwt[i][j] = new float[lsize[i - 1] + 1];
            }
        }

        //	seed and assign random weights
        // srand((unsigned)(time(NULL)));

        Random rand = new Random();

        for (int i = 1; i < numl; i++) {
            for (int j = 0; j < lsize[i]; j++) {
                for (int k = 0; k < lsize[i - 1] + 1; k++) {
                    weight[i][j][k] = (float) (rand.nextDouble() - .5);//32767
                //	initialize previous weights to 0 for first iteration
                }
            }
        }

        for (int i = 1; i < numl; i++) {
            for (int j = 0; j < lsize[i]; j++) {
                for (int k = 0; k < lsize[i - 1] + 1; k++) {
                    prevDwt[i][j][k] = 0.0f;// Note that the following variables are unused,
//
// delta[0]
// weight[0]
// prevDwt[0]

//  I did this intentionaly to maintains consistancy in numbering the layers.
//  Since for a net having n layers, input layer is refered to as 0th layer,
//  first hidden layer as 1st layer and the nth layer as output layer. And 
//  first (0th) layer just stores the inputs hence there is no delta or weigth
//  values corresponding to it.
                }
            }
        }
    }

    public Vector<Tweakable> getTweaks() {
        return tweaks;
    }

    float[] output() {
        return out[numl - 1];
    }
    
    float[] input() {
        return out[0];
    }
    
    //	sigmoid function
    float sigmoid(float in) {
        return (float) (1.0 / (1.0 + Math.exp(-in)));
    }

    //	mean square error
    float mse(float tgt[]) {
        float mse = 0;
        for (int i = 0; i < lsize[numl - 1]; i++) {
            mse += (tgt[i] - out[numl - 1][i]) * (tgt[i] - out[numl - 1][i]);
        }
        return mse / 2;
    }
//    //	returns i'th output of the net
//    float Out(int i) {
//        return out[numl - 1][i];
//    }


    // feed forward one set of input
    void ffwd(float in[]) {
        float sum;

        //	assign content to input layer
        for (int i = 0; i < lsize[0]; i++) {
            out[0][i] = in[i];  // output_from_neuron(i,j) Jth neuron in Ith Layer

        //	assign output(activation) value 
        //	to each neuron usng sigmoid func
        }
        for (int i = 1; i < numl; i++) {				// For each layer
            for (int j = 0; j < lsize[i]; j++) {		// For each neuron in current layer
                sum = 0.0f;
                for (int k = 0; k < lsize[i - 1]; k++) {		// For input from each neuron in preceeding layer
                    sum += out[i - 1][k] * weight[i][j][k];	// Apply weight to inputs and add to sum
                }
                sum += weight[i][j][lsize[i - 1]];		// Apply bias
                out[i][j] = sigmoid(sum);				// Apply sigmoid function
            }
        }
    }
    //	backpropogate errors from output
    //	layer uptill the first hidden layer
    void bpgt(float in[], float tgt[]) {
        float sum;

        //	update output values for each neuron
        ffwd(in);

        //	find delta for output layer
        for (int i = 0; i < lsize[numl - 1]; i++) {
            delta[numl - 1][i] = out[numl - 1][i] *
                    (1 - out[numl - 1][i]) * (tgt[i] - out[numl - 1][i]);
        }

        //	find delta for hidden layers	
        for (int i = numl - 2; i > 0; i--) {
            for (int j = 0; j < lsize[i]; j++) {
                sum = 0.0f;
                for (int k = 0; k < lsize[i + 1]; k++) {
                    sum += delta[i + 1][k] * weight[i + 1][k][j];
                }
                delta[i][j] = out[i][j] * (1 - out[i][j]) * sum;
            }
        }

        //	apply momentum ( does nothing if alpha=0 )
        for (int i = 1; i < numl; i++) {
            for (int j = 0; j < lsize[i]; j++) {
                for (int k = 0; k < lsize[i - 1]; k++) {
                    weight[i][j][k] += alpha * prevDwt[i][j][k];
                }
                weight[i][j][lsize[i - 1]] += alpha * prevDwt[i][j][lsize[i - 1]];
            }
        }

        //	adjust weights usng steepest descent	
        for (int i = 1; i < numl; i++) {
            for (int j = 0; j < lsize[i]; j++) {
                for (int k = 0; k < lsize[i - 1]; k++) {
                    prevDwt[i][j][k] = (float) (beta * delta[i][j] * out[i - 1][k]);
                    weight[i][j][k] += prevDwt[i][j][k];
                }
                prevDwt[i][j][lsize[i - 1]] = (float) (beta * delta[i][j]);
                weight[i][j][lsize[i - 1]] += prevDwt[i][j][lsize[i - 1]];
            }
        }
    }
    
    void makeTweaks() {
        betaTweak = new TweakableDouble(0., 20., beta, .00001, "beta");
        alphaTweak = new TweakableDouble(0., 1000., alpha, .1, "alpha");

        betaTweak.addObserver(new Observer() {

            public void update(Observable o, Object arg) {
                beta = betaTweak.doubleValue();
            }
        });


        alphaTweak.addObserver(new Observer() {

            public void update(Observable o, Object arg) {
                alpha = alphaTweak.doubleValue();
            }
        });


        tweaks.add(alphaTweak);
        tweaks.add(betaTweak);
    
    }
    
    private void readObject(java.io.ObjectInputStream in)
         throws IOException, ClassNotFoundException
    {
            in.defaultReadObject();
            makeTweaks();
    } 
}


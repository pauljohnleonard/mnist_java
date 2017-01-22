package uk.ac.bath.ai.test;

// based on C++ code found at 
// http://www.codeproject.com/KB/recipes/BP.aspx?msg=2809798#xx2809798xx
import java.util.Random;
//	initializes and allocates memory on heap
public class BackProp {
//	output of each neuron
    double out[][];//	delta error value for each neuron
    double delta[][];//	vector of weights for each neuron
    double weight[][][];//	no of layers in net
//	including input layer
    int numl;//	vector of numl elements for size 
//	of each layer
    int lsize[];//	learning rate
    double beta;//	momentum parameter
    double alpha;//	storage for weight-change made
//	in previous epoch
    double prevDwt[][][];

    BackProp(int sz[], double b, double a) {
        beta = b;
        alpha = a;


        //	set no of layers and their sizes
        numl = sz.length;
        lsize = new int[numl];

        for (int i = 0; i < numl; i++) {
            lsize[i] = sz[i];
        }

        //	allocate memory for output of each neuron
        out = new double[numl][];

        for (int i = 0; i < numl; i++) {
            out[i] = new double[lsize[i]];
        }

        //	allocate memory for delta
        delta = new double[numl][];

        for (int i = 1; i < numl; i++) {
            delta[i] = new double[lsize[i]];
        }

        //	allocate memory for weights
        weight = new double[numl][][];

        for (int i = 1; i < numl; i++) {
            weight[i] = new double[lsize[i]][];
        }
        for (int i = 1; i < numl; i++) {
            for (int j = 0; j < lsize[i]; j++) {
                weight[i][j] = new double[lsize[i - 1] + 1];
            }
        }

        //	allocate memory for previous weights
        prevDwt = new double[numl][][];

        for (int i = 1; i < numl; i++) {
            prevDwt[i] = new double[lsize[i]][];

        }
        for (int i = 1; i < numl; i++) {
            for (int j = 0; j < lsize[i]; j++) {
                prevDwt[i][j] = new double[lsize[i - 1] + 1];
            }
        }

        //	seed and assign random weights
        // srand((unsigned)(time(NULL)));

        Random rand = new Random();

        for (int i = 1; i < numl; i++) {
            for (int j = 0; j < lsize[i]; j++) {
                for (int k = 0; k < lsize[i - 1] + 1; k++) {
                    weight[i][j][k] = (double) (rand.nextDouble() - .5);//32767

                //	initialize previous weights to 0 for first iteration
                }
            }
        }
        for (int i = 1; i < numl; i++) {
            for (int j = 0; j < lsize[i]; j++) {
                for (int k = 0; k < lsize[i - 1] + 1; k++) {
                    prevDwt[i][j][k] = 0.0;

                // Note that the following variables are unused,
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
//	sigmoid function
    double sigmoid(double in) {
        // return Math.tanh(in);
        return (double) (1.0 / (1.0 + Math.exp(-in)));
    }

//	mean square error
    double mse(double tgt[]) {
        double mse = 0;
        for (int i = 0; i < lsize[numl - 1]; i++) {
            mse += (tgt[i] - out[numl - 1][i]) * (tgt[i] - out[numl - 1][i]);
        }
        return mse / 2;
    }
//	returns i'th output of the net
    double Out(int i) {
        return out[numl - 1][i];
    }

// feed forward one set of input
    void ffwd(double in[]) {
        double sum;

        //	assign content to input layer
        for (int i = 0; i < lsize[0]; i++) {
            out[0][i] = in[i];  // output_from_neuron(i,j) Jth neuron in Ith Layer

        //	assign output(activation) value 
        //	to each neuron usng sigmoid func
        }
        for (int i = 1; i < numl; i++) {				// For each layer
            for (int j = 0; j < lsize[i]; j++) {		// For each neuron in current layer
                sum = 0.0;
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
    void bpgt(double in[], double tgt[]) {
        double sum;

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
                sum = 0.0;
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
                    prevDwt[i][j][k] = beta * delta[i][j] * out[i - 1][k];
                    weight[i][j][k] += prevDwt[i][j][k];
                }
                prevDwt[i][j][lsize[i - 1]] = beta * delta[i][j];
                weight[i][j][lsize[i - 1]] += prevDwt[i][j][lsize[i - 1]];
            }
        }
    }
}


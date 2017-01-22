/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.convolution;

import uk.ac.bath.ai.AbstractBrain;
import uk.ac.bath.ai.Result;
import uk.ac.bath.ai.util.Util;
import uk.ac.bath.ai.image.WeightVectorImage;
import uk.ac.bath.ai.io.DataSource.Data;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import uk.ac.bath.util.TweakableDouble;

/**
 *
 * @author pjl
 * 
 * Replaced with the Layered brain.
 */
@Deprecated
public class ConvolutionBrain extends AbstractBrain {

    /**
     * 
     * Neurons of type at (x,y)         (w,delta)
     *   
     *    start[type] + 0:nx[type]*ny[type]]  
     *    
     * Each type has:
     *     nx[type]*ny[type] positons
     *     nIn[type] inputs  
     * 
     * Position  i'th inout at (x,y)'th neuron of type  is  
     *     ptr[start[type]+i] + x*dx[type] + y*dy[type] 
     * 
     * i'th weight of type (actual node from above) 2D array  
     *       w[type][i]
     *  
     * 
     */
    float delta[]; //  dE / do_j
    float o[];     //  neuron state
    //   private Neuron[] outputLayer;
    //  private Random rand = Util.random();
    private int nType;
    double alpha;
    double beta;
    transient private TweakableDouble betaTweak;
    transient private TweakableDouble alphaTweak;
    int nState;
    int nLayer = 2;
    int iLayer[];
    Neuron layer[][];

    public ConvolutionBrain(Dimension dIn, Dimension dOut) {
        super(dIn, dOut, "Convolution Back Propagation");

        nType = nLabel;

        // neurons on layer = 0 are not used //
        layer = new Neuron[nLayer][];

        nState = nPixel + nLabel;
        o = new float[nState];
        delta = new float[nState];
        iLayer = new int[]{0, nPixel, nPixel + nLabel};

        layer[nLayer - 1] = new Neuron[nLabel];
        alpha = 0.0;
        beta = 0.1;
        makeTweaks();
        for (int i = 0; i < nType; i++) {
            int startOut = nPixel + i;
            //       int nIn = nPixel;
            int ptrIn[] = new int[nPixel];
            for (int j = 0; j < nPixel; j++) {
                ptrIn[j] = j;
            }
            int nx = 1;
            int ny = 1;
            int dx = 0;
            int dy = 0;
            layer[nLayer - 1][i] = new Neuron(startOut, nPixel, ptrIn, nx, ny, dx, dy);
            layer[nLayer - 1][i].init();
            WeightVectorImage image = new WeightVectorImage(layer[nLayer - 1][i].w, dIn, "Hid" + i);
            imagination.add(image);
        }

    }
//     public String outString() {
//        float out[] = bp.output();
//        String ret = "";
//        for (int i = 0; i < layerSizes[layerSizes.length - 1]; i++) {
//            ret += "|" + i + ":" + String.format("%3.2f ", out[i]);
//        }
//        return ret;
//    }
    public void fire(Data data) {
        System.arraycopy(data.image, 0, o, 0, nPixel);

        for (int i = 1; i < nLayer; i++) {
            for (Neuron n : layer[i]) {
                n.fire();
            }
        }

        loadOut(data);
        setChanged();

    }

    public void setTrain(Data data) {
        System.arraycopy(data.image, 0, o, 0, nPixel);

        // feed forward ---------------------------------------------

        for (int i = 1; i < nLayer; i++) {
            for (Neuron n : layer[i]) {
                n.fire();
            }
        }

        // reset the deltas because we use them for a running summation
        for (int i = 0; i < nState; i++) {
            delta[i] = 0.0f;
        }

        
        int outIndex=0;
        for (Neuron n:layer[nLayer-1]) {
            n.calcDeltaOut(data.label[outIndex++]);
        }


        for (int i=nLayer-2; i>0;i++) {
            for (Neuron n:layer[i]) {
                n.calcDeltaIn();
             }
        }

        // 
//        for (int i = 0; i < outputLayer.length; i++) {
//            outputLayer[i].calcDeltaIn();
//        }

//        for (int i = 0; i < nPixel; i++) {
//            delta[i] = o[i] * (1.0f - o[i]) * delta[i];
//        }

        for (int i = 1; i < nLayer; i++) {
            for (Neuron n:layer[i]) {
                    n.updateWeights();
             }
        }

        loadOut(data);
        setChanged();
    }

    /**
     * copy cached result into result supplied by caller
     * 
     * @param result   overwritten with current cached result
     */
    public synchronized void getLatestResult(Result res) {
        if (this.result == null) {
            return;
        }

        System.arraycopy(this.result.in.label, 0, res.in.label, 0, nLabel);
        System.arraycopy(this.result.in.image, 0, res.in.image, 0, nPixel);

        System.arraycopy(this.result.out.label, 0, res.out.label, 0, nLabel);
    //    System.arraycopy(this.result.out.image, 0, res.out.image, 0, nImage);

    }

    private synchronized void loadOut(Data data) {
        //      System.arraycopy(bp.output(), nImage,result.out.label, 0, nLabel);
        System.arraycopy(o, nPixel, result.out.label, 0, nLabel);
        System.arraycopy(data.label, 0, result.in.label, 0, nLabel);
        System.arraycopy(data.image, 0, result.in.image, 0, nPixel);
    }

    public class Neuron {

        int startOut;
        float w[];     // weights w[]=wglobal[id][]   
        float prevDw[];
        int nIn;
        int ptrIn[];     // ptr to inputs
        int nx;        //  nx*ny  outputs 
        int ny;        //
        int dx;        //  spacing 
        int dy;

        Neuron(int startOut, int nIn, int ptrIn[], int nx, int ny, int dx, int dy) {
            this.nIn = nIn;
            this.ptrIn = ptrIn;
            this.nx = nx;
            this.ny = ny;
            this.dx = dx;
            this.dy = dy;
            this.startOut = startOut;
            // extra slot for the basis
            w = new float[nIn + 1];
            prevDw = new float[nIn + 1];
        }

        void init() {
            for (int i = 0; i < (nIn + 1); i++) {
                w[i] = (float) (rand.nextDouble() - .5);//32767
            }
        }

        void fire() {

            int outPtr = startOut;
            // visit each placement of the neuron
            for (int x = 0; x < nx; x++) {
                for (int y = 0; y < ny; y++, outPtr++) {
                    int off = x * dx + y * dy;
                    float sum = w[nIn];   // basis input
                    for (int i = 0; i < nIn; i++) {
                        int k = ptrIn[i] + off;  // i global index of input
                        assert (k == i);
                        sum += w[i] * o[k];
                    }
                    o[outPtr] = Util.sigmoid(sum);
                }
            }

        }
        //	backpropogate errors from output
        //	layer uptill the first hidden layer
        void calcDeltaOut(float tgt) {

            assert (nx * ny == 1);

            int outPtr = startOut;

            delta[outPtr] = o[outPtr] * (1 - o[outPtr]) * (tgt - o[outPtr]);

        }

        void calcDeltaIn() {

            //	find contributions to delta on previous layer

            int outPtr = startOut;

            for (int x = 0; x < nx; x++) {
                for (int y = 0; y < ny; y++, outPtr++) {

                    float deltaOut = delta[outPtr];

                    int off = x * dx + y * dy;

                    for (int i = 0; i < nIn; i++) {
                        int k = ptrIn[i] + off;   // k is id of input neuron
                        delta[k] += deltaOut * w[i];
                    }
                }
            }
        }

        void updateWeights() {


            //	apply momentum ( does nothing if alpha=0 )
            for (int i = 0; i < nIn + 1; i++) {
                w[i] += alpha * prevDw[i];
                prevDw[i] = 0.0f;
            }

            //	adjust weights usng steepest descent	

            int dPtr = startOut;

            for (int x = 0; x < nx; x++) {
                for (int y = 0; y < ny; y++, dPtr++) {

                    int off = x * dx + y * dy;

                    // Average over all instances of this type
                    for (int i = 0; i < nIn; i++) {
                        int k = ptrIn[i] + off;   // k is id of input neuron
                        prevDw[i] += (float) (beta * delta[dPtr] * o[k]);
                    }

                    prevDw[nIn] += (float) (beta * delta[dPtr]);
                }
            }

            for (int i = 0; i < nIn + 1; i++) {
                w[i] += prevDw[i];
            }

        //           prevDw[nIn] = (float) (beta * delta[dPtr]);

        }
    }

    public String outString() {
        return " TODO";
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

    public void dump() {


        for (int i = 0; i < nLayer; i++) {


            System.out.println("Layer " + i);

            int k = iLayer[i];
            for (int j = 0; k < iLayer[i + 1]; j++, k++) {
                System.out.println(" Neuron " + j + " =" + o[k] + "   " + delta[k]);
                if (i > 0) {
                    Neuron n = layer[i][j];

                    for (int l = 0; l < n.w.length; l++) {
                        System.out.println("w prev [" + k + "]" + n.w[l] + " " + n.prevDw[l]);
                    }
                }
            }
        }
    }

  
}
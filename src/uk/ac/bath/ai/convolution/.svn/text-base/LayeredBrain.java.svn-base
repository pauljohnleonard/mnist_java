/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.convolution;

import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import uk.ac.bath.ai.AbstractBrain;
import uk.ac.bath.ai.gui.LayoutHint;
import uk.ac.bath.ai.gui.LayoutHinter;
import uk.ac.bath.ai.image.DisplayImage;
import uk.ac.bath.ai.image.OffFloatVectorImage;
import uk.ac.bath.ai.image.WeightVectorImage;
import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.MyDimension;
import uk.ac.bath.ai.util.Result;
import uk.ac.bath.ai.util.Util;
import uk.ac.bath.util.TweakableDouble;

/**
 *
 * @author pjl
 */
public class LayeredBrain extends AbstractBrain implements LayoutHinter {

    static long serialVersionUID=0L;

    transient float delta[][]; //  dE / do_j
    float o[][];     //  neuron state
    double alpha;
    double beta;
    transient private TweakableDouble betaTweak;
    transient private TweakableDouble alphaTweak;
    int nLayer;
    Layer layers[];
    int layerSizes[];
    Dimension dImage[];
    transient public LayoutHint hint;
    LayerConfig configs[];

    public LayeredBrain() {  

    }
    
    public LayeredBrain(MyDimension dIn, MyDimension dOut, LayerConfig configs[],double beta,double alpha) {
        super(dIn, dOut, "Convolution Back Propagation");
        
        // assert(dIn.equals(configs[0].dIn));
        // assert(configs[configs.length-1].dOut.equals(dOut));
        this.configs = configs;
        this.alpha = alpha;
        this.beta = beta;
        init();
    }

    public LayeredBrain(MyDimension dSrc, MyDimension dOut, LayerConfig[] config) {
    	this(dSrc,dOut,config,40.0,.0001);
	}

	NetRep getNetrep(){
    	NetRep rep= new NetRep();
    	
    	float z=0;
    	
    	
    	for (LayerConfig l:configs){
    		
    		
    		
    	}
    	
    	return rep;
    	
    }
    void init() {


        int nHiddenLayer = configs.length - 1; // getNumberOfHiddenLayers();

        float z=0;
        
        layerSizes = new int[2 + nHiddenLayer];

        layerSizes[0] = nPixel;    // from dIn
        layerSizes[1 + nHiddenLayer] = nLabel;
        for (int i = 0; i < nHiddenLayer; i++) {
            layerSizes[1 + i] = configs[i].getSize();
        }


        nLayer = layerSizes.length - 1;

        // cosmetic brain dispaly stuff
        // set dimension to null to disable display
        dImage = new Dimension[nLayer];

        dImage[0] = dPixel;

        // state stuff
        delta = new float[nLayer + 1][];
        o = new float[nLayer + 1][];

        // now make the layers
        layers = new Layer[nLayer];

        for (int i = 0; i < nLayer + 1; i++,z+=1) {
            int n = layerSizes[i];
            o[i] = new float[n];

            if (i > 0) {
                delta[i] = new float[n];
                LayerConfig config = configs[i - 1];
                layers[i - 1] = new Layer("layer" + i, delta[i - 1], delta[i], o[i - 1], o[i],
                        config,z);

            }
        }


        makeTweaks();
    }


    public float [] fire(float image[]) {
        System.arraycopy(image, 0, o[0], 0, nPixel);

        for (Layer l : layers) {
            l.fire();
        }

        return o[nLayer];
        
    }

    public float [] doTraining(float [] image,float [] label) {
        System.arraycopy(image, 0, o[0], 0, nPixel);

        // feed forward ---------------------------------------------

        for (Layer l : layers) {
            l.fire();
        }

        layers[nLayer - 1].calcDeltaOut(label);

        for (int i = nLayer - 1; i > 0; i--) {
            Layer l = layers[i];
            l.backProp();
        }

        for (Layer l : layers) {
            l.updateWeights();
        }

        return o[nLayer];
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
        //       throw new UnsupportedOperationException("Not supported yet.");
    }

    public LayoutHint layoutHint() {
        return hint;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(configs);
        out.writeDouble(alpha);
        out.writeDouble(beta);
        for (Layer lay:layers) {
            for(Layer.Neuron n:lay.neurons) {
                out.writeObject(n.w);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        configs = (LayerConfig[]) in.readObject();
        alpha =in.readDouble();
        beta=in.readDouble();

        // construct the structure
        init();
        for (Layer lay:layers) {
            for(Layer.Neuron n:lay.neurons) {
                n.w = (float[]) in.readObject();
            }
        }



    }

    public class Layer  {

        float deltaIn[];  //  dE / do_j
        float deltaOut[]; //  dE / do_j
        float out[];      //  neuron state
        float in[];       //  neuron state
        //   int nState;
        Neuron neurons[];
//        int nIn;
        //       int nOut;
        boolean fix;
        private String name;

        public Layer(String name, float deltaIn[], float deltaOut[],
                float in[], float out[], LayerConfig config,float z) {

            this.name = name;
            this.fix = config.fix;
            this.in = in;
            this.out = out;
            this.deltaOut = deltaOut;
            this.deltaIn = deltaIn;

            neurons = new Neuron[config.neurons.length];

            int outPtr = 0;
            for (int i = 0; i < neurons.length; i++) {

                System.out.println(name + " unitOutSize=" + config.dUnitOut.n + "  modulations[" + i + "]=" + config.neurons[i].mod.length);
                neurons[i] = new Neuron(outPtr, config.neurons[i], fix);

                neurons[i].init();

                if (config.displayWeights) {
                    DisplayImage image = new WeightVectorImage(neurons[i].w, config.dKernel, "Hid" + i);
                    addImage(image);
                }

                if (config.displayOut) {
                    DisplayImage image = new OffFloatVectorImage(out, outPtr, config.dUnitOut, "Out" + i);
                    addImage(image);
                }
                outPtr += config.neurons[i].mod.length;
            }

        }

        public void fire() {
            // System.arraycopy(data.image, 0, o, 0, nPixel);

            //        System.out.println(" Firing " + name);
            for (Neuron n : neurons) {
                n.fire();
            }
        }

        public void backProp() {
            // reset the deltas because we use them for a running summation

            Arrays.fill(deltaIn, 0.0f);
            if (fix) {
                return;
            }
            for (Neuron n : neurons) {
                n.calcDeltaIn();
            }
        }

        public void updateWeights() {
            if (fix) {
                return;
            }
            for (Neuron n : neurons) {
                n.updateWeights();
            }
        }

        public void calcDeltaOut(float target[]) {
            int i = 0;
            for (Neuron n : neurons) {
                n.calcDeltaOut(target[i++]);
            }
        }

        public void writeExternal(ObjectOutput out) throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * 
         * Only one neuron for each set of weights.
         * 
         * For a convolution network each neuron represents many connections defined by
         * the modulations
         * 
         *  For the i'th modulation  
         * 
         *  output ---> o[layer+1][startOut+i]
         * 
         *  For j'th input
         *  
         *  inputs ---> o[layer][modulation[i]+ptrIn[j]]      
         * 
         * @author eespjl
         *
         */
        public class Neuron {

            int startOut;
            float w[];     // weights w[]=wglobal[id][]   
            float prevDw[];
            int nIn;
            int ptrIn[];     // ptr to inputs
            int modulations[];
            boolean fix;

            Neuron(int startOut, LayerConfig.NeuronConfig conf, boolean fix) {

                ptrIn = conf.ptr;
                modulations = conf.mod;
                assert (startOut + conf.mod.length <= out.length);
                this.nIn = ptrIn.length;
                this.startOut = startOut;
                // extra slot for the basis
                w = new float[nIn + 1];
                prevDw = new float[nIn + 1];
                this.fix = fix;
            }

            void init() {
                if (fix) {
                    for (int i = 0; i < (nIn); i++) {
                        w[i] = 1.0f;
                    }
                } else {
                    for (int i = 0; i < (nIn + 1); i++) {
                        w[i] = (float) (rand.nextDouble() - .5);
                    }
                }
            }

            void fire() {

                int outPtr = startOut;
                // visit each placement of the neuron
                for (int off : modulations) {
                    float sum = w[nIn];   // basis input
                    for (int i = 0; i < nIn; i++) {
                        int k = ptrIn[i] + off;  // i global index of input
                        if (k >= in.length) {
                            System.out.println("k > in " + k + " " + in.length + "   " + off + " " + name);
                        }
                        if (i >= w.length) {
                            System.out.println("i > w " + i + "  " + w.length);
                        }
                        //    assert (k == i);
                        sum += w[i] * in[k];
                    }


                    out[outPtr++] = Util.sigmoid(sum);
                }
            }
            //	backpropogate errors from output
            //	layer uptill the first hidden layer

            void calcDeltaOut(float tgt) {

                assert (modulations.length == 1);
                int outPtr = startOut;

                deltaOut[outPtr] = out[outPtr] * (1 - out[outPtr]) * (tgt - out[outPtr]);
            }

            void calcDeltaIn() {

                // contribute to delta on previous layer deltaIn
                // (called if we need delta for a layer nearer input)

                int outPtr = startOut;

                for (int off : modulations) {

                    float delta_k = deltaOut[outPtr++];

                    for (int i = 0; i < nIn; i++) {
                        int j = ptrIn[i] + off;   // j is id of input neuron
                        float o_j = in[j];
                        deltaIn[j] += delta_k * w[i] * o_j * (1.0f - o_j);
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

                for (int off : modulations) {
                    float d_out = deltaOut[dPtr++];

                    // Average over all instances of this type
                    for (int i = 0; i < nIn; i++) {
                        int k = ptrIn[i] + off;   // k is id of input neuron
                        prevDw[i] += (float) (beta * d_out * in[k]);
                    }

                    prevDw[nIn] += (float) (beta * d_out);
                }


                for (int i = 0; i < nIn + 1; i++) {
                    w[i] += prevDw[i];
                }

            }
        }
    }
}

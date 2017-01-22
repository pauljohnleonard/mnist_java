package uk.ac.bath.ai.convolution;

import uk.ac.bath.ai.util.MyDimension;
import uk.ac.bath.ai.gui.LayoutHint;
import java.awt.Dimension;
import java.io.Serializable;

public class LayerConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NeuronConfig neurons[];
	boolean fix = false;

	MyDimension dKernel;       // size of kernel
	MyDimension dUnitOut;      // size of each kernels output
	
	boolean displayWeights = false;
	boolean displayOut = false;

	class NeuronConfig implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		int[] mod;
		int[] ptr;

		private NeuronConfig(int[] ptr1, int[] mods1) {
			mod = mods1;
			ptr = ptr1;
		}
	}

	/**
	 * 
	 * treat previous layer as 1 big block create nUnit fully connected outputs
	 * 
	 * @param prevLayer
	 * @param nUnit
	 */
	private LayerConfig(LayerConfig prevLayer, int nOut) {

		dKernel = new MyDimension(nOut, prevLayer.dUnitOut.n);
		dUnitOut = new MyDimension(1, 1);

		neurons = new NeuronConfig[nOut];

		// All units share same input pointersand modulations
		System.out.println(" Prev layer " + prevLayer.getSize());

		int[] ptr1 = new int[prevLayer.getSize()];
		int[] mods1 = new int[1];

		for (int k = 0; k < ptr1.length; k++) {
			ptr1[k] = k;
		}

		mods1[0] = 0;

		for (int i = 0; i < nOut; i++) {
			neurons[i] = new NeuronConfig(ptr1, mods1);
		}
		displayOut = false;
		displayWeights = false;

	}

	/**
	 * create layer parameters for units of same dimension as the source image.#
	 * No convolution
	 * 
	 * @param dSrc
	 * @param nUnit
	 */
	private LayerConfig(MyDimension dSrc, int nNeuron) {
		super();

		dKernel = dSrc;
		dUnitOut = new MyDimension(1, 1);
		// this.nNeuron = nNeuron;
		// MyDimension dUnitOut = new MyDimension(dSrc.width - dUnitIn.width +
		// 1, dSrc.width - dUnitIn.width + 1);

		// hiddenLayerSize = nUnit;

		neurons = new NeuronConfig[nNeuron];

		// All units share same input pointersand modulations
		int[] ptr1 = new int[dSrc.n];
		int[] mods1 = new int[1];

		for (int k = 0; k < dSrc.n; k++) {
			ptr1[k] = k;
		}

		mods1[0] = 0;

		for (int i = 0; i < nNeuron; i++) {
			neurons[i] = new NeuronConfig(ptr1, mods1);
		}
	}

	/**
	 * This is the interesting one.
	 * 
	 * @param dSrc
	 *            dimension of the input image
	 * @param dKernel
	 *            dimension of each kernel.
	 * @param nUnit
	 *            number of patches.
	 * 
	 */
	private LayerConfig(MyDimension dSrc, MyDimension dKernel, int nNeuron) {
		super();
		this.dKernel = dKernel;

		dUnitOut = new MyDimension(dSrc.width - dKernel.width + 1, dSrc.height
				- dKernel.height + 1);
		neurons = new NeuronConfig[nNeuron];

		// All units share same input pointers and modulations
		int[] ptr1 = new int[dKernel.n];
		int[] mods1 = new int[dUnitOut.n];

		int k = 0;
		for (int j = 0; j < dKernel.height; j++) {
			for (int i = 0; i < dKernel.width; i++, k++) {
				ptr1[k] = dSrc.index(i, j);
			}
		}

		k = 0;
		for (int j = 0; j < dUnitOut.height; j++) {
			for (int i = 0; i < dUnitOut.width; i++, k++) {
				mods1[k] = dSrc.index(i, j);
			}
		}
		for (int i = 0; i < nNeuron; i++) {
			neurons[i] = new NeuronConfig(ptr1, mods1);
		}

	}

	int getSize() {
		return neurons.length * dUnitOut.n;
	}

	
	//******************************************************************************
	// helpers to create layers
	//******************************************************************************

	static LayerConfig createFullyConnectedInput(MyDimension dSrc, int nNeuron) {
		return new LayerConfig(dSrc, nNeuron);
	}

	static LayerConfig createConvolution(MyDimension dSrc, MyDimension dKernel,
			int nNeuron) {
		return new LayerConfig(dSrc, dKernel, nNeuron);
	}

	
	static LayerConfig createFullyConnectedToPrevLayer(LayerConfig prevLayer,
			int nOut) {
		return new LayerConfig(prevLayer, nOut);
	}

	
	// ********************************************************************************
	// Helpers to create complete brains
	// ****************************************************************************

	/**
	 * Create an FIR style brain
	 * 
	 * source has dimension - nFeature x nTimeSlots the layer i is defined by
	 * two arrays nTaps[i] -- number of taps in the filter nKernel[i] -- number
	 * of filters.
	 * 
	 * Final connection layer is dense to 
	 * 
	 * nTarget --- number of classifications
	 * 
	 * 
	 * @param dSrc
	 * @param kernelWidth
	 * @param nKernel
	 * @return
	 * 
	 */

	static public LayeredBrain createFIRBrain(MyDimension dSrc, MyDimension dOut,int nTaps,
			int nKernel, float beta, float alpha) {
		System.out.println(" Creating FIR brain. Source is " + dSrc.width
				+ " x " + dSrc.height);

		// connection 1.
		// convolution with nKernels
		//
		int i = 0;
		MyDimension kDim = new MyDimension(dSrc.width, nTaps);
		LayerConfig l1 = new LayerConfig(dSrc, kDim, nKernel);
		
		// second layer has a feature for each kernel.
		// the length in time is reduced because of convolution
		MyDimension l2Dim = new MyDimension(nKernel, dSrc.height-nTaps+1);

		int nTarget=dOut.n;
		
		LayerConfig l2 = new LayerConfig(l2Dim, nTarget);
		

		LayerConfig config[] = new LayerConfig[2];

		config[0]=l1;
		config[1]=l2;
		
		LayeredBrain brain = new LayeredBrain(dSrc, dOut, config,beta,alpha);

		return brain;

	}

	static public LayeredBrain create3Layer(MyDimension dIn, MyDimension dOut,
			int nHidden, float beta, float alpha) {

		LayerConfig config[] = new LayerConfig[2];

		config[0] = new LayerConfig(new MyDimension(dIn), nHidden);
		config[0].displayWeights = true;

		config[1] = new LayerConfig(new MyDimension(1, nHidden), dOut.width
				* dOut.height);

		LayeredBrain brain = new LayeredBrain(dIn, dOut, config, beta, alpha);

		return brain;
	}

	static public LayeredBrain createSillySimple3Layer(MyDimension dIn,
			MyDimension dOut, int nType, float beta, float alpha) {

		// nType = 1;
		LayerConfig config[] = new LayerConfig[2];

		MyDimension dKernel = new MyDimension(1, 1);

		config[0] = new LayerConfig(new MyDimension(dIn), dKernel, nType);

		config[0].displayWeights = true;
		config[0].displayOut = true;

		config[1] = new LayerConfig(new MyDimension(dIn), dOut.width
				* dOut.height);
		config[1].dKernel = config[0].dUnitOut;
		config[1].displayWeights = true;
		// config[0].displayOut=true;

		LayeredBrain brain = new LayeredBrain(dIn, dOut, config, beta, alpha);

		return brain;
	}

	/**
	 * 
	 * @param dIn
	 *            pixel image dimension
	 * @param dOut
	 *            output dimmension
	 * @param nKernel
	 *            number of kernels
	 * @param nK
	 *            kenrel size is (nK x nK)
	 * @return shiney new brain
	 * 
	 */
	static public LayeredBrain create3LayerConvolution(MyDimension dIn,
			MyDimension dOut, int nKerenel, int nK, double beta, double alpha) {

		// int nNeuron = 4;
		LayerConfig config[] = new LayerConfig[2];

		MyDimension dKernel = new MyDimension(nK, nK);

		config[0] = new LayerConfig(new MyDimension(dIn), dKernel, nKerenel);
		config[0].displayWeights = true;
		config[0].displayOut = true;

		config[1] = new LayerConfig(config[0], dOut.height * dOut.width);

		LayeredBrain brain = new LayeredBrain(dIn, dOut, config, beta, alpha);
		brain.hint = new LayoutHint(10);
		return brain;
	}

	/**
	 * 
	 * 
	 * Same as create3LayerConvolution with an extra hidden layer before the
	 * outout.
	 * 
	 */
	static public LayeredBrain create4LayerConvolution(MyDimension dIn,
			MyDimension dOut, int nNeuron, int nK, int nHidden, double beta,
			double alpha) {

		LayerConfig config[] = new LayerConfig[3];

		MyDimension dKernel = new MyDimension(nK, nK);

		config[0] = new LayerConfig(new MyDimension(dIn), dKernel, nNeuron);
		config[0].displayWeights = true;
		config[0].displayOut = true;

		config[1] = new LayerConfig(config[0], nHidden);

		config[2] = new LayerConfig(config[1], dOut.height * dOut.width);

		LayeredBrain brain = new LayeredBrain(dIn, dOut, config, beta, alpha);
		brain.hint = new LayoutHint(10);
		return brain;
	}
}

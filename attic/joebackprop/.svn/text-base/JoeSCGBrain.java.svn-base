/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.joebackprop;

import uk.ac.bath.ai.*;
import uk.ac.bath.ai.gui.ImageLabel;
import uk.ac.bath.ai.gui.Image;
import uk.ac.bath.ai.FloatVectorImage;
import uk.ac.bath.ai.io.DataSource;
import uk.ac.bath.ai.io.DataSource.Data;
import jahuwaldt.tools.NeuralNets.BasicNeuronFactory;
import jahuwaldt.tools.NeuralNets.FeedForwardNetSCG;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Vector;
import uk.ac.bath.util.Tweakable;


/**
 *
 * @author pjl
 */
public class JoeSCGBrain extends AbstractBrain implements  BatchTrainable {

    FeedForwardNetSCG myFFNet2;
    private int n;
    private Image[] imagination;
    private ImageLabel reality;
    static int nLabels = 10;

    public JoeSCGBrain(Dimension d) {
        super(d,null,"JOES");
        this.n = d.height * d.width;
        int nHidden = 40;
        imagination = new Image[nHidden];
//    
//        double beta=.1;
//        double alpha=5;
//  

//        layerSizes=new int[]{n,nHidden,20,10};
        // Create a feedforward network with scaled conjugate gradient training using BasicNeurons.
        // int nHidden=20;

        myFFNet2 = new FeedForwardNetSCG(n, nLabels, 1, nHidden, new BasicNeuronFactory());

        for (int i = 0; i < nHidden; i++) {
            imagination[i] = new WeightVectorImage(myFFNet2.getWeights(0, i), d,"Hid"+i);
        }
        
        reality=new ImageLabel(new FloatVectorImage(d),-1);
    }

    public Image getImagination(int i) {
        return imagination[i];
    }

    public int getImaginationCount() {
        return imagination.length;
    }

    public ImageLabel getReality() {
        return reality;
    }
//    static float labs[][];
//    
//
//    static {
//        labs = new float[nLabels][];
//
//        for (int i = 0; i < 10; i++) {
//            labs[i] = new float[10];
//            labs[i][i] = 1.0f;
//        }
//
//    }

    public void setTrain(ImageLabel il) {
        throw new UnsupportedOperationException("Not supported for this type of brain use batch training.");

    //        int label = il.label;
//        reality = il;
//        setChanged();
    }

    public String outString() {
        float out[] = myFFNet2.getOutputs();
        String ret = "";
        for (int i = 0; i < out.length; i++) {
            ret += "|" + i + ":" + String.format("%3.2f ", out[i]);
        }
        return ret;
    }

    public void fire(Data data) {

        myFFNet2.setInputs(data.image);

        reality.setData(data);
        setChanged();
       // return myFFNet2.getOutputs();
    }

    public void setTrain(Data data) {
        throw new UnsupportedOperationException("Not supported for this type of brain use batch training.");
    }

    public void doBatchTrain(DataSource src) {
        Trainer t = new Trainer(src,src.getSize());
        float tol=.1f;
        myFFNet2.train(t, tol,src.getSize());
        setChanged();
    }

    public Vector<Tweakable> getTweaks() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    public ImageLabel getFantasy() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

//    public void feedForward(Data data) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    public void getLatestResult(Result result) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void dump() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

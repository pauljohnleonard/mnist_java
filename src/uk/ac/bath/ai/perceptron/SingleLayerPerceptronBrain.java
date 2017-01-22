/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.perceptron;

import uk.ac.bath.ai.image.WeightVectorImage;
import uk.ac.bath.ai.*;
import uk.ac.bath.ai.gui.ImageLabel;
import uk.ac.bath.ai.image.FloatVectorImage;
import uk.ac.bath.ai.gui.LayoutHint;
import uk.ac.bath.ai.io.DataSource;
import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.Result;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import uk.ac.bath.util.Tweakable;
import uk.ac.bath.util.TweakableDouble;

/**
 *
 * @author pjl
 */
public class SingleLayerPerceptronBrain extends AbstractBrain { //implements LayoutHinter {
    
    Perceptron layer[];
    double accel = 0.001;
    private float[] out;
    transient private TweakableDouble accelTweak;
  
    public SingleLayerPerceptronBrain(Dimension dImage, Dimension d2) {
        super(dImage, d2, "Single Layer Perceptron");
        makeTweaks();

        layer = new Perceptron[nLabel];

        out = new float[nLabel];
        for (int i = 0; i < nLabel; i++) {
            layer[i] = new Perceptron(nPixel);
            imagination.add(new WeightVectorImage(layer[i].w, dImage, "Perceptron Wieghts"));
        }
    }

    public float [] doTraining(float image[],float [] label) {


        int count = 0;
   
        for (Perceptron pp : layer) {
            
            out[count] = pp.train(image, label[count], accel);
            count++;
        }

        return out;
    }

    public LayoutHint layoutHint() {
        return new LayoutHint(10);
    }

    public float [] fire(float in[]) {

        for (int i = 0; i < nLabel; i++) {
            out[i] = layer[i].fire(in);
        }

    
        return out;
    }

//    public String outString() {
//        String str = "";
//        for (Integer i : outlist) {
//            str += i;
//        }
//        return str;
//    }
    public Vector<Tweakable> getTweaks() {
        return tweaks;
    }

//    public synchronized void getLatestResult(Result res) {
//        if (this.result == null) {
//            return;
//        }
//        System.arraycopy(this.result.in.label, 0, res.in.label, 0, nLabel);
//        System.arraycopy(this.result.in.image, 0, res.in.image, 0, nPixel);
//
//        System.arraycopy(this.result.out.label, 0, res.out.label, 0, nLabel);
//        System.arraycopy(this.result.out.image, 0, res.out.image, 0, nPixel);
//
//    }

    void makeTweaks() {
        accelTweak = new TweakableDouble(0., 20., accel, .00001, "beta");

        accelTweak.addObserver(new Observer() {

            public void update(Observable o, Object arg) {
                accel = accelTweak.doubleValue();
            }
        });

        tweaks.add(accelTweak);

    }

    public void dump() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

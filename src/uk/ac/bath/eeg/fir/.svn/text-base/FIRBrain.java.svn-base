/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.eeg.fir;

import uk.ac.bath.ai.image.WeightVectorImage;
import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.Result;
import uk.ac.bath.ai.*;

import java.awt.Dimension;
import java.util.Vector;
import uk.ac.bath.util.Tweakable;

/**
 *
 * @author pjl, bwm23
 */
public class FIRBrain extends AbstractBrain {


    FIRF bp;
    int layerSizes[];
 //   private boolean debug=true;
  

    public FIRBrain(Dimension d, Dimension dLab,int hidden[],double beta) {
        super(d,dLab,"FIR - MLP");

   

        layerSizes = new int[2+hidden.length];
        
        layerSizes[0]=nPixel;
        for (int i=1;i<=hidden.length;i++) {
            layerSizes[i]=hidden[i-1];
        }
        
        layerSizes[layerSizes.length-1]=nLabel;

        // Creating the net
        bp = new FIRF(layerSizes, beta, rand);

        //TODO What does this do??
        //for (int i = 0; i < layerSizes[1]; i++) {
        //    imagination.add(new WeightVectorImage(bp.weight[1][i], d, "pre" + i));
        //}

    }

    public String outString() {
        float out[] = bp.output();
        String ret = "";
        for (int i = 0; i < layerSizes[layerSizes.length - 1]; i++) {
            ret += "|" + i + ":" + String.format("%3.2f ", out[i]);
        }
        return ret;
    }

    public float [] fire(float [] image) {

        bp.ffwd(image);
 
        return bp.output();
    // return bp.output();
    }

    public float [] doTraining(float [] image,float[] label) {
        //bp.bpgt(image,label);
  
        return  bp.output();
    }

    public Vector<Tweakable> getTweaks() {
        return bp.getTweaks();
    	//throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void dump() {
        bp.dump();
    }

    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

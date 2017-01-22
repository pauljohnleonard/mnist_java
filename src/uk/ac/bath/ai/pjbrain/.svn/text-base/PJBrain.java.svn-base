/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.pjbrain;

import java.io.InputStream;
import java.io.OutputStream;
import uk.ac.bath.ai.AbstractBrain;
import uk.ac.bath.ai.Data;
import uk.ac.bath.ai.DisplayImage;
import uk.ac.bath.ai.Result;
import uk.ac.bath.ai.image.WeightVectorImage;
import uk.ac.bath.ai.gui.Image;
import uk.ac.bath.ai.gui.ImageLabel;
; 
import java.awt.Dimension;
import java.util.Vector;
import uk.ac.bath.util.Tweakable;

/**
 *
 * @author pjl
 */
public class PJBrain extends  AbstractBrain {

    DisplayImage image[];
    float w[][];
    int nImagines;
    private ImageLabel reality;
    // int n;
    int nNeuron;
    int nIn;
    int nOut;
    private float[] x;
 //   private float[] z;
    private float[] o;
    private float[] out;
 //   private float[] error;
//    private float[] delta;
    private float beta;
    int n;
    private float[] f;
    private float[] error;
   
    public PJBrain(Dimension din, Dimension dout) {
        super(din,dout,"PJ Brain");
        
        // TODO delete these 
        nIn = din.width * din.height;
        nOut = dout.width * dout.height;    
        n = nIn + nOut;
        
        nNeuron = 10;

        f = new float[n];
        o = new float[nNeuron+1];
        error = new float[n];
        o[nNeuron]=1.0f;
        w = new float[nNeuron+1][];
        image = new DisplayImage[nNeuron+1];
        out = new float[nOut];

        x = new float[n+1];
        x[n]=1.0f;
        
        beta=.01f;
       // nActive=0;
        
        for (int i = 0; i < nNeuron+1; i++) {
            w[i] = new float[n+1]; 
            if (i == 0) image[i] = new WeightVectorImage(f, din,"fantasy");
            else image[i] = new WeightVectorImage(w[i-1], din,"Image"+i);
        }
       
    }

    public DisplayImage getImagination(int i) {
        return image[i];
    }

    public int getImaginationCount() {
        return nNeuron;
    }

    public ImageLabel getReality() {
        return reality;
    }

    public Vector<Tweakable> getTweaks() {
        return null;  //throw new UnsupportedOperationException("Not supported yet.");
    }
    //	sigmoid function
    static float sigmoid(float in) {
        return (float) (1.0 / (1.0 + Math.exp(-in)));
    }

    
    
    
    public void fire(Data data) {
        createIn(data);
        forwardProject();
        createOut();
       //  return out;
    }
 
    private void createOut() {
        System.arraycopy(f, nIn, out, 0, nOut);
    }
    
    private void createIn(Data data) {
        System.arraycopy(data.image, 0, x, 0, nIn);
        System.arraycopy(data.label, 0, x, nIn, nOut);
        
    }
    
    private void forwardProject() {
        assert(x[x.length-1] == 1.0f);
        for (int i = 0; i < nNeuron; i++) {
            float sum = 0f;
            for (int j = 0; j < n+1; j++) {
                sum += w[i][j] * x[j];
            }
//            zo[i] = sum;
            o[i] = sigmoid(sum);
        }
    }
    
    
    public void backProject() {
       
        for (int i = 0; i < n ; i++) {
            float sum= 0f;
         
            for (int j = 0; j < nNeuron+1; j++) {
                  sum += w[j][i]*o[j]; 
            }
            
            f[i]=sigmoid(sum);
        }
        
        for (int i=0;i<n;i++) {
            error[i]=f[i]-o[i];
            
        }
        
    }

    public void setTrain(Data data) {
        createIn(data);
        forwardProject();
        backProject();
        
    }

    public String outString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ImageLabel getFantasy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getResult(Result result) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getLatestResult(Result result) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void dump() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//    public void save(OutputStream str) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public void load(InputStream str) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
}

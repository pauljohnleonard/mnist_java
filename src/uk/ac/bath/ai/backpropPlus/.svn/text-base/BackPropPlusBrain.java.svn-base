/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.backpropPlus;

import uk.ac.bath.ai.AbstractBrain;
import uk.ac.bath.ai.image.WeightVectorImage;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.Vector;
import uk.ac.bath.util.Tweakable;

/**
 *
 * @author pjl-h
 *
 */

public class BackPropPlusBrain  extends AbstractBrain {
    
//    ImageLabel reality=null;
//    Vector<Image> imagination=new Vector<Image>();
    private int n;
   // float accel=0.1f;
    BackPropPlusF bp;
    int layerSizes[];
       
   // static int nLabels=10;
  
  //  private ImageLabel fantasy;
    
    private float[] in;
 //   Data out;
  //  private int nImage;
//    private int nLabel;
//   private Result result;
//    
    public BackPropPlusBrain(Dimension d,Dimension d2) {  
        super(d,d2,"Back Propagation Plus");
     //   this.nImage=d.height*d.width;
      //  this.nLabel=d2.height*d2.width;
        this.n=nPixel+nLabel;
        int nHidden=80;  //80
  //      int extra=30;
        int nMiddle=10;  // 20
     
        double beta=.001;
        double alpha=20;
    
       // result=new Result(d,d2);

        in=new float[n];
        
        layerSizes=new int[]{n,nHidden,nMiddle,nHidden,n};
        
         // Creating the net
        bp = new BackPropPlusF(layerSizes, beta, alpha);

        
        for(int i=0;i<nHidden;i++) {
            imagination.add(new WeightVectorImage(bp.weight[1][i],d,"pre"+1));
        }
 

    }


    public String outString() {
 
        return "XX";
    }

    
    private void loadTraining(float [] image,float [] label) {
        System.arraycopy(image, 0, in, 0, nPixel);
        System.arraycopy(label, 0,in, nPixel, nLabel);
    }
  
    
    private void loadFire(float image[]) {
        System.arraycopy(image, 0, in, 0, nPixel);
        Arrays.fill(in, nPixel, nPixel+nLabel,0.5f);
    }
 
    public float [] fire(float image[]) {        
        loadFire(image);
        bp.ffwd(in);
     
        return bp.output();
    }

    public float [] doTraining(float [] image,float [] label) {
        loadTraining(image,label); 
        bp.bpgt(in,in);
 
        return bp.output();
    }

    public Vector<Tweakable> getTweaks() {
        return bp.getTweaks();
    }

    public void dump() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    
}

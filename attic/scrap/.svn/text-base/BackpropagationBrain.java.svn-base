/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.scrap;

import uk.ac.bath.ai.gui.ImageLabel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author pjl
 */
public class BackpropagationBrain extends Observable {
    
    ImageLabel reality=null;
    ImageLabel imagination[];
    SigPerceptron  layer1[];
    SigPerceptron  layer2[];
    private int nPixel;
    float accel=0.1f;
    int nHidden=20;
    int nLabel=10;
    float hidden[];
    float output[];
    float error[];
    float teach[];
    
    BackpropagationBrain(Dimension d) {      
        layer2=new SigPerceptron[nLabel];
        layer1=new SigPerceptron[nHidden];
        
        this.nPixel=d.height*d.width;
        imagination = new ImageLabel[nHidden];
        hidden=new float[nHidden];
        output=new float[nLabel];
        error=new float[nLabel];
        teach=new float[nLabel];
        
        for (int i=0;i<nLabel;i++) {
            layer2[i]=new SigPerceptron(nHidden);
        }

        for (int i=0;i<nHidden;i++) {
            layer2[i]=new SigPerceptron(nPixel);
//            imagination[i]=new ImageLabel(new WeightVectorImage(layer2[i].w,d),i);
        }
     
    }
    
    void setTrain(ImageLabel il) {       
 
        // Forward pass
//        float image[]=il.image.a;
//      
//        
//        int label=il.label;
//        
//        for(int i=0;i<nLabel;i++) {
//            if (i == label) teach[i]=1.0f;
//            else teach[i]=0.0f;
//        }
//        
//        for (int i=0;i<nHidden;i++){
//            SigPerceptron p=layer1[i];
//            p.fire(image);
//            hidden[i]=p.output;
//        }
//        
//        for (int i=0;i<nLabel;i++){
//            SigPerceptron p=layer2[i];
//            p.fire(image);
//            output[i]=p.output;
//            error[i]=teach[i]-output[i];
//        }
//  
//        
//        
//        reality=il;
//        setChanged();
    }
        
   List<Integer> recognise(ImageLabel il) {
        
        ArrayList<Integer> recog=new ArrayList<Integer>();
        
        for (int i=0; i<nHidden ; i++) {
 //           layer1[i].fire(il.image.getFloatVectorImage().a);
            
                recog.add(i);                     
            
        }
        
        setChanged();
        return recog;
   }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.gui;

import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.Util;
import uk.ac.bath.ai.image.FloatVectorImage;
import uk.ac.bath.ai.*;

/**
 *
 * @author pjl
 */
public class ImageLabel  {
    FloatVectorImage image;
    int              label;

    public ImageLabel(FloatVectorImage f, int l) {
       setImageLabel(f,l);
    }

    public int getLabel() {
        return label;
    }

      public void setData(Data data) {
        image.setArray(data.image);
        label=Util.toLabel(data.label);
     }

    void setData(float[] image, float[] label) {
        
        this.image.setArray(image);
        this.label=Util.toLabel(label);
 //       System.out.println(": label " +this.label);
    }
      
    
    private void setImageLabel(FloatVectorImage src,int label) {
        this.label=label;
        image=src.getFloatVectorImage();
    }
    

}

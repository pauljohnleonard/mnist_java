/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.image;

import uk.ac.bath.ai.image.FloatVectorImage;
import java.awt.Dimension;

/**
 *
 * @author pjl
 */
public class WeightVectorImage extends FloatVectorImage implements DisplayImage {

    
    public WeightVectorImage(float[] w,Dimension dim,String name) {
        super(w,dim,name);
    }

   
    

    
    
    @Override  
    public  int[] getRGBArray() {
       
        for (int i = 0; i < dim.width; i++) {
            for (int j = 0; j < dim.height; j++) {
                float val=a[j*dim.width+i];
                
                int c_b;
                    int c_r;
                    int c_g; 
                    
                if (val > 0.0 ) {
                    if (val > 1.0 ) val =1.0f;
                  
                    c_g = (int)(255.0*val);
                    c_b = 0; // 255-c_g; // 0;// (int)(100.0*val);
                    c_r = 0;//(int)(255.0*val);
                } else {
                    val=-val;
                    if (val > 1.0 ) val =1.0f;

                   //0;//(int)(100.0*val);
                    c_r = (int)(255.0*val);
                    c_b = 0; // 255-c_r;
                    c_g = 0;
                }                    

                
                int color = (c_b) + (c_g << 8) + (c_r << 16) + (0xFF << 24);
                rgb[j * dim.width + i] = color;
            }
        }
        return rgb;
    }

}

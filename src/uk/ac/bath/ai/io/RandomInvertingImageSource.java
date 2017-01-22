/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.io;

import uk.ac.bath.ai.*;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.MyDimension;

/**
 *
 * @author pjl
 */
public class RandomInvertingImageSource implements DataSource {

    ImageLabelDataSource source;

    public RandomInvertingImageSource(String name) throws FileNotFoundException, IOException {
        source=new ImageLabelDataSource(name);
    }
    
    
    
    public MyDimension getInDimension() {
        return source.getInDimension();
    }

    public int getSize() {
        return source.getSize();
    }

    Random rand=new Random();
    
    public Data getData(int index) {
        Data data=source.getData(index);

        if (rand.nextBoolean()) {
            for(int i=0;i<data.image.length;i++) {
                data.image[i] = 1.0f-data.image[i];
            }
            
        }
        return data;
    }

    public MyDimension getOutDimension() {
        return source.getOutDimension();
    }
    
	 public String nameOf(int feat_id){
		 
		 return source.nameOf(feat_id);
	 }
	 
	 public List<String> getLabels() {

			return source.getLabels();
		}


}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.io;



import java.util.List;

import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.MyDimension;

/**
 *  Interface of training data
 *
 * @author pjl
 */
public interface  DataSource {

    public MyDimension getInDimension();    // input dimension 

    public MyDimension getOutDimension();   // output dimension
   
    public int getSize();                  // number of training sets
    
    /*  returns i'th instance of training data */
    public Data getData(int index) throws Exception;
    
    public String nameOf(int feat_id);

	public List<String> getLabels();
 
}

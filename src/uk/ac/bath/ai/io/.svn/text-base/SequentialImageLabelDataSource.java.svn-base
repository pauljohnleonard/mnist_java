/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.NewClass 
 */
package uk.ac.bath.ai.io;

//import uk.ac.bath.ai.io.DataSource;
//import uk.ac.bath.ai.io.TrainingFileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.MyDimension;

/**
 *
 * @author pjl
 */
public class SequentialImageLabelDataSource implements DataSource {

    private SequentialTrainingFileReader trainingReader;
    private MyDimension dimension;
    private SequentialTrainingFileReader trainingLabelReader;

     public SequentialImageLabelDataSource(String base,String name) throws FileNotFoundException, IOException {
        //String root = "/home/pjl/TrainingData/";
        URL file = new URL(base + name + "-images-idx3-ubyte");
        System.out.println(file.toString());
        trainingReader = new SequentialTrainingFileReader(file);

        int d[] = trainingReader.getSizes();
        dimension = new MyDimension(d[1], d[2]);
        file = new URL(base + name + "-labels-idx1-ubyte");
        trainingLabelReader = new SequentialTrainingFileReader(file);
    }


    public SequentialImageLabelDataSource(String name) throws FileNotFoundException, IOException {
        String root = "/home/pjl/TrainingData/";
        File file = new File(root + name + "-images-idx3-ubyte");
        trainingReader = new SequentialTrainingFileReader(file);

        int d[] = trainingReader.getSizes();
        dimension = new MyDimension(d[1], d[2]);
        file = new File(root + name + "-labels-idx1-ubyte");
        trainingLabelReader = new SequentialTrainingFileReader(file);
    }

    public int getSize() {
        return trainingReader.getSizes()[0];
    }

    
    static float labs[][];
    static int nLabels=10;
    static List<String> str_labs=new ArrayList<String>();
    
    static {
        labs=new float[nLabels][];
        
        for(int i=0;i<10;i++){
            labs[i]=new float[10];
            labs[i][i]=1.0f;
            String str=Integer.toString(i);
            str_labs.add(str);
        }
        
    }
    public Data getData(int index) {

        Data data = new Data();

        try {
            byte[][] b = (byte[][]) trainingReader.getData(index);
            int w = b.length;
            int h = b[0].length;
            data.image = new float[w * h];
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    data.image[i + j * w] = ((float) (b[j][i] & 0xff) ) / 256.0f;   // normalize to 1.0 max
                }
            }
            int lab= trainingLabelReader.getLabel(index);
            data.label=labs[lab];
        } catch (Exception ex) {
            Logger.getLogger(SequentialImageLabelDataSource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return data;
    }

    public MyDimension getInDimension() {
        int siz[] =  trainingReader.getSizes();
        MyDimension dim=new MyDimension(siz[1],siz[2]);
        return dim;
    }

    static MyDimension dout=new MyDimension(10,1);
    public MyDimension getOutDimension() {
        return dout;
    }
    
	 public String nameOf(int feat_id){
		 
		 return Integer.toString(feat_id);
	 }
	 
	 public List<String> getLabels() {

		 
			return str_labs;
		}

}

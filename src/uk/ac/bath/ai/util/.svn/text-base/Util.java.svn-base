/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.util;

import java.util.Random;

/**
 *
 * @author pjl
 */
public class Util {

   public static boolean fixseed=true;

    public static int toLabel(float a[]) {
        
        int lab=-1;
        float val=Float.NEGATIVE_INFINITY;
        for (int i=0 ; i<a.length ; i++) {
            if ( a[i]> val ) {
                lab=i;
                val=a[i];
            }
        }
        return lab;
    }
    
    
    static float labs[][];
    static float nullLab[];
    
    static int nLabels=10;
    
    static {
        labs=new float[nLabels][];
        nullLab=new float[10];
        
        for(int i=0;i<10;i++){
            labs[i]=new float[10];
            labs[i][i]=1.0f;
            nullLab[i]=0.0f;
        }
        
    }
    
    public static float[] nullLabel() {
        return nullLab;
   }
    public static float[] fromLabel(int lab) {
    	   
        return labs[lab];
   }
       //	sigmoid function
    public static float sigmoid(float in) {
        return (float) (1.0 / (1.0 + Math.exp(-in)));
    }

    static public  Random createRandom() {
        if (fixseed) return new Random(0L);
        else return new Random();
    }
	public static float[] normalize(float[] label) {
		float ret[]=new float[label.length];
		float sum=0;
		for (float x : label){
			sum+=x;
		}
		
		
		for (int i=0 ; i<label.length;i++){
			if (sum == 0){
				ret[i]=0.0f;
			} else {
				ret[i]=label[i]/sum;
			}
		}
		return ret;
	}
    
 
    
}

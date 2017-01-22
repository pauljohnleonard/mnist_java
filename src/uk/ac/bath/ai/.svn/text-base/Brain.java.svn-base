/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai;

import java.io.Externalizable;


import uk.ac.bath.ai.image.DisplayImage;
import uk.ac.bath.ai.image.FloatVectorImage;
import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.Result;

import uk.ac.bath.ai.gui.Image;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;
import uk.ac.bath.util.Tweakable;

/**
 *
 *  Interface for Brains
 * 
 * @author pjl
 */
public interface Brain extends Externalizable {

    
    final static long  serialVersionUID = 0L;  // 3237386353788526304L;

    //protected abstract void addImage(DisplayImage image);

   // public void addObserver(Observer panel);

    public DisplayImage getImagination(int i);

    public int getImaginationCount();

    public String getName();

    public Random getRandom();

    public Vector<Tweakable> getTweaks();

   // public void notifyObservers();

    public float [] fire(float [] in);

    //public void getLatestResult(Result result);

    public float [] doTraining(float [] image,float [] label);

    public void dump();

}

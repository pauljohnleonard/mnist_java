/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.util;


import java.awt.Dimension;

/**
 *
 *  State
 *
 * @author pjl
 */
public class Result {
    public Data in;
    public Data out;

    public Result(Dimension dImage, Dimension dLabel) {
        int nPix=dImage.height*dImage.width;
        int nLab=dLabel.height*dLabel.width;
        in=new Data();
        out=new Data();
        in.image=new float[nPix];
        in.label=new float[nLab];
        out.image=new float[nPix];
        out.label=new float[nLab];
    //    throw new UnsupportedOperationException("Not yet implemented");
    }
    
}

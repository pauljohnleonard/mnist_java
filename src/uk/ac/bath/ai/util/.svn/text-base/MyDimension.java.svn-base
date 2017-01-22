/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.util;

import java.awt.Dimension;

/**
 *
 * @author pjl
 */
public class MyDimension extends Dimension {

	static long serialVersionUID = 0L;
	
    public final int n;

    public MyDimension(Dimension dIn) {
        this(dIn.width,dIn.height);
    }
    
    public MyDimension(int w, int h) {
       super(w,h);
       n=w*h;
    }
    
    public int index(int x,int y) {
        return x+y*width;
    }
    
    
}

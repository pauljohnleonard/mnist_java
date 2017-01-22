/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.image;

import uk.ac.bath.ai.gui.*;
import java.awt.Dimension;

/**
 *
 * @author pjl
 */
public class OffFloatVectorImage implements DisplayImage {

    protected float a[];
    protected Dimension dim;
    protected String name;
    protected int rgb[];
    int off;
    
    public OffFloatVectorImage(float a[],int off, Dimension dim, String name) {

        assert(off+a.length >= dim.width*dim.height);
        this.a = a;
        this.dim = dim;
        this.off=off;
        rgb = new int[dim.width * dim.height];
        this.name=name;
    //     this.notify();
    }

 

    public String getName() {
        return name;
    }

  

//    public OffFloatVectorImage getFloatVectorImage() {
//        return this;
//    }

    public Dimension getDimension() {
        return dim;
    }

    public int[] getRGBArray() {
        // int width=image.dim.width;
        // int height=image.dim.height;

        // setup(image.getDimension());
        //  float a[]=image.a;
        for (int j = 0; j < dim.height; j++) {
            for (int i = 0; i < dim.width; i++) {
                int c_b = (int) (256.0 * a[j * dim.width + i+off]);
                int c_r = c_b;
                int c_g = c_r;
                int color = (c_b) + (c_g << 8) + (c_r << 16) + (0xFF << 24);
                rgb[j * dim.width + i] = color;
            }
        }
        return rgb;
    }

    public void print() {

        System.out.println();
        for (int i = 0; i < dim.width; i++) {

            for (int j = 0; j < dim.height; j++) {
                float val = a[j * dim.width + i];
                System.out.print(String.format(" %3.2f", val));
            }
            System.out.println();

        }

    }
    @Override
	public void setPixel(int x, int y, float val) {
		if (x < 0 || x >= dim.width) return;
		if (y < 0 || y >= dim.height) return;
		
		a[y * dim.width + x +off]=val;
		
	}
    
}

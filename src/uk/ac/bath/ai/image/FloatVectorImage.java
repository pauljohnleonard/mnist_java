/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.image;

import java.awt.Dimension;

import uk.ac.bath.ai.gui.Image;

/**
 *
 * @author pjl
 */
public class FloatVectorImage implements DisplayImage {

    protected float a[];
    protected Dimension dim;
    protected String name;
    protected int rgb[];

    public FloatVectorImage(float a[], Dimension dim, String name) {

        assert(a.length >= dim.width*dim.height);
        this.a = a;
        this.dim = dim;
        rgb = new int[dim.width * dim.height];
        this.name=name;
    //     this.notify();
    }

    protected FloatVectorImage(float a[], Dimension dim) {
        this.a = a;
        rgb = new int[dim.width * dim.height];
        this.dim = dim;
    }

    public String getName() {
        return name;
    }

    public FloatVectorImage(Dimension dim) {
        a = new float[dim.width * dim.height];
        rgb = new int[dim.width * dim.height];
        this.dim = dim;
    }

    public FloatVectorImage getFloatVectorImage() {
        return this;
    }

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
                int c_b = (int) (256.0 * a[j * dim.width + i]);
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

    public void setArray(float[] in) {
        assert (in.length >= dim.width * dim.height);
        a = in;
    }

	@Override
	public void setPixel(int x, int y, float val) {
		if (x < 0 || x >= dim.width) return;
		if (y < 0 || y >= dim.height) return;
		
		a[y * dim.width + x]=val;
		
	}

	public void clear() {
		for (int i = 0; i < dim.width; i++) {
            for (int j = 0; j < dim.height; j++) {
                a[j * dim.width + i]=0;
            }
		}
	}
	
	public float[] getInputVec() {
		return a;
	}
}

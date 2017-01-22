/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.hippo;

import java.awt.Dimension;
import java.util.Random;
import uk.ac.bath.ai.Data;
import uk.ac.bath.ai.io.DataSource;
import uk.ac.bath.ai.util.MyDimension;
import uk.ac.bath.ai.util.Util;

/**
 *
 *  Data source to try out the Mitman Laurant Levy CA3 paper
 *
 * @author pjl
 */
public class HippoTrainingDataSource implements DataSource {
    private int n;
    private int nPat;
    private int sutter;
    private int setSize;
    private int actSize;
    private MyDimension dim;
    Random rand=Util.createRandom();

    public HippoTrainingDataSource(int n,int nPat,int sutter,int setSize,int actSize) throws Exception {

        if ((n % setSize) != 0) {
            throw new Exception("n % setSize != 0");
        }
        this.n=n;
        this.nPat=nPat;
        this.sutter=sutter;
        this.setSize=setSize;
        this.actSize=actSize;
        dim=new MyDimension(setSize,n/setSize);

    }


    public MyDimension getInDimension() {
        return dim;
    }

    public MyDimension getOutDimension() {
        return dim;
    }

    public int getSize() {
        return nPat*sutter;
    }

    public Data getData(int index) {
        index = index%(nPat*sutter);
        int id=index/sutter;

        int i1=id*setSize;
        int i2=i1+setSize;

        Data data= new Data();
        data.image = new float[n];
        data.label = null;

        for (int i=0;i<actSize;i++) {
            int ii=(int) (i1 + rand.nextDouble() * setSize);
            data.image[ii]=1.0f;
        }
        return data;
    }
    

}

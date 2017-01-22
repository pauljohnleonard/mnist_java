/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai;

import uk.ac.bath.ai.io.DataSource;
//import jahuwaldt.tools.NeuralNets.TrInstGenerator;

public class Trainer { //implements TrInstGenerator {

    int count = 0;
    DataSource.Data data;
    private DataSource src;
    private int nTest;
    private int nMax;
    
    public Trainer(DataSource src,int nTest) {
        this.src = src;
        this.nTest = nTest;
        nMax = src.getSize();
        data = new DataSource.Data();
    }

    public void reset() {
        count = 0;
    }

    public int numberInstances() {
        return nTest;
    }

    public void nextInstance() {
        data = src.getData(count++%nMax);
    }

    public float[] getTestInputs() {
        return data.image;
    }

    public float[] getTestOutputs() {
        return data.label;
    }
};

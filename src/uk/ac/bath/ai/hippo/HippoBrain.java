/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.hippo;

import java.util.Arrays;
import uk.ac.bath.ai.AbstractBrain;
import uk.ac.bath.ai.Data;
import uk.ac.bath.ai.util.MyDimension;
import uk.ac.bath.ai.Result;


/**
 *
 * @author pjl
 */
public class HippoBrain extends AbstractBrain {
//

    float ZB[];
    float Z[];
    float Zprev[];
    float Znow[];

    Neuron neurons[];

    int n;
    private double fireRatio = 7.5 / 100.0;
    private double connectRatio = 8.0 / 100.0;
    private float alpha;
    final float stutter;
    float mu;

    public HippoBrain(MyDimension dIn, MyDimension dOut, String name, int stutter) {
        super(dIn, dOut, name);

        this.stutter = 13; // stutter;
        mu = (float) (Math.pow(1.05, (1.0 / stutter)) - 1.0f);
        System.err.println(" mu ="+mu);
        
   //     mu=.001f;
        Z = new float[dOut.n];
        Zprev = new float[dOut.n];
        Znow = new float[dOut.n];
        ZB = new float[dOut.n];
        n = dOut.n;
        neurons = new Neuron[n];
        alpha = 0.85f;

        int cc[] = new int[n]; // temporary aray for pointers

        for (int i = 0; i < n; i++) {

            Z[i] = (float) (rand.nextDouble())*.00001f; // randomize initial state
            Zprev[i]=(float) (rand.nextDouble())*.00001f;
            Znow[i]=(float) (rand.nextDouble())*.00001f;

            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (j == i) {
                    continue;
                }
                if (rand.nextFloat() < connectRatio) {
                    cc[cnt++] = j;
                }
            }

            int ptrIn[] = new int[cnt];

            System.arraycopy(cc, 0, ptrIn, 0, cnt);
            neurons[i] = new Neuron(ptrIn, i);

        }

    }

    public void fire(Data data) {

        System.arraycopy(data.image, 0, result.in.image, 0, nPixel);

        // first stage of firing
        for (Neuron n : neurons) {
            n.calcSum();
        }

        // only fire the fireRatio sort into assending order
        Arrays.sort(neurons);

        int nCnt = (int) (n * fireRatio);

        // set the firing status 
        int cnt = 0;
        for (Neuron nn : neurons) {
            int id = nn.id;
            if (cnt < nCnt || data.image[id] > 0) {
                Z[id] = 1.0f;
            } else {
                Z[id] = 0.0f;
            }
            cnt++;
        }

        // do the learning
        for (Neuron nn : neurons) {
            nn.updateWeights();
        }

        // now decay
        for (int i = 0; i < n; i++) {
            if (Z[i] == 1.0f) {
                ZB[i] = 1.0f;
            } else {
                ZB[i] = alpha * ZB[i];
            }

        }

        System.arraycopy(Z, 0, result.out.image, 0, nPixel);
    }

    /**
     * copy cached result into result supplied by caller
     *
     * @param result   overwritten with current cached result
     */
    public synchronized void getLatestResult(Result res) {
        if (this.result == null) {
            return;
        }

        // System.arraycopy(this.result.in.label, 0, res.in.label, 0, nLabel);
        System.arraycopy(this.result.in.image, 0, res.in.image, 0, nPixel);

        //   System.arraycopy(this.result.out.label, 0, res.out.label, 0, nLabel);
        System.arraycopy(this.result.out.image, 0, res.out.image, 0, nPixel);

    }

    public void setTrain(Data data) {
        fire(data);
    }

    public void dump() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class Neuron implements Comparable<Neuron> {

        float w[];
        int ptrIn[];
        float y;
        float y_prev;
        float z;
        private int nIn;
        int id;

        Neuron(int ptrIn[], int id) {

            this.nIn = ptrIn.length;
            this.id = id;
            this.ptrIn = ptrIn;
            this.w = new float[nIn];
            this.y = (float) rand.nextDouble();
            for (int i = 0; i < (nIn); i++) {
                this.w[i] = //(float) (rand.nextDouble()) * .1f;
                 (float) (rand.nextDouble() - .5)*0.00001f;
            }
        }

        void calcSum() {
            float tmp = 0.0f;
            y_prev=y;
            for (int i = 0; i < nIn; i++) {
                tmp = tmp + w[i] * Z[ptrIn[i]];
            }
            y = tmp;
            z=y-y_prev;
        }

        void updateWeights() {
            for (int ii = 0; ii < nIn; ii++) {
                int i = ptrIn[ii];
                w[ii] = w[ii] + mu * Z[id] * (ZB[i] - w[ii]);
            }
        }

        public int compareTo(Neuron o) {
            return Float.compare(o.z, z);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.backpropPlus;

/**
 *
 * @author pjl
 */
public class XORtest {

    public static void main(String args[]) {
        int sz[] = {2, 2, 1};
        double b = .001;
        double a = 150;
        BackPropPlusF brain = new BackPropPlusF(sz, b, a);


        float[][] in = {{0f, 0f}, {0f, 1f}, {1f, 0f}, {1f, 1f}};
        float t[][] = {{0}, {1}, {1}, {0}};

        float error;
        int iter=0;
        do {
            iter++;
            error = 0.0f;
            for (int i = 0; i < 4; i++) {
                brain.bpgt(in[i], t[i]);
                float out[] = brain.output();
                error = Math.max(error, Math.abs(t[i][0] - out[0]));
            }
            System.out.println(" iter "+iter+ " max error="+error);
        } while (error > .1);

    }
}

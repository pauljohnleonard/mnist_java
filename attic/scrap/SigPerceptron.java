/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.scrap;

/**
 *
 * @author pjl
 */
class SigPerceptron {
    float w[];
    private int n;
    float b;
    float output;
    
    static double sigmoid(double v){
        return 1.0/(1.0f-Math.exp(-v));
    }
   
    static double dsigmoid(double v){
        double ss=sigmoid(v);
        return ss*(1.0-ss);
    }
    
    public SigPerceptron(int n) {
        w=new float[n];
        this.n=n;
    }

    public void fire(float vec[]) {
        float sum=b;
        for (int i=0;i<n;i++) {
            sum+=w[i]*vec[i];
        } 
        output=(float) sigmoid(sum);
    }
    
    public void train(float vec[],boolean val,float accel){
//        if (fire(vec) == val) {
//    //        System.out.println(" Percept:  OK ");
//                    
//            return;
//        }

    //     System.out.println(" Percept: Error ... training ");
        
        float delta;
        if (val) delta=accel;
        else delta=-accel;
        
        for (int i=0;i<n;i++) {
            w[i] = w[i] + delta*vec[i];
        } 
        b=b+delta;
    }
    
}

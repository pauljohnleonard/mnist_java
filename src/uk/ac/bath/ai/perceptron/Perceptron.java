/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.perceptron;

/**
 *
 * @author pjl
 */
class Perceptron {
    float w[];
    private int n;
    float b;
    float firing;
    
    public Perceptron(int n) {
        w=new float[n];
 //       for (int i=0;i<n;i++) w[i]=(2.0f*i-n)/n;
        this.n=n;
    }

    static float squash(float val) {
        if (val > 0) return 1.0f;
        else return 0.0f;
    }
    
     public float fire(float vec[]) {
        
        float sum=b;
        for (int i=0;i<n;i++) {
            sum+=w[i]*vec[i];
        } 
        firing=squash(sum);
        return firing;
    }
    
    public float train(float vec[],float val,double accel){
    
        fire(vec);
        
        float error=val-firing;
        float delta=(float) accel*error;
        
        for (int i=0;i<n;i++) {
            w[i] = w[i] + delta*vec[i];
        } 
        b=b+delta;
        return firing;
    }
    
}

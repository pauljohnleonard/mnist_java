/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author pjl
 */
public class DigitResultPanel extends JPanel {

    
    float guess[];
    float target[];
    
   public  DigitResultPanel(){
        setMinimumSize(new Dimension(500,50));
        setPreferredSize(new Dimension(300,50));
        setBackground(Color.BLACK);
    }
    
    public void set(float guess[],float target[]){
        if (guess == null) return;
        this.guess=guess;
        this.target=target;
   
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w=this.getWidth();
        int h=this.getHeight();
        
        if (guess == null) return;
        
        Font f=g.getFont();
        
        Font nf=f.deriveFont(Font.BOLD,40.0f);
        g.setFont(nf);
        
        for(int i=0;i<guess.length;i++) {
            int vv=(int) (guess[i] * 255);
            int val=vv+ (vv << 8) + (vv << 16);
            g.setColor(new Color(val,false));
            g.drawString(Integer.toString(i), (i*w)/10,h-8);
        }
    }
    
}

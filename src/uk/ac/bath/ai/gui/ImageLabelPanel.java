/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author pjl
 */
public class ImageLabelPanel extends JPanel  {
    
    ImagePanel imagePanel;
    JLabel label;
    ImageLabel data;
    
    public ImageLabelPanel(int scale,ImageClient client) {
        setLayout(new BorderLayout());
        add(imagePanel=new ImagePanel(scale,client),BorderLayout.CENTER);
        add(label=new JLabel("UNSET"),BorderLayout.SOUTH);
      //  validate();
    }

    @Override
    public void paint(Graphics g) {
            if (label != null)
                 if (data !=null) label.setText(Integer.toString(data.label));
            super.paint(g);
    }
    
    void setImageLabel(ImageLabel newData) {
        if (data == newData) return;
        data=newData;
        imagePanel.setImage(data.image);
        label.setText(Integer.toString(data.label));
        repaint();
    }

	
}

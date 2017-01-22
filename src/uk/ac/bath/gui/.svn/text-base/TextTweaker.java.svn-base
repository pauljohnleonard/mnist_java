package uk.ac.bath.gui;
import java.awt.BorderLayout;
import uk.ac.bath.util.*;
import java.awt.event.*;

import javax.swing.*;

public class TextTweaker  implements ActionListener {

    Tweakable t;
    JTextField textField;
    JPanel panel;

   public  TextTweaker(Tweakable t) {
	panel=new JPanel();
        panel.setLayout(new BorderLayout());
        
        this.t=t;
	int len = t.getMaximum().toString().length();
	textField = new JTextField(String.valueOf(t.getNumber()),len);
	textField.addMouseListener(new MouseListener(){

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseEntered(MouseEvent e) {
			textField.requestFocusInWindow();

			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			textField.getRootPane().requestFocusInWindow();
			
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	});
        panel.add(textField,BorderLayout.CENTER);
	panel.add(new JLabel(t.getLabel()),BorderLayout.WEST);
	textField.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {

	//	Object o=e.getSource();
	//	Object v = ((JFormattedTextField)o).getValue();
	t.set(textField.getText()); //.toString());
	textField.setText(t.toString());

    }
	
    public JComponent getComponent(){
        return panel;
    }

}

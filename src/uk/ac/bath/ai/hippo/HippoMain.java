/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.hippo;


import uk.ac.bath.ai.*;
import uk.ac.bath.ai.gui.*;
import uk.ac.bath.ai.io.DataSource;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import uk.ac.bath.ai.util.MyDimension;
import uk.ac.bath.ai.util.Util;

/**
 *
 * @author pjl
 */
public class HippoMain extends JFrame {

    private static MyDimension dIn;
    private static MyDimension dOut;
    static  Brain b;
    static  DataSource trainSrc;
 
    static JMenuBar createMenuBar(JPanel panel) throws FileNotFoundException, IOException {



        JMenuBar menuBar = new JMenuBar();


        JMenu menu = new JMenu("Exit");

        menu.add(new JMenuItem(new AbstractAction("Exit"){

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        }));
        menuBar.add(menu);



//        menu = new JMenu("Transplant");
//
//
//        Actions actions=new Actions(dIn,dOut);
//
//        for(AbstractAction act:actions.getActions()) {
//            JMenuItem item=new JMenuItem(act);
//            menu.add(item);
//            JButton but=new JButton(act);
//            panel.add(but);
//        }
//
//        menuBar.add(menu);
        return menuBar;
    }

    private static void createAndShowGUI() throws FileNotFoundException, IOException {


        try {
            JFrame frame = new AIBrainFrame(b,trainSrc,null);
            frame.setTitle("Hippo Brain");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AIBrainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AIBrainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AIBrainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
//        //    JFrame.setDefaultLookAndFeelDecorated(true);
//        JFrame frame = new JFrame("Brain Launcher");
//        JPanel content=new JPanel();
//        frame.setContentPane(content);
//        // We now also set the MenuBar of the Frame to our MenuBar
//        JMenuBar bar=createMenuBar(content);
//        frame.setJMenuBar(bar);
//        frame.setSize(new Dimension(400, 600));
//        frame.validate();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      //  frame.pack();
//        frame.setVisible(true);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, InvocationTargetException, Exception {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.

        Util.fixseed=false;
        int nPat=10;
        int sutter=9;
        int setSize=96;
        int actSize=64;
        int n=setSize*(4096/setSize);
        
        trainSrc = new HippoTrainingDataSource(n,nPat,sutter,setSize,actSize);
        dIn =  trainSrc.getInDimension();
        dOut = trainSrc.getOutDimension();
        b=new HippoBrain(dIn, dOut, "Hippo",sutter);

        SwingUtilities.invokeAndWait(new Runnable() {

            public void run() {
                try {
                    createAndShowGUI();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(HippoMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(HippoMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


}
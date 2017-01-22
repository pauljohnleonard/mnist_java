/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;

import uk.ac.bath.ai.gui.LoadPanel;

/**
 *
 * @author pjl
 */
public class AIApplet extends JApplet {

    @Override
    public void init() {
        String dataDir = System.getProperty("user.dir") + "/data";

        File dir = new File(dataDir);

        String base;
         if (dir.isDirectory()) {
             System.out.println(" Using local training data");
             base = "file://localhost/" + dataDir + "/";
         } else {
        System.out.println(" Downloading training data from the web");

        base = "http://people.bath.ac.uk/eespjl/courses/CI/demo/NN/";
        }
        try {
            new LoadPanel(this, base);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AIApplet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AIApplet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

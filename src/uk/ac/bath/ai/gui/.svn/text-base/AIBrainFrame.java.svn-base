/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.gui;

import uk.ac.bath.ai.io.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import uk.ac.bath.ai.AIModel;
import uk.ac.bath.ai.Brain;

/**
 *
 * @author pjl
 */
public class AIBrainFrame extends JFrame {

    AIModel model;


    public AIBrainFrame(final Brain b, DataSource trainSrc, DataSource testSrc) throws FileNotFoundException, IOException, Exception {



        model = new AIModel(b, trainSrc, testSrc);
        AIPanel panel = new AIPanel(model);
        model.addObserver(panel);

        setContentPane(panel);
        setTitle(model.getBrain().getName());

        validate();
        pack();
        setVisible(true);
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);

    }
}


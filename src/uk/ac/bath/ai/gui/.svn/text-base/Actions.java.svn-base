/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;

import uk.ac.bath.ai.Brain;
import uk.ac.bath.ai.backprop.BackPropBrain;
import uk.ac.bath.ai.convolution.LayerConfig;
import uk.ac.bath.ai.io.DataSource;
import uk.ac.bath.ai.perceptron.SingleLayerPerceptronBrain;
import uk.ac.bath.ai.util.MyDimension;

/**
 *
 * @author pjl
 */
public class Actions {

    public static Brain brain;
    Vector<AbstractAction> actions;
    public SpinnerNumberModel kernelSize;
    public SpinnerNumberModel nKernel;
    public SpinnerNumberModel nHidden;
    public SpinnerNumberModel nHidden2;
    double alpha = 40.0;  // acceleration
    double beta = 0.001;  // acceleration
    private final DataSource trainSrc;
    private final DataSource testSrc;

    public void createBrainFrame(AbstractAction act, Brain b) {
        try {

            JFrame frame = new AIBrainFrame(b, trainSrc, testSrc);
            frame.setTitle((String) act.getValue(AbstractAction.NAME));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AIBrainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AIBrainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AIBrainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("serial")
	public Actions(final DataSource trainSrc, final DataSource testSrc) {
        kernelSize = new SpinnerNumberModel(6, 1, 8, 1);
        nKernel = new SpinnerNumberModel(6, 1, 20, 1);
        nHidden = new SpinnerNumberModel(10, 1, 50, 1);
        nHidden2 = new SpinnerNumberModel(10, 1, 50, 1);
        this.trainSrc = trainSrc;
        this.testSrc = testSrc;
        final MyDimension dIn = trainSrc.getInDimension();
        final MyDimension dOut = trainSrc.getOutDimension();

        actions = new Vector<AbstractAction>();

        actions.add(new AbstractAction("Load a brain") {

            public void actionPerformed(ActionEvent e) {
                File file = new File("greymatter.dat");
                File curDir = new File(System.getProperty("user.dir"));

                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(curDir);
                
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                }

                if (file == null) {
                    return;
                }

                if (!file.exists()) {
                    return;
                }

                ObjectInputStream in = null;
                try {
                    FileInputStream inStr = new FileInputStream(file);

                    in = new ObjectInputStream(inStr);
                    Brain brain = (Brain) in.readObject();
                    createBrainFrame(this, brain);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);

                } finally {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });


        actions.add(new AbstractAction("Single Layer Perceptron") {

            public void actionPerformed(ActionEvent e) {
                createBrainFrame(this, new SingleLayerPerceptronBrain(dIn, dOut));
            }
        });

        actions.add(new AbstractAction("Back Propagation (no hidden)") {

            public void actionPerformed(ActionEvent e) {
                int hidden[] = new int[]{};
                createBrainFrame(this, new BackPropBrain(dIn, dOut, hidden, beta, alpha));
            }
        });

        actions.add(new AbstractAction("Back Propagation (1 hidden layer)") {

            public void actionPerformed(ActionEvent e) {
                int nH = nHidden.getNumber().intValue();
                int hidden[] = new int[]{nH};
                createBrainFrame(this, new BackPropBrain(dIn, dOut, hidden, beta, alpha));
            }
        });






        actions.add(new AbstractAction("Back Propagation (2 hidden layers)") {

            public void actionPerformed(ActionEvent e) {
                int nH = nHidden.getNumber().intValue();
                int nH2 = nHidden2.getNumber().intValue();

                int hidden[] = new int[]{nH, nH2};
                createBrainFrame(this, new BackPropBrain(dIn, dOut, hidden, beta, alpha));
            }
        });





        actions.add(new AbstractAction("Convolution brain") {

            public void actionPerformed(ActionEvent e) {

                int nK = kernelSize.getNumber().intValue();
                int nT = nKernel.getNumber().intValue();
                createBrainFrame(this, LayerConfig.create3LayerConvolution(dIn, dOut, nT, nK, beta, alpha));
            }
        });


        actions.add(new AbstractAction("Convolution with added hidden layer") {

            public void actionPerformed(ActionEvent e) {

                int nK = kernelSize.getNumber().intValue();
                int nT = nKernel.getNumber().intValue();
                int nH = nHidden.getNumber().intValue();
                createBrainFrame(this, LayerConfig.create4LayerConvolution(dIn, dOut, nT, nK, nH, beta, alpha));
            }
        });


        actions.add(new AbstractAction("FIR layer  (taps=kernel size)") {

            public void actionPerformed(ActionEvent e) {

                int nT= kernelSize.getNumber().intValue();
                int nK = nKernel.getNumber().intValue();
               // int nH = nHidden.getNumber().intValue();
                createBrainFrame(this, LayerConfig.createFIRBrain(dIn, dOut, nT,nK,(float)beta, (float)alpha));
            }
        });
        
    }

    public Iterable<AbstractAction> getActions() {
        return actions;
    }
}

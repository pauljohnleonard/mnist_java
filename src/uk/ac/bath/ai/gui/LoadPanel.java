/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.ProgressMonitor;
import uk.ac.bath.ai.io.CachedImageDataLabelSource;
import uk.ac.bath.ai.io.SequentialImageLabelDataSource;

/**
 *
 * @author pjl
 */
public class LoadPanel extends JPanel {

    int loadCount = 0;
    private final Container container;
    private Actions actions;
    public CachedImageDataLabelSource trainSrc;
    public CachedImageDataLabelSource testSrc;

    public LoadPanel(Container container, String base) throws FileNotFoundException, IOException {
        this.container = container;

        container.add(this);

        JPanel loadPanel = new JPanel();
        loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.Y_AXIS));

     

        trainSrc =
                new CachedImageDataLabelSource(
                new SequentialImageLabelDataSource(
                base, "train"));

        JPanel p = loadData(trainSrc, "Loading training data");

        loadPanel.add(p);
        testSrc =
                new CachedImageDataLabelSource(
                new SequentialImageLabelDataSource(
                base, "t10k"));


        p = loadData(testSrc, "Loading test data");

        loadPanel.add(p);

        add(loadPanel);



    }

    private JPanel loadData(CachedImageDataLabelSource task, String label) {

        loadCount += 1;

        JPanel p = new JPanel();



        final JProgressBar pbar1 = new JProgressBar(0, 100);
        p.add(pbar1);
        JLabel lab = new JLabel(label, JLabel.LEFT);
        lab.setPreferredSize(new Dimension(200, 20));

        p.add(lab);

        class MyListener implements PropertyChangeListener {

            private CachedImageDataLabelSource task;
            private final JProgressBar bar;
            boolean done = false;

            MyListener(CachedImageDataLabelSource task, JProgressBar bar) {
                this.bar = bar;
                this.task = task;
            }

            public void propertyChange(PropertyChangeEvent pce) {
                int progress = task.getProgress();
                bar.setValue(progress);
                if (task.isDone() && !done) {
                    done = true;
                    System.out.println("  LOAD DONE " + this);
                    loadCount -= 1;
                    if (loadCount == 0) {
                        try {
                            createBrainPanel();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(LoadPanel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(LoadPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }
        }


        ProgressMonitor progressMonitor1 = new ProgressMonitor(pbar1,
                "Running a Long Task",
                "", 0, 100);

        progressMonitor1.setProgress(0);
        MyListener listener1 = new MyListener(task, pbar1);




        task.addPropertyChangeListener(listener1);
        task.execute();
        return p;

    }

    void createBrainPanel() throws FileNotFoundException, IOException {


        removeAll();
        setLayout(new FlowLayout());
        
        JPanel panel = this; //new JPanel(new FlowLayout());
        actions = new Actions(trainSrc, testSrc);


        JPanel tt;



        tt = new JPanel();
        tt.add(new JLabel("Neurons in hidden layer 1"));
        tt.add(new JSpinner(actions.nHidden));
        panel.add(tt);



        tt = new JPanel();
        tt.add(new JLabel("Neurons in hidden layer 2"));
        tt.add(new JSpinner(actions.nHidden2));
        panel.add(tt);


        tt = new JPanel();
        tt.add(new JLabel("Number of kernels"));
        tt.add(new JSpinner(actions.nKernel));
        panel.add(tt);


        tt.add(new JLabel("Kernel size (NxN)"));
        tt.add(new JSpinner(actions.kernelSize));
        panel.add(tt);



        for (AbstractAction act : actions.getActions()) {
            //  JMenuItem item = new JMenuItem(act);
            //          menu.add(item);
            JButton but = new JButton(act);
            panel.add(but);
        }


     //   add(panel);
        validate();
        //    menuBar.add(menu);



        //  return menuBar;
    }
}

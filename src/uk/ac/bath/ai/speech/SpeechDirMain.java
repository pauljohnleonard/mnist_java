/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.speech;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;

import uk.ac.bath.ai.gui.Actions;
import uk.ac.bath.ai.io.CachedImageDataLabelSource;
import uk.ac.bath.ai.io.DataWriter;
import uk.ac.bath.ai.io.FileDataSource;
import uk.ac.bath.ai.io.SequentialImageLabelDataSource;

/** DEPRECATED
 * 
 * @author pjl
 */
public class SpeechDirMain extends JFrame {

	static Actions actions;
	public static CachedImageDataLabelSource trainSrc;
	public static CachedImageDataLabelSource testSrc;
	private static JFrame frame;

	static JMenuBar createBrainPanel(JPanel panel)
			throws FileNotFoundException, IOException {

		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");

		menu.add(new JMenuItem(new AbstractAction("Exit") {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}));
		menuBar.add(menu);

		menu = new JMenu("Transplant");

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
			JMenuItem item = new JMenuItem(act);
			menu.add(item);
			JButton but = new JButton(act);
			panel.add(but);
		}

		menuBar.add(menu);

		return menuBar;
	}

	static int loadCount = 1; // rather naff way of counting for 2 tasks to
								// finish.

	private static JPanel loadData(CachedImageDataLabelSource task, String label) {

		JPanel p = new JPanel();

		final JProgressBar pbar1 = new JProgressBar(0, 100);
		p.add(pbar1);
		JLabel lab = new JLabel(label, JLabel.RIGHT);
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
							Logger.getLogger(SpeechDirMain.class.getName()).log(
									Level.SEVERE, null, ex);
						} catch (IOException ex) {
							Logger.getLogger(SpeechDirMain.class.getName()).log(
									Level.SEVERE, null, ex);
						}
					}
				}
			}
		}

		ProgressMonitor progressMonitor1 = new ProgressMonitor(pbar1,
				"Running a Long Task", "", 0, 100);

		progressMonitor1.setProgress(0);
		MyListener listener1 = new MyListener(task, pbar1);

		task.addPropertyChangeListener(listener1);
		task.execute();
		return p;

	}

	private static void createAndShowGUI() throws FileNotFoundException,
			IOException, ClassNotFoundException {
		// JFrame.setDefaultLookAndFeelDecorated(true);

		frame = new JFrame("Brain Launcher");

		JPanel content = new JPanel();
		frame.setContentPane(content);

		JPanel loadPanel = new JPanel();
		loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.Y_AXIS));

		File dir = new File("/tmp/SPEECH/train");

		trainSrc = new CachedImageDataLabelSource(new FileDataSource(dir,
				"train"));

		JPanel p = loadData(trainSrc, "Loading training data");

		loadPanel.add(p);

		dir = new File("/tmp/SPEECH/test");

		testSrc = new CachedImageDataLabelSource(
				new FileDataSource(dir, "test"));

		p = loadData(testSrc, "Loading test data");

		
		loadPanel.add(p);

	//	testSrc=trainSrc;
		
		content.add(loadPanel);

		frame.setSize(new Dimension(400, 600));
		frame.validate();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Note createBrainPanel called when data is loaded.

	}

	static void createBrainPanel() throws FileNotFoundException, IOException {

		JPanel content = new JPanel();
		JMenuBar bar = createBrainPanel(content);
		frame.setContentPane(content);
		frame.validate();
		frame.setJMenuBar(bar);

	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException, InterruptedException, InvocationTargetException {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.

		SwingUtilities.invokeAndWait(new Runnable() {

			public void run() {
				try {
					createAndShowGUI();
				} catch (FileNotFoundException ex) {
					Logger.getLogger(SpeechDirMain.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(SpeechDirMain.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}
}

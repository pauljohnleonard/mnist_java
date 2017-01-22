package uk.ac.bath.ai;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ProgressMonitor;

import uk.ac.bath.ai.io.CachedImageDataLabelSource;
import uk.ac.bath.ai.io.DataWriter;
import uk.ac.bath.ai.io.SequentialImageLabelDataSource;

public class MakeSerializedData {

	public static CachedImageDataLabelSource trainSrc;
	public static CachedImageDataLabelSource testSrc;
	
	public static void main(String args[]) throws FileNotFoundException, IOException {
		// root directory for data
		// if this does not exist data is loaded from the web
		String dataDir = System.getProperty("user.dir") + "/data";
		JFrame frame=new JFrame();
		
		JPanel content = new JPanel();
		frame.setContentPane(content);

		JPanel loadPanel = new JPanel();
		loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.Y_AXIS));

		File dir = new File(dataDir);

		String base;

		if (dir.isDirectory()) {
			System.out.println(" Using local training data");
			base = "file://localhost/" + dataDir + "/";
		} else {
			System.out.println(" Downloading training data from the web");
			base = "http://people.bath.ac.uk/eespjl/courses/CI/demo/NN/";
		}

		trainSrc = new CachedImageDataLabelSource(
				new SequentialImageLabelDataSource(base, "train"));

		JPanel p = loadData(trainSrc, "Loading training data");

		loadPanel.add(p);
		testSrc = new CachedImageDataLabelSource(
				new SequentialImageLabelDataSource(base, "t10k"));

		p = loadData(testSrc, "Loading test data");
		
		loadPanel.add(p);

		content.add(loadPanel);

		frame.setSize(new Dimension(400, 600));
		frame.validate();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	static int loadCount=2;
	
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
							System.out.println(" Writing files . . . . ");
							
							DataWriter w = new DataWriter();
							w.write(testSrc, new File("/home/pjl/mnisnt/test"), "test");
							w.write(trainSrc, new File("/home/pjl/mnisnt/train"), "train");
							System.out.println(" Done  . . . Writing files");
							
						} catch (FileNotFoundException ex) {
							Logger.getLogger(MNISTMain.class.getName()).log(
									Level.SEVERE, null, ex);
						} catch (IOException ex) {
							Logger.getLogger(MNISTMain.class.getName()).log(
									Level.SEVERE, null, ex);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
}

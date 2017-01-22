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
import java.util.ArrayList;
import java.util.List;
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
import uk.ac.bath.ai.io.DataSource;
import config.Config;

/**
 * 
 * @author pjl
 */
public class SpeechDirMain2 extends JFrame {

	static Actions actions;
	public static DataSource trainSrc;
	public static DataSource testSrc;
	private static JFrame frame;
	static Config config = Config.jr();

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

	private static Config conf;

	private static void createAndShowGUI() throws FileNotFoundException,
			IOException, ClassNotFoundException {
		// JFrame.setDefaultLookAndFeelDecorated(true);

		frame = new JFrame("Brain Launcher");

		JPanel content = new JPanel();
		frame.setContentPane(content);

		JPanel loadPanel = new JPanel();
		loadPanel.setLayout(new BoxLayout(loadPanel, BoxLayout.Y_AXIS));

		String testDir = "Anny";

		File base = new File("../JavaSpeechToolData/wavfiles/Dynamic");

		List<String> words = new ArrayList<String>();

		if (true) {
			File dirT = new File(base, testDir);
			for (File child : dirT.listFiles()) {
				String name = child.getName();
				if (".".equals(name) || "..".equals(name)
						|| ".svn".equals(name)) {
					continue; // Ignore the self and parent aliases.
				}
				if (name.contains("_")) {
					int endI = name.indexOf("_");
					words.add(name.substring(0, endI + 1));
					continue;
				}
			}
		}
		
		List<String> subdirs = new ArrayList<String>();

		for (File child : base.listFiles()) {
			String name = child.getName();
			if (".".equals(name) || "..".equals(name) || ".svn".equals(name)) {
				continue; // Ignore the self and parent aliases.
			}
			if (testDir.equals(name)) {
				continue;
			}
			subdirs.add(name);
		}

		trainSrc = new WavFileDataSource(base, subdirs, words, config); // new
																		// CachedImageDataLabelSource(new
																		// WavFileDataSource(base,subdirs,words,config));

		// JPanel p = loadData(trainSrc, "Loading training data");

		// loadPanel.add(p);

		subdirs = new ArrayList<String>();
		subdirs.add("Anny");

		testSrc = new WavFileDataSource(base, subdirs, words, config);

		// p = loadData(testSrc, "Loading test data");

		// loadPanel.add(p);

		// testSrc=trainSrc;

		// content.add(loadPanel);

		createBrainPanel();
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
					Logger.getLogger(SpeechDirMain2.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(SpeechDirMain2.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}
}

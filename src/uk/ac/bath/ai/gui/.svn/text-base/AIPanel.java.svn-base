/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.Timer;

import uk.ac.bath.ai.AIModel;
import uk.ac.bath.ai.image.DisplayImage;
import uk.ac.bath.ai.image.FloatVectorImage;
import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.Result;
import uk.ac.bath.ai.util.Util;
import uk.ac.bath.gui.ConfusionPanel;
import uk.ac.bath.gui.TextTweaker;
import uk.ac.bath.gui.TweakerPanel;
import uk.ac.bath.util.Tweakable;

/**
 * The application's main frame.
 */
public class AIPanel extends JPanel implements Observer {

	// private TrainingFileReader trainingLabelReader;
	// private Brain brain;
	// Random rand = new Random();
	// TrainingFileReader trainingReader;
	private ImagePanel imaginationPanel[];
	private javax.swing.JButton nextTraining;
	private javax.swing.JButton nextTest;
	private javax.swing.JButton allTraining;
	private javax.swing.JProgressBar progressBar;
	private ImageLabelPanel realityPanel;
	private javax.swing.JLabel statusMessageLabel;
	private javax.swing.JPanel statusPanel;
	private AIModel model;
	private JButton allTest;
	private DigitResultPanel digitResultPanel;
	private Timer timer;
	private Progressable progressable;
	private JButton contTraining;
	private JButton stopBut;
	private boolean stop;
	private ImageLabelPanel fantasyPanel;
	private ImageLabel reality;
	private ImageLabel fantasy;
	private JButton oneTraining;
	private JButton saveBut;
	private ImageLabel myLabelImage;
	private FloatVectorImage myImg;
	private boolean manualInput = false;
	Data myData = new Data();
	double [][] confused;
	private ConfusionPanel confusion;
	
	public AIPanel(AIModel model) {
		this.model = model;
		myImg = new FloatVectorImage(model.getImageDimension());
		myLabelImage = new ImageLabel(myImg, 0);
		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(createTopButtons());
		add(createImagePanel());

		JPanel p = new JPanel();

		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		Vector<Tweakable> tweaks = model.getBrain().getTweaks();

		if (tweaks != null) {
			System.out.println(" Added tweaks ");
			TweakerPanel tp = new TweakerPanel(1, tweaks.size());
			tp.setPreferredSize(new Dimension(200, 100));
			for (Tweakable t : tweaks) {
				TextTweaker tt = new TextTweaker(t);
				tp.addComponent(tt.getComponent());
				tp.newRow();
			}
			c.weightx = 1.0;
			p.add(tp, c);
			c.gridx++;

		}

		final JToggleButton manual = new JToggleButton("ManualInput");

		manual.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manualInput = manual.isSelected();
				if (manualInput) {
					myImg.clear();
					realityPanel.setImageLabel(myLabelImage);
				}
			}
		});

		p.add(manual, c);
		c.gridx++;

		ImageClient myClient = new ImageClient() {

			@Override
			public void processImage(FloatVectorImage image) {

				float []image_in = image.getInputVec();
				float []label = Util.nullLabel();
				model.doTest(image_in,label);

			}

		};

		realityPanel = new uk.ac.bath.ai.gui.ImageLabelPanel(3, myClient);

		realityPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Reality"));

		p.add(realityPanel, c);
		c.gridx++;

		digitResultPanel = new DigitResultPanel();

		p.add(digitResultPanel, c);
		c.gridx++;

		add(p);

		
		
		statusPanel = new javax.swing.JPanel();

		javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
		statusMessageLabel = new javax.swing.JLabel();

		add(statusPanel);
		statusPanel.add(statusMessageLabel);
		statusPanel.add(statusPanelSeparator);
		statusPanel.setBorder(javax.swing.BorderFactory
				.createLoweredBevelBorder());
		statusMessageLabel.setText("Why not hit a button");
		progressBar = new JProgressBar();
		statusPanel.add(progressBar);
		timer = new Timer(500, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int progress = 0;
				if (progressable != null) {
					progress = (int) (100 * progressable.getProgress());
				}
				progressBar.setValue(progress);
				update(null, null);
			}
		});

		//int n=model.trainSrc.getLabels().size();
		
		confusion=new ConfusionPanel(model.trainSrc.getLabels());	

		confusion.setPreferredSize(new Dimension(600, 600));
		
		add(confusion);
		
		validate();
		timer.start();
		timer.setRepeats(true);
	}

	static int count = 0;

	JPanel createTopButtons() {

		JPanel buts = new JPanel();

		oneTraining = new javax.swing.JButton();
		buts.add(oneTraining);

		nextTraining = new javax.swing.JButton();
		buts.add(nextTraining);

		allTraining = new javax.swing.JButton();
		buts.add(allTraining);

		contTraining = new javax.swing.JButton();
		buts.add(contTraining);

		nextTest = new javax.swing.JButton();
		buts.add(nextTest);

		allTest = new javax.swing.JButton();
		buts.add(allTest);

		stopBut = new javax.swing.JButton();
		buts.add(stopBut);

		saveBut = new javax.swing.JButton();
		buts.add(saveBut);

		nextTraining.setText("NEXT MISS");

		nextTraining.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				try {
					model.nextMiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// model.dump();
			}
		});

		nextTest.setText("TEST NEXT");

		nextTest.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					model.nextTest();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// model.dump();
			}
		});

		oneTraining.setText("TRAIN ONE");

		oneTraining.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				model.nextTrain();
				// model.dump();
			}
		});

		allTraining.setText("TRAIN ALL");

		allTraining.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				progressable = model;
				Thread t = new Thread() {

					@Override
					public void run() {
						setWorking(true);
						model.allTraining();
						try {
							model.allTest();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						progressable = null;
						setWorking(false);
					}
				};

				t.start();

			}
		});

		contTraining.setText("TRAIN FOREVER");

		contTraining.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				progressable = model;
				Thread t = new Thread() {

					@Override
					public void run() {

						stop = false;
						while (!stop) {
							setWorking(true);
							model.allTraining();
							try {
								model.allTest();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						progressable = null;
						setWorking(false);
					}
				};

				t.start();

			}
		});

		allTest.setText("TEST ALL");

		allTest.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				progressable = model;

				Thread t = new Thread() {

					@Override
					public void run() {
						setWorking(true);
						try {
							model.allTest();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						setWorking(false);
						progressable = null;
					}
				};

				t.start();
			}
		});

		stopBut.setText("STOP");

		stopBut.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stop = true;
				model.stop();

			}
		});

		stopBut.setEnabled(false);

		saveBut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				File file = new File("greymatter.dat");
				File curDir = new File(System.getProperty("user.dir"));

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(curDir);

				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
					try {
						model.save(file);
					} catch (IOException ex) {
						Logger.getLogger(AIPanel.class.getName()).log(
								Level.SEVERE, null, ex);
					}

				}
			}
		});

		saveBut.setText("SAVE");
		return buts;
	}

	void setWorking(boolean yes) {
		oneTraining.setEnabled(!yes);
		allTraining.setEnabled(!yes);
		nextTraining.setEnabled(!yes);
		contTraining.setEnabled(!yes);
		allTest.setEnabled(!yes);
		nextTest.setEnabled(!yes);
		stopBut.setEnabled(yes);
		saveBut.setEnabled(!yes);
	}

	// View into the brain
	JComponent createImagePanel() {

		JScrollPane scroll = new JScrollPane();

		JPanel main = new JPanel();
		scroll.setViewportView(main);

		GridBagLayout bag = new GridBagLayout();
		main.setLayout(bag);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		int n = model.getBrain().getImaginationCount();

		imaginationPanel = new ImagePanel[n];

		int nPerRow = 10;
		LayoutHint dl = model.getLayoutHint();
		if (dl != null) {
			nPerRow = dl.nPerRow;
		}
		int scale = Math.max(1, Math.min(3, 1500 / 30 / nPerRow));

		for (int i = 0; i < n; i++) {
			imaginationPanel[i] = new ImagePanel(scale, null);

			bag.setConstraints(imaginationPanel[i], c);
			c.gridx++;
			if (c.gridx >= nPerRow) {
				c.gridx = 0;
				c.gridy++;
			}
			main.add(imaginationPanel[i]);

			imaginationPanel[i].setBorder(javax.swing.BorderFactory
					.createLoweredBevelBorder());
			imaginationPanel[i].setName("imaginationPanel"); // NOI18N
			DisplayImage imag = model.getBrain().getImagination(i);
			imaginationPanel[i].setImage(imag);
		}

		return scroll;

	}

	public void update(Observable o, Object arg) {

		// System.out.println("UPDATE");

		if (!manualInput) {
			Result result = model.getLatestResult();

			if (result == null) {
				return;
			}

			if (reality == null) {
				reality = new ImageLabel(new FloatVectorImage(result.in.image,
						model.getImageDimension(), "Reality"), -1);
			}

			reality.setData(result.in.image, result.in.label);

			realityPanel.setImageLabel(reality);

			statusMessageLabel.setText(model.getStatusString());
			digitResultPanel.set(result.out.label, result.in.label);
			
			confusion.update(model.confused);
		}

		repaint();

	}
}

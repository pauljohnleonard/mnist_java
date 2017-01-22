package uk.ac.bath.ai;

import uk.ac.bath.ai.util.BatchTrainable;
import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.MyDimension;
import uk.ac.bath.ai.util.Result;
import uk.ac.bath.ai.util.Util;
import uk.ac.bath.ai.gui.LayoutHinter;
import uk.ac.bath.ai.gui.Progressable;
import uk.ac.bath.ai.io.DataSource;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Random;

import javax.swing.Timer;

import uk.ac.bath.ai.gui.LayoutHint;

/**
 * 
 * @author pjl
 */
public class AIModel extends Observable implements Progressable {

	Brain brain;
	private Dimension dimImage;
	private String statusString;
	public DataSource trainSrc;
	private DataSource testSrc;
	private Result result;
	private float progress;
	Random rand = new Random();
	private boolean stop;
	private boolean dispose;

	int trainCount = 0;
	int testCount = 0;
	MyDimension dimensionOut;
	public float[][] confused;
	
	public AIModel(Brain brain, DataSource training, DataSource test)
			throws FileNotFoundException, IOException, Exception {
		this.trainSrc = training;
		this.testSrc = test;
		dimImage = trainSrc.getInDimension();
		dimensionOut = trainSrc.getOutDimension();
		this.brain = brain;
		result = new Result(dimImage, dimensionOut);
		Timer t= new Timer(500,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			//	System.out.println(" Hi Paul");
				notifyObservers();
			}
			
		});
		
		t.start();
	}

	public void dispose() {
		System.out.println(" Disposing  . . . . . ");
		dispose = true;
		stop = true;
	}

	public void dump() {
		brain.dump(); // throw new
						// UnsupportedOperationException("Not yet implemented");
	}

	public Brain getBrain() {
		return brain;
	}

	public void allTraining() {
		// statusString = "Training";

		if (brain instanceof BatchTrainable) {
			((BatchTrainable) brain).doBatchTrain(trainSrc);
		} else {
			int maxx = trainSrc.getSize(); // trainingReader.getSizes()[0];
			for (int i = 0; i < maxx; i++) {

//				Data data = trainSrc.getData(i);
//				
//				
//				brain.doTraining(data);
				doTrain(i);
				progress = (float) (i) / maxx;
				if (stop || dispose) {
					break;
				}
			}
		}
		// statusString = "Done";
		stop = false;
		notifyObservers();

	}

	public void allTest() throws Exception {
		statusString = "Testing";
		if (testSrc == null) {
			System.out.println(" tester is null  . . . .Skipping testing");

		} else {
			
			int n=dimensionOut.n;
			confused = new float[n][n];
			
			
			int maxx = testSrc.getSize();
			int right = 0;
			int wrong = 0;

			for (int i = 0; i < maxx; i++) {
				doTest(i);

				// brain.getLatestResult(result);

				progress = ((float) i) / maxx;
				int guess = -1;
				float maxV = 0.0f;
				float[] x=Util.normalize(result.out.label);
				
				
				int row=Util.toLabel(result.in.label);
				
				for (int k = 0; k < dimensionOut.n; k++) {
					confused[row][k] +=x[k];
					
					if (result.out.label[k] > maxV) {
						maxV = result.out.label[k];
						guess = k;
					}
				}

				if (guess == Util.toLabel(result.in.label)) {
					right++;
				} else {
					wrong++;
				}
				if (stop || dispose) {
					break;
				}
			}

			statusString = "Correct: " + (float) right * 100.0 / maxx + "%";
			
			for(int i=0;i<n;i++){
				confused[i]=Util.normalize(confused[i]);
			}
		
		}
		stop = false;
		notifyObservers();
	}

	public Dimension getImageDimension() {
		return dimImage;
	}

	public LayoutHint getLayoutHint() {
		if (brain instanceof LayoutHinter) {
			return ((LayoutHinter) brain).layoutHint();
		}
		return new LayoutHint(10);
	}

	public void nextMiss() throws Exception {
		int maxx = testSrc.getSize(); // [0];
		long count = 0;

		do {
			doTest((testCount++) % maxx);
			if (stop || dispose) {
				break;
			}
		} while (Util.toLabel(result.out.label) == Util
				.toLabel(result.in.label) && count++ < maxx);
	
	}

	public void doTrain(int index) {

		//statusString = "Training";
	
		Data data=null;
		try {
			data = trainSrc.getData(index);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float label[] = brain.doTraining(data.image,data.label);
		System.arraycopy(label, 0, result.out.label, 0, label.length);
		System.arraycopy(data.label, 0, result.in.label, 0, data.label.length);
		System.arraycopy(data.image, 0, result.in.image, 0, data.image.length);
		setChanged();
		
	}

	public void nextTrain() {
		doTrain(trainCount++);
	}

	public void nextTest() throws Exception {

		// System.out.println(" NEXT TEST");
		int maxx = testSrc.getSize(); // [0];

		doTest((testCount++) % maxx);
	
	}

	public Result getLatestResult() {
		return result;
	}

	private void doTest(int index) throws Exception {
		Data data = testSrc.getData(index);
		doTest(data.image, data.label);
	}

	public void doTest(float[] image, float[] label_in) {
		float[] label = brain.fire(image);
		System.arraycopy(label, 0, result.out.label, 0, label.length);
		System.arraycopy(label_in, 0, result.in.label, 0, label_in.length);
		System.arraycopy(image, 0, result.in.image, 0, image.length);
		setChanged();
		// brain.getLatestResult(result);
	}

	public String getStatusString() {
		return statusString;
	}

	public float getProgress() {
		return progress;
	}

	public void stop() {
		stop = true;
	}

	public void save(File file) throws IOException {

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
				file));
		out.writeObject(brain);
	}

}

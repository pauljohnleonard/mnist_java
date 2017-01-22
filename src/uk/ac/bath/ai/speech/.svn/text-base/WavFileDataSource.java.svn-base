package uk.ac.bath.ai.speech;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import speech.dynamic.WavToFeature;
import uk.ac.bath.ai.io.DataSource;
import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.MyDimension;
import config.Config;

/**
 * 
 * Helper to read a set of training data from a directory of Serialized Datas
 * 
 * Also serialised dimensions for the input and output.
 * 
 * @author pjl
 * 
 * 
 * 
 *         dir/sound/who_lab.wav
 * 
 */

public class WavFileDataSource implements DataSource {

	int count = 0;
	private MyDimension inDim;
	private MyDimension outDim;
	ArrayList<File> files = new ArrayList<File>();
	Data data[];
	private WavToFeature waveToFeat;
	private int rowSize;
	private List<String> names;
	
	public WavFileDataSource(File root, List<String> subjectList,List<String> featList, Config config)
			throws IOException, ClassNotFoundException {
		
		
		names=featList;
		/**
		 * 
		 * Data is stored as
		 * 
		 * root/subject/feat[XX].wav
		 * 
		 * subject is typically a person
		 * feat is The sound
		 * XX is an optional tag if there are multiple samples.
		 * 
		 */
		
		
		System.out.println(" Creating data set\n subjects:"+subjectList+"\n features:"+featList);
		
		if (!root.exists()) {
			throw new IOException(root.getAbsolutePath() + " Does not exist ");
		}

		// count the subdirectories (number of sounds)

		List<Data> dataList=new ArrayList<Data>();

		int featSize = featList.size();
		float feats [][]=new float[featSize][];
		
		for (int i=0;i<featSize;i++){
			float feat[] = new float[featSize];
			feat[i] = 1.0f;
			feats[i]=feat;
		}

		
		for (String sub : subjectList) {
			File dir = new File(root, sub);
			if (!dir.exists()) {
				throw new IOException(dir.getAbsolutePath()
						+ " Subject "+sub+"Does not exist ");
			}

			for (File child : dir.listFiles()) {
				
				int cnt=0;
				for (String feat: featList){
					if (child.getName().startsWith(feat)){
						files.add(child);
						Data data = new Data();
						data.label = feats[cnt];
						dataList.add(data);
						break;
					}	
					cnt++;
				}
			}
		}
	
		waveToFeat = new WavToFeature(config);
		outDim = new MyDimension(featSize, 1);
		rowSize=config.getFeatureVectorSize();
		data = (Data[]) dataList.toArray(new Data[]{});
		
		// Load an image to find out the size
		getData(0);
	}

	@Override
	public MyDimension getInDimension() {
		return inDim;
	}

	@Override
	public MyDimension getOutDimension() {
		return outDim;
	}

	@Override
	public int getSize() {
		return data.length;
	}

	@Override
	public Data getData(int index) throws IOException, ClassNotFoundException {
		
		if (data[index].image != null) return data[index];
		
		File filename = files.get(index);
		try {
			data[index].image=waveToFeat.readFile(filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int xx=data[index].image.length;
		
		if (inDim == null) {
			assert( xx % rowSize == 0);
			int colSize=xx/rowSize;
			inDim=new MyDimension(rowSize,colSize);
		} else {
			assert(xx == inDim.n);
		}
		
		
		return data[index];
	}
	
	public String nameOf(int index){
		return names.get(index);
	}
	
	public List<String> getLabels() {

		return names;
	}

}

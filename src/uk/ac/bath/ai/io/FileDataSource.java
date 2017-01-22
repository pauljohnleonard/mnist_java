package uk.ac.bath.ai.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.MyDimension;

/**
 * 
 * Helper to read a set of training data from a directory of Serialized Datas
 * 
 * Also serialised dimensions for the input and output.
 * 
 * @author pjl
 * 
 */

public class FileDataSource implements DataSource {

	private File dir;
	private String base;
	int count = 0;
	private MyDimension inDim;
	private MyDimension outDim;

	public FileDataSource(File dir, String base) throws IOException,
			ClassNotFoundException {
		this.dir = dir;
		this.base = base;
		if (!dir.exists()) {
			throw new IOException(dir.getAbsolutePath() + " Does not exist ");
		}

		for (File child : dir.listFiles()) {
			if (child.getName().startsWith(base))
				count++;
		}

		System.out.println(" Found " + count + " datas");

		{
			File filename = new File(dir, "dimensionIn.data");
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fis);
			Object o = in.readObject();
			fis.close();
			inDim = (MyDimension) o;
		}
		{
			File filename = new File(dir, "dimensionOut.data");
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fis);
			Object o = in.readObject();
			fis.close();
			outDim = (MyDimension) o;
		}

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
		return count;
	}

	@Override
	public Data getData(int index) throws IOException, ClassNotFoundException {
		File filename = new File(dir, base + "_" + index + ".data");
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream in = new ObjectInputStream(fis);
		Object o = in.readObject();
		fis.close();
		return (Data) o;
	}

	public List<String> getLabels() {
		return null;
	}

	public String nameOf(int feat_id) {

		return Integer.toString(feat_id);
	}
}

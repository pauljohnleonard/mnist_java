/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.io;

import java.util.List;

import javax.swing.SwingWorker;

import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.MyDimension;

/**
 * 
 * @author pjl
 */
public class CachedImageDataLabelSource extends SwingWorker<Void, Void>
		implements DataSource {
	private final MyDimension inDim;
	private final MyDimension outDim;
	private final int size;
	private final Data[] data;
	private final DataSource src;

	public CachedImageDataLabelSource(DataSource src) {
		inDim = src.getInDimension();
		outDim = src.getOutDimension();
		size = src.getSize();
		data = new Data[size];
		this.src = src;

	}

	public MyDimension getInDimension() {
		return inDim;
	}

	public MyDimension getOutDimension() {
		return outDim;
	}

	public int getSize() {
		return size;
	}

	public Data getData(int index) {
		return data[index];
	}

	@Override
	protected Void doInBackground() {

		System.out.println(" READING DATA");
		for (int i = 0; i < size; i++) {
			try {
				data[i] = src.getData(i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setProgress((100 * i) / (size));
		}
		setProgress(100);
		return null;
	}

	public String nameOf(int feat_id) {

		return src.nameOf(feat_id);
	}

	public List<String> getLabels() {

		return src.getLabels();
	}

}

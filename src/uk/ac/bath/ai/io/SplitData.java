package uk.ac.bath.ai.io;

import java.util.List;
import java.util.Random;

import uk.ac.bath.ai.util.Data;
import uk.ac.bath.ai.util.MyDimension;

public class SplitData {

	public DataSource trainSrc;
	public DataSource testSrc;
	public DataSource src;
	int keyTest[];
	int keyTrain[];

	public SplitData(final DataSource src, float probTest) {

		Random rand = new Random();

		int n = src.getSize();

		final int nTest = (int) (n * probTest);
		final int nTrain = n - nTest;

		int cTest = nTest;
		int cTrain = nTrain;

		for (int i = 0; i < n; i++) {

			double wTest = cTest * rand.nextDouble();
			double wTrain = cTrain * rand.nextDouble();

			if (wTest < wTrain) {
				keyTrain[--cTrain] = i;
			} else {
				keyTest[--cTest] = i;
			}
		}

		trainSrc = new DataSource() {

			@Override
			public MyDimension getInDimension() {
				return src.getInDimension();
			}

			@Override
			public MyDimension getOutDimension() {
				return src.getOutDimension();
			}

			@Override
			public int getSize() {
				return nTrain;
			}

			@Override
			public Data getData(int index) throws Exception {
				return src.getData(keyTrain[index]);
			}

			public String nameOf(int feat_id) {

				return src.nameOf(feat_id);
			}

			public List<String> getLabels() {

				return src.getLabels();
			}

		};

		testSrc = new DataSource() {

			@Override
			public MyDimension getInDimension() {
				return src.getInDimension();
			}

			@Override
			public MyDimension getOutDimension() {
				return src.getOutDimension();
			}

			@Override
			public int getSize() {
				return nTest;
			}

			@Override
			public Data getData(int index) throws Exception {
				return src.getData(keyTest[index]);
			}

			public String nameOf(int feat_id) {

				return src.nameOf(feat_id);
			}

			public List<String> getLabels() {

				return src.getLabels();
			}

		};

	}

}

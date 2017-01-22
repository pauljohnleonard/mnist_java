package uk.ac.bath.util;

import java.util.*;

public class TweakableDouble extends Tweakable {

	/**
	 * 
	 * @param min
	 * @param max
	 * @param val
	 * @param step
	 * @param label
	 */
	public TweakableDouble(double min, double max, double val, double step,
			String label) {
		super(label, new Double(val), new Double(min), new Double(max),
				new Double(step));
	}

	/**
	 * 
	 * @param c
	 * @param min
	 * @param max
	 * @param val
	 * @param step
	 * @param label
	 */
	public TweakableDouble(Collection<Tweakable> c, double min, double max, double val,
			double step, String label) {
		super(c, label, new Double(val), new Double(min), new Double(max),
				new Double(step));
	}

	/**
	 * 
	 */
	public void set(String s) {
		try {
			n = new Double(s);
			setChanged();
			notifyObservers();
		} catch (Exception e) {
		} // TODO

	}

}

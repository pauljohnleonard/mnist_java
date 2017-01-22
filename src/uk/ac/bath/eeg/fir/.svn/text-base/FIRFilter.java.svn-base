package uk.ac.bath.eeg.fir;

/**
 * 
 * @author bwm23
 * 
 * A simple FIR filter, uses a circular buffer
 *
 */
public class FIRFilter {
	private int orderOfFilter = 0;
	private double[] delayLine;
	private double[] filterCoefficients;
	
	// Our position in the buffer
	private int bufferPosition = 0;
	
	/**
	 * Setup a filter with length(coeffs)
	 * @param coeffs The filter coefficients.
	 */
	FIRFilter(double[] coeffs){
		orderOfFilter = coeffs.length;
		
		//TODO Do we need to deep copy?
		filterCoefficients = coeffs;
		
		delayLine = new double[orderOfFilter];
	}
	
	/**
	 * Set the new filter coefficients
	 * @param coeffs A double list of new coefficients
	 */
	public void setCoeffs(double[] coeffs){
		
		// Check that no one has tried to make the filter longer!
		if (coeffs.length > orderOfFilter){
			System.out.println("Tried to make a filter longer!");
		}
		else
		{
			//TODO Do we need to deep copy?
			filterCoefficients = coeffs;
		}
		
	}
	
	/**
	 * Get the current filter coefficients
	 * @return A double list of the current coefficients
	 */
	public double[] getCoeffs(){
		return filterCoefficients;
	}
	
	/**
	 * Add a new input sample, and extract the next output sample
	 * @param inputSample The next input sample
	 * @return The next output sample after filtering
	 */
	double getOutputSample(double inputSample){
		delayLine[bufferPosition] = inputSample;
		
		double result = 0.0;
		
		int index = bufferPosition;
		
		for (int i = 0; i < orderOfFilter; i++){
			result += filterCoefficients[i] * delayLine[index--];
			if (index < 0) index = orderOfFilter - 1;
		}
		
		if (++bufferPosition >= orderOfFilter) bufferPosition = 0;
		return result;
	}

}

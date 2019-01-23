// Perceptron
// This class represents a Perceptron. 

public class Perceptron {
	public double [] weights;
	public double learningRate;
	
	/**
	 * Constructs a perceptron given the number of features in the data set
	 * and the learning rate.
	 * @param nFeatures
	 * @param learningRate
	 */
	public Perceptron(int nFeatures, double learningRate)
	{
		weights = new double [nFeatures];
		this.learningRate = learningRate;
	}
	/**
	 * Returns the string representation of the perceptron. This is
	 * simply the list of weights.
	 * @return string representation of perceptron
	 */
	public String toString()
	{
		String result = "";
		for (int i=0; i<weights.length; i++) {
			result += " " + weights[i];
		}
		return result;
	}
	/**
	 * Computes the dot product between the sample and the percptron
	 * weights.
	 * @param sample sample item to be processed
	 * @return dot product between weights and feature vector
	 */
	public double processSample(Sample sample)
	{
		double sum = 0;
		int nFeatures = sample.data.length;
		for (int i=0; i<nFeatures; i++) {
			sum += weights[i] * sample.data[i]; 
		}
		return sum;
	}
	/**
	 * Applies the perceptron to a sample to arrive at a classification.
	 * @param sample
	 * @return true if the items is classified as a positive
	 */
	public boolean classify(Sample sample)
	{
		return processSample(sample) > 0;
	}
	/**
	 * Shuffles the order of the samples
	 * @param nSamples number of samples in training set
	 * @return arrays of sample numbers, in random order
	 */
	private int [] randomSampleOrder(int nSamples)
	{
		int [] sampleIDs = new int[nSamples];
		for (int i=0; i<nSamples; i++) {
			sampleIDs[i] = i;
		}
		for (int i=0; i<nSamples; i++) {
			int tmpSlot = (int)(Math.random() * nSamples);
			int tmp = sampleIDs[i];
			sampleIDs[i] = sampleIDs[tmpSlot];
			sampleIDs[tmpSlot] = tmp;
		}
		return sampleIDs;
	}
	/**
	 * Applies the percptron learning algorithm once to each item
	 * in the training set (in random order).
	 * @param set array of training samples
	 */
	public void nextEpoch(Sample [] set)
	{
		int nSamples = set.length;
		int [] randomOrder = randomSampleOrder(nSamples);
		for (int i=0; i<nSamples; i++) {
			Sample x = set[ randomOrder[i] ];
			int nFeatures = x.data.length;

			double in = processSample(x);
			double desiredOutput = x.isPositive ? +1 : -1;
			double err = desiredOutput - in;
//			double sum = 0;
			for (int j=0; j<nFeatures; j++) {
				weights[j] += learningRate * err * x.data[j];
//				sum += weights[j];
				if (Double.isInfinite(weights[j])) {
					System.out.println("?");
				}
			}
//			for (int j=0; j<nFeatures; j++) {
//				weights[j] /= sum;
//			}
		}
	}
	/**
	 * Applies the perceptron learning algorithm to a set of training data.
	 * This overloaded version of train includes sampleWeights, which could
	 * be used in a an ensemble learning method such as Ada Boost.
	 * @param learningRate learning coefficient
	 * @param set set of training data
	 * @param nEpochs number of epochs to train
	 * @return trained perceptron
	 */
	public static Perceptron train(double learningRate, Sample [] set, int nEpochs)
	{
		int nFeatures = set[0].data.length;
		Perceptron perceptron = new Perceptron(nFeatures, learningRate);
		for (int e=0; e<nEpochs; e++) {
			perceptron.nextEpoch(set);
		}
		return perceptron;
	}
	/**
	 * Applies the perceptron to a set of samples and determines the
	 * percentage of correct classifications.
	 * @param set array of samples
	 * @return percentage of samples correctly classified
	 */
	public double performance(Sample [] set)
	{
		int corr = 0;
		for (int i=0; i<set.length; i++) {
			double output = processSample(set[i]);
			boolean shouldBePositive = set[i].isPositive; 
			if (output > 0 && shouldBePositive) {
				corr++;
			} else if (output < 0 && !shouldBePositive) {
				corr++;
			}
		}
		return corr / (double)set.length;
	}
}

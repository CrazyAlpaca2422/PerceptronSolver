// Training.java
// This is the main driver for Perceptron training.
// The user will be prompted for training and test data, in
// addition to the number of epochs and learning rate.

import java.util.Scanner;
import java.io.*;

public class Training {
	public static Sample [] loadSamples(Scanner inputStream)
	{
		int nSamples = inputStream.nextInt();
		int nFeatures = inputStream.nextInt();
		Sample [] samples = new Sample [nSamples];
		for (int i=0; i<nSamples; i++) {
			samples[i] = new Sample(nFeatures, inputStream);
		}
		return samples;
	}
	public static void main(String[] args) throws Exception
	{
		Scanner keyboard = new Scanner(System.in);
		
		System.out.printf("Enter name of training data: ");
		String trainFname = keyboard.nextLine();

		System.out.printf("Enter name of test data: ");
		String testFname = keyboard.nextLine();
		
		System.out.printf("Number of epochs: ");
		int nEpochs = keyboard.nextInt();
		
		System.out.printf("Learning rate: ");
		double learningRate = keyboard.nextDouble();
		
		Sample [] trainData = loadSamples(new Scanner(new File(trainFname)));
		Sample [] testData = loadSamples(new Scanner(new File(testFname)));
		Perceptron perceptron = Perceptron.train(learningRate, trainData, nEpochs);
		
		System.out.printf("Perceptron weights: %s%n", perceptron.toString());
		System.out.printf("Training performance: %f%n", perceptron.performance(trainData));
		System.out.printf("Testing performance: %f%n", perceptron.performance(testData));
		
		keyboard.close();
	}
}

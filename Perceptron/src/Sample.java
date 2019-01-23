// Sample.java
// Represents a simple data sample. This includes all of the
// numeric attributes and its classification (+ or -).

import java.util.Scanner;

public class Sample {
	public double [] data;
	public boolean isPositive;
	public Sample(int nFeatures, Scanner inputStream)
	{
		data = new double [nFeatures + 1];
		String c = inputStream.next();
		isPositive = c.equals("+");
		for (int i=0; i<nFeatures; i++) {
			data[i] = inputStream.nextDouble();
		}
		data[nFeatures] = 1;
	}
}

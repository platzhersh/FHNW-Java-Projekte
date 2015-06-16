package ch.fhnw.igs.cbir.classifier;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import ch.fhnw.igs.cbir.IClassifier;

public class MyClassifier implements IClassifier {
	
	private Map<String, java.util.Vector<int[]>> learnedSet = null;
	
	public MyClassifier() {
		
	}
	
	/* IMPLEMENT THIS METHOD */
	@Override
	public String classify(int[] histogram) {
		
		String class1 = null;
		String class2 = null;
		String class3 = null;
		double dist1 = Double.MAX_VALUE;
		double dist2 = Double.MAX_VALUE;
		double dist3 = Double.MAX_VALUE;
		
		for (Entry<String, Vector<int[]>> entry : learnedSet.entrySet()) {
			
			for (int[] sample : entry.getValue()) {
				double dist = manhattanDistance(sample, histogram);
				
				if (dist < dist3) {
					if(dist < dist2) {
						if(dist<dist1){
							dist3 = dist2; 
							dist2 = dist1;
							dist1 = dist;
							
							class3 = class2;
							class2 = class1;
							class1 = entry.getKey();
						}

						dist3 = dist2; 
						dist2 = dist;
						
						class3 = class2;
						class2 = entry.getKey();
					}

					dist3 = dist;
					
					class3 = entry.getKey();
				}
			}
		}
//		return class1;
		if (class1.equals(class2))
			return class1;
		else if (class1.equals(class3))
			return class1;
		else
			return class2;
	
		
	}
	
	private double manhattanDistance(int[] x, int[] y) {
		double dist = 0;
		for (int i = 0; i < x.length; i++) {
			dist += Math.abs(x[i] - y[i]);
		}
		return dist;
	}
	
	private double euklidianDistance(int[] x, int[] y) {
		double dist = 0;
		for (int i = 0; i < x.length; i++) {
			dist += Math.pow(x[i] - y[i], 2);
		}
		return Math.sqrt(dist);
	}
	
	private double cosineSimilarity(int[] docVector1, int[] docVector2) {
		double dotProduct = 0.0;
		double magnitude1 = 0.0;
	    double magnitude2 = 0.0;
	    double cosineSimilarity = 0.0;

	    for (int i = 0; i < docVector1.length; i++) //docVector1 and docVector2 must be of same length
	    {
	        dotProduct += docVector1[i] * docVector2[i];  //a.b
	        magnitude1 += Math.pow(docVector1[i], 2);  //(a^2)
	        magnitude2 += Math.pow(docVector2[i], 2); //(b^2)
	    }

	    magnitude1 = Math.sqrt(magnitude1);//sqrt(a^2)
	    magnitude2 = Math.sqrt(magnitude2);//sqrt(b^2)

	    if (magnitude1 != 0.0 | magnitude2 != 0.0) {
	        cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
	    } else {
	        return 0.0;
	    }
	    return cosineSimilarity;
	}
	

	
	/* IMPLEMENT THIS METHOD */
	@Override
	public void learn(Map<String, Vector<int[]>> dataSet) {
		learnedSet = dataSet;
	}
	
}

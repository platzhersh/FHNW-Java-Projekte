package ch.fhnw.igs.cbir.classifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import ch.fhnw.igs.cbir.IClassifier;


public class StatisticClassifier implements IClassifier {

	private final int K;
	private String[] classModel;
	public StatisticClassifier(int k) {
		this.K = k;
	}

	@Override
	public void learn(Map<String, Vector<int[]>> dataSet) {
		Map<String,int[]> model = new HashMap<String, int[]>();
		
		
		//count each VisualWord for each class 
		for(String className : dataSet.keySet())
		{
			int[] totalLHisto = new int[K];
			for(int[] histo : dataSet.get(className))
				for(int i=0;i<K;i++) totalLHisto[i]+=histo[i];
			
			model.put(className, totalLHisto);
		}
		
		
		//set a visualWord to the image class with the most counts 
		this.classModel = new String[K];
		
		for(int i=0;i<K;i++) {
			String maxClass = "unknown";
			int max = Integer.MIN_VALUE;
			
			for(String className : model.keySet())
			{
				if(model.get(className)[i] > max) {
					max = model.get(className)[i];
					maxClass = className;
				}
			}
			
			classModel[i]=maxClass;
		}
		
	}
	
	@Override
	public String classify(int[] histogram) {
		//get the model from the global variable
				
		Map<String,Integer> classCounter = new HashMap<String,Integer>();
				
		//look up the class for each VisualWord in the model
		for(int i=0;i<K;i++) {
			String className = classModel[i];
			if(!classCounter.containsKey(className)) classCounter.put(className,new Integer(0));
			classCounter.put(className, classCounter.get(className)+histogram[i]);
		}
			
		String maxClass = "unknown";
		int max = Integer.MIN_VALUE;
				
		//return the image class with the most VisualWords
		for(String className : classCounter.keySet())
		{
			if(classCounter.get(className) > max) {
				max = classCounter.get(className);
				maxClass = className;
			}
		}
				
		return maxClass;
	}


}

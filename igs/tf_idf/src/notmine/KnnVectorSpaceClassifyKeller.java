package notmine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 
 * @author Lukas Keller
 *
 */
public class KnnVectorSpaceClassifyKeller
{	
	
	static String trainingName = "training1";
	
	//enable/disable stopwords/stemming filtering
	static final boolean filter = true;
	
	private final static boolean debug = false;
	
	/**
	 * 
	 * @param train	all "known" training documents
	 * @param test	all "unknown" test documents
	 * @param k	parameter for the knn majority decision 
	 * 
	 * @return 	an overall error value as sum over all test case errors
	 * 			test case error	
	 * 			0	:	exact classification	C == C1 
	 * 			0.5	:	tie classification		C in {C1 .. Cn}
	 * 			1	:	false classification	C != C1
	 * @throws FileNotFoundException 
	 * 			   
	 * 
	 */
	static double knnClassify(List<IGSDocument> train, List<IGSDocument> test, int k) throws FileNotFoundException  {
		
		double error = 0;
		
		HashMap<String, List<Integer>> invertedIndex = invert(train);
		
		for(IGSDocument doc: train)
		{
			computeDocumentInformation(doc, train.size(), invertedIndex);
		}
		
		for(IGSDocument doc: test)
		{
			computeDocumentInformation(doc, train.size(), invertedIndex);
		}
		
		//Classification
		error = classification(train, test, k, error);
		
		return error;
	}

	private static double classification(List<IGSDocument> train, List<IGSDocument> test, int k, double error) {

		Comparator<Double> doubleReverseComperator = new Comparator<Double>() {
			
			@Override
			public int compare(Double o1, Double o2) {
				return o2.compareTo(o1);
			}
		};
		
		Comparator<Integer> integerReverseComperator = new Comparator<Integer>() {
			
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		};
		
		for(IGSDocument testDoc: test)
		{
			Map<Double, String> sortedMap = new TreeMap<Double, String>(doubleReverseComperator);
			
			for(IGSDocument trainDoc: train)
			{
				double similarity = compare(trainDoc, testDoc);
				sortedMap.put(similarity, trainDoc.documentClass);
			}
			
			int counter = 0;
			
			Map<String, Integer> clazzCounter = new HashMap<String, Integer>();
			
			for(Entry<Double, String> entry:sortedMap.entrySet())
			{
				if(counter>=k)
				{
					break;
				}
				
				String key = entry.getValue();
				
				if(clazzCounter.containsKey(key))
				{
					int value = clazzCounter.get(key);
					clazzCounter.put(key, value+1);
				}
				else
				{
					clazzCounter.put(key, 1);
				}
				
				counter++;
			}
			
			Map<Integer, List<String>> count = new TreeMap<Integer, List<String>>(integerReverseComperator);
			
			for(Entry<String, Integer> entry:clazzCounter.entrySet())
			{
				if(count.containsKey(entry.getValue()))
				{
					count.get(entry.getValue()).add(entry.getKey());
				}
				else
				{
					List<String> clazz = new ArrayList<String>();
					clazz.add(entry.getKey());
					count.put(entry.getValue(),clazz);
				}
			}
				
			double tmpError = 0;
			
			for(Entry<Integer, List<String>> entry:count.entrySet())
			{
//				out.println("TestDoc classified as: " + entry.getValue() + " It is a " + testDoc.documentClass);
				
				if(!entry.getValue().contains(testDoc.documentClass))
				{
					tmpError=1;
				}
				else if(entry.getValue().contains(testDoc.documentClass))
				{
					if(entry.getValue().size()==1)
					{
						tmpError = 0;
					}
					else
					{
						tmpError = 0.5;
					}
				}

				break;	
			}
			
			error+=tmpError;
		}
		return error;
	}
	
	private static double compare(IGSDocument docA, IGSDocument docB)
	{
		/*
		 * cos(theta)=(vnormA*vnormB)/(norm(vnormA)*norm(vnormB)) = (vnormA*vnormB)/(1*1)
		 */
		return dotProduct(docA.vnorm, docB.vnorm);
	}
	
	private static double dotProduct(HashMap<String,Double> vectorA, HashMap<String,Double> vectorB)
	{
		/*
		 * Wichtig: Die Wortvektoren koennen unterschiedlicher Laenge sein! Dies muss jedoch beim Berechnen des Skalarproduktes beruecksichtigt werden!
		 */
		
		double result = 0;
		
		for(Entry<String, Double> entryA: vectorA.entrySet())
		{
			String key = entryA.getKey();

			if(vectorB.containsKey(key))
			{
				result+=entryA.getValue()*vectorB.get(key);
			}
			//otherwise part product is zero
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param docs
	 * @return
	 * @author Lukas
	 */
	private static HashMap<String, List<Integer>> invert(List<IGSDocument> docs)
	{
		HashMap<String, List<Integer>> inverted = new HashMap<>();
		
		for(int i = 0; i<docs.size();i++)
		{
			IGSDocument doc = docs.get(i);
			
			for(String word: doc.words)
			{
				if(inverted.containsKey(word))
				{
					//known word
					
					//add docId only once...
					if(!inverted.get(word).contains(i))
					inverted.get(word).add(i);
				}
				else
				{
					//new unknown word
					List<Integer> invertedList = new ArrayList<Integer>();
					invertedList.add(i);
					inverted.put(word, invertedList);
				}
			}
		}
		
		return inverted;
	}
	
	private static void computeDocumentInformation(IGSDocument doc, int numberOfDocuments, HashMap<String, List<Integer>> inverted)
	{
		//calculate word vector
		HashMap<String, Double> vector = computeWordVector(doc);
		
		//calculate vnorm
		HashMap<String, Double> vectorNorm = computeWordVectorNorm(doc, vector);
		
		//calculate TFID
		computeTFIDF(doc, numberOfDocuments, inverted, vectorNorm);
		
	}

	private static HashMap<String, Double> computeWordVectorNorm(IGSDocument doc, HashMap<String, Double> vector) 
	{
		HashMap<String, Double> vectorNorm = new HashMap<String, Double>();
		
		double squaredSum = 0;
		
		for(Entry<String,Double> entry: vector.entrySet())
		{
			squaredSum+=Math.pow(entry.getValue(),2);
		}
		
		double length = Math.sqrt(squaredSum);
		
		for(Entry<String,Double> entry: vector.entrySet())
		{
			vectorNorm.put(entry.getKey(), entry.getValue()/length);
		}
		
		doc.vnorm = vectorNorm;
		
		return vectorNorm;
	}

	private static void computeTFIDF(IGSDocument doc, int numberOfDocuments, HashMap<String, List<Integer>> inverted, HashMap<String, Double> vector) 
	{
		for(String word: doc.words)
		{
			double n = 1;
			
			if(inverted.containsKey(word))
			{
				n = inverted.get(word).size()+1;
			}
			
			doc.tfidfs.put(word, Math.log((numberOfDocuments+1)/n)*vector.get(word));
		}
	}

	private static HashMap<String, Double> computeWordVector(IGSDocument doc) {
		HashMap<String,Double> vector = new HashMap<String, Double>();
		
		for(String word: doc.words)
		{
			if(vector.containsKey(word))
			{
				//known word
				double value = vector.get(word);
				vector.put(word, value+1);
			}
			else
			{
				//unknown word
				vector.put(word,1d);
			}
		}
		return vector;
	}

  
  public static void main(String[] _args) throws Exception
  {
	long time = System.currentTimeMillis();
	
	String name = trainingName+"_";
	
	if(filter)
	{
		name+="withFilter";
	}
	else
	{
		name+="withoutFilter";
	}
	
	PrintWriter errorWritter = new PrintWriter(name+"Error.out");
	
    //read all training documents
    List<IGSDocument> training = readDocuments(trainingName);
    
    //read all test documents
    List<IGSDocument> test = readDocuments("test");
    
    //classify each test document based on training set for different k 
    for(int k=1; k<71; k++)
    	errorWritter.println(knnClassify(training,test,k));
    
	
	errorWritter.close();
	
	long endtime = System.currentTimeMillis()-time;
	System.out.println("time: " + endtime);
  }
  
  /**
   * read input documents and apply stemming
   * @param dirName the directory for all documents
   * @return a list of read documents from the directory
   */
  static List<IGSDocument> readDocuments(String dirName) 
  {
	List<IGSDocument> res = new LinkedList<IGSDocument>();
	File actual = new File(dirName);
	for(File f : actual.listFiles()) {
		IGSDocument doc = new IGSDocument(f,filter);
		res.add(doc);
		if (debug) System.out.println(doc);
	}

    return res;
  }
}

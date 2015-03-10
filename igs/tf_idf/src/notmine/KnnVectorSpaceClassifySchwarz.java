package notmine;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class KnnVectorSpaceClassifySchwarz
{	
	
	//enable/disable stopwords/stemming filtering
	static final boolean filter = false;
	
	static final String TRAININGSET = "training2";
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	static double knnClassify(List<IGSDocument> train, List<IGSDocument> test, int k) throws Exception  {
		
		double error = 0;
		
		// Indexing
		calcTfidfs(train, test); // also calcs vnorm's
		
		
		// Classification
		for (IGSDocument testDoc : test) {
			List<String> kBestNNs = getKBestNNs(train, testDoc, k);
			List<String> topClasses = determineTopClasses(kBestNNs);
			double testDocError = calcError(topClasses, testDoc);
			error += testDocError;
		}
		
		// Cleaning up
		resetTFs(train, test);
		
		return error;
	}
	
	/**
	 * 
	 * Calculates the TF for each word in each train and test document
	 * and stores it in tfidfs in the document. Also calculates the inverted
	 * file. Calls fillTfidfs to calculate the actuall tfidfs.
	 * 
	 * @param train	all "known" training documents
	 * @param test	all "unknown" test documents
	 * 
	 * @throws Exception 
	 * 			   
	 * 
	 */
	private static void calcTfidfs(List<IGSDocument> train, List<IGSDocument> test) throws Exception {
		
		double N = (double) (train.size() + test.size());
		
		HashMap<String, HashSet<String>> invertedFile = new HashMap<String, HashSet<String>>();

		for (IGSDocument igs : train) {
			for (String word : igs.words) {
				igs.tfidfs = incHashValue(igs.tfidfs, word); // calcs only TF
				invertedFile = addToInvFile(invertedFile, word, igs.id);
			}
		}
		
		for (IGSDocument igs : test) {
			for (String word : igs.words) {
				igs.tfidfs = incHashValue(igs.tfidfs, word); // calcs only TF
				invertedFile = addToInvFile(invertedFile, word, igs.id);
			}
		}
				
		fillTfidfs(train, test, invertedFile, N);
	}
	
	/**
	 * 
	 * Puts a word in a HashMap or increments its value if it already exists
	 * 
	 * @param map a map with String key and Double values
	 * @param word a word to put in the map or increment its value if it already exists
	 */
	private static HashMap<String, Double> incHashValue(HashMap<String, Double> map, String word) {
		if (map.containsKey(word)) {
			map.put(word, map.get(word) + 1);
		} else {
			map.put(word, 1.0);
		}
		return map;
	}
	
	/**
	 * 
	 * Adds the id of a Document to a word 
	 * 
	 * @param invertedFile a file that shows which words exist in which documents
	 * @param word to which a document id is to be added
	 * @param id of the document to be added to a word
	 * 
	 * @return 	a hashmap with the updated values
	 * 			   
	 * 
	 */
	private static HashMap<String, HashSet<String>> addToInvFile (HashMap<String, HashSet<String>> invertedFile, String word, String id) {
		if (invertedFile.containsKey(word)) {
			invertedFile.get(word).add(id);
		} else {
			HashSet<String> hs = new HashSet<String>();
			hs.add(id);
			invertedFile.put(word, hs);
		}
		return invertedFile;
	}
	
	/**
	 * 
	 * replaces the TFs with the TF-IDFs in the tfidfs HashMap of a Document. Also
	 * calls calcNorms to calculate the norm to each document.
	 * 
	 * @param train	all "known" training documents
	 * @param test	all "unknown" test documents
	 * @param invertedFile a file that shows in which documents a word exists
	 * @param N the sum of train and test 
	 * 			   
	 * 
	 */
	private static void fillTfidfs (List<IGSDocument> train, List<IGSDocument> test, HashMap<String, HashSet<String>> invertedFile, double N) throws Exception {
		
		HashMap<String, Double> normPerDoc = new HashMap<String, Double>();
		
		for (IGSDocument igs : train) {
			for (String word : igs.tfidfs.keySet()) {
				double TF = igs.tfidfs.get(word);
				double nj = invertedFile.get(word).size();
				double toPut = TDIDF(TF, N, nj);
				igs.tfidfs.put(word, toPut);
				normPerDoc = incHashValue(normPerDoc, igs.id, (toPut * toPut));
			}
		}
		
		for (IGSDocument igs : test) {
			for (String word : igs.tfidfs.keySet()) {
				double TF = igs.tfidfs.get(word);
				double nj = invertedFile.get(word).size();
				double toPut = TDIDF(TF, N, nj);
				igs.tfidfs.put(word, toPut);
				normPerDoc = incHashValue(normPerDoc, igs.id, (toPut * toPut));
			}
		}
		
		for (String key : normPerDoc.keySet()) {
			normPerDoc.put(key, Math.sqrt(normPerDoc.get(key)));
		}
				
		calcNorms(normPerDoc, train, test);
	}
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	private static void calcNorms(HashMap<String, Double> normPerDoc, List<IGSDocument> train, List<IGSDocument> test) throws Exception {
		
		for (IGSDocument igs : train) {
			for (String key : igs.tfidfs.keySet()) {
				double value = igs.tfidfs.get(key) / normPerDoc.get(igs.id);
				igs.vnorm.put(key, value);
			}
		}
		
		for (IGSDocument igs : test) {
			for (String key : igs.tfidfs.keySet()) {
				double value = igs.tfidfs.get(key) / normPerDoc.get(igs.id);
				igs.vnorm.put(key, value);
			}
		}
	}
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	private static double compareIGSDocs(IGSDocument doc1, IGSDocument doc2) {
		double dotprod = 0;
		for (String word : doc1.vnorm.keySet()) {
			if (doc2.vnorm.containsKey(word)) {
				dotprod += doc1.vnorm.get(word) * doc2.vnorm.get(word);
			}
		}
		return dotprod;
	}
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	private static List<String> getKBestNNs(List<IGSDocument> train, IGSDocument testdoc, int k) throws Exception {
		HashMap<String, List<Double>> nns = new HashMap<String, List<Double>>();
		LinkedList<String> classes = new LinkedList<String>();

		for (IGSDocument traindoc : train) {
			double costheta = compareIGSDocs(traindoc, testdoc);
			addCosTheta(nns, traindoc.documentClass, costheta);
		}

		for (String key : nns.keySet()) {
			List<Double> sortedList = nns.get(key);
			Collections.sort(sortedList, Collections.reverseOrder());
			nns.put(key, sortedList);
		}

		for (int i = 0; i < k; i++) {
			String biggestClass = findBiggestAngleClass(nns);
			List<Double> reducedList = nns.get(biggestClass);
			reducedList.remove(0);
			nns.put(biggestClass, reducedList);
			classes.add(biggestClass);
		}
		
		return classes;
	}
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	private static String findBiggestAngleClass(HashMap<String, List<Double>> nns) throws Exception {
		String biggestClass = null;
		double biggestAngle = Double.MIN_VALUE;
		
		for (String key : nns.keySet()) {
			if (!nns.get(key).isEmpty()) {
				biggestClass = key;
				biggestAngle = nns.get(key).get(0);
			}
		}
		
		if (biggestClass == null) throw new Exception("All Classes empty - K is too big");
		
		for (String key : nns.keySet()) {
			if (!nns.get(key).isEmpty()) {
				if (nns.get(key).get(0) > biggestAngle) {
					biggestClass = key;
					biggestAngle = nns.get(key).get(0);
				}
			}
		}
		
		return biggestClass;
	}
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	private static void addCosTheta(HashMap<String, List<Double>> nns, String docClass, double costheta) {
		if (nns.containsKey(docClass)) {
			nns.get(docClass).add(costheta);
		} else {
			LinkedList<Double> thetas = new LinkedList(); thetas.add(costheta);
			nns.put(docClass, thetas);
		}
	}
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	private static List<String> determineTopClasses(List<String> kBestNNs) {
		HashMap<String, Double> nns = new HashMap<String, Double>();
		LinkedList<String> topClasses = new LinkedList<String>();

		for (String docClass : kBestNNs) {
			nns = incHashValue(nns, docClass);
		}		
		List sortedList = sortMap(nns);
				
		double topValue = (Double) ((Map.Entry) sortedList.get(0)).getValue();
		
		for (int i = 0; i < sortedList.size(); i++) {
			double currVal = (Double) ((Map.Entry) sortedList.get(i)).getValue();
			if (currVal == topValue) {
				String docClass = (String) ((Map.Entry) sortedList.get(i)).getKey();
				topClasses.add(docClass);
			}
		}
		
		return topClasses;
	}
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	private static double calcError(List<String> topClasses, IGSDocument test) {
					
		double error = Double.MIN_VALUE;
		
		if (topClasses.contains(test.documentClass)) {
			if (topClasses.size() == 1) {
				error =  0;
			} else {
				error = 0.5;
			}
		} else {
			error = 1;
		}
		
		return error;
	}
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	@SuppressWarnings("unchecked")
	private static List sortMap(Map map) {
		List sortList = new LinkedList(map.entrySet());
		Collections.sort(sortList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
			}
		});
		return sortList;
	}
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	private static double TDIDF(double TF, double N, double nj) {
		return Math.log((N+1)/(nj+1)) * TF;
	}
	
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
	 * @throws Exception 
	 * 			   
	 * 
	 */
	private static HashMap<String, Double> incHashValue(HashMap<String, Double> map, String id, double value) {
		if (map.containsKey(id)) {
			map.put(id, map.get(id) + value);
		} else {
			map.put(id, value);
		}
		return map;
	}
	
	private static void resetTFs(List<IGSDocument> train, List<IGSDocument> test) {
		for (IGSDocument igs : train) {
			igs.tfidfs.clear();
			igs.vnorm.clear();
		}
		
		for (IGSDocument igs : test) {
			igs.tfidfs.clear();
			igs.vnorm.clear();
		}
	}
  
  public static void main(String[] _args) throws Exception
  {
	  
    //read all training documents
    List<IGSDocument> training = readDocuments(KnnVectorSpaceClassifySchwarz.TRAININGSET);
    
    //read all test documents
    List<IGSDocument> test = readDocuments("test");
    
    
    //classify each test document based on training set for different k 
    System.out.println("k\ttotal error ");
    
    int K = 71;
    
    long start = System.currentTimeMillis();
    for(int k=1; k<K; k++) System.out.println(knnClassify(training,test,k)); //System.out.println(k+"\t"+knnClassify(training,test,k)); 
    long end = System.currentTimeMillis();
    long duration = end - start;
    System.out.println("duration: " + duration);
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

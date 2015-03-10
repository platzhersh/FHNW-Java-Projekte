import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;


public class KnnVectorSpaceClassify
{	
	
	//enable/disable stopwords/stemming filtering
	static final boolean filter = true;
	
	private final static boolean debug = false;
	
	/**
	 * TODO:
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
	 * 			   
	 * 
	 */
	static double knnClassify(List<IGSDocument> train, List<IGSDocument> test, int k)  {
				
		List<IGSDocument> traindump = new ArrayList<>(train);

        calcTfIdfvAM(test, traindump);
		
		// 1. Vector Space
		 
		// 2. TF-IDF
		
		// 3. Normalisieren
		// 4. similarity
		// 5. klassifizieren
		// 6. Fehler berechnen
		
		
		double error = 0;
		
		return error;
	}
	
	private static void calcTfIdfvAM (List<IGSDocument> docs2search, List<IGSDocument> list) {
        HashMap<String, Integer> ntds = new HashMap<>(); // AM: (word, #documents containing this word at least once)
        HashMap<String, Boolean> ntdsisset = new HashMap<>(); // AM: (Indicates whether specified doc affected ntds already or not)      

//      List<IGSDocument> list = new ArrayList<>(docs2search);

        list.addAll(docs2search);
	}
	
	public HashMap<String, Double> computeTFIDF(List<IGSDocument> documents) {
		
		// Hashmap erstellen mit allen Wörtern die in den verschiedenen Files vorkommen
		HashMap<String, Double> allWords = new HashMap<String, Double>();
		HashMap<String, Double> nj = new HashMap<String, Double>(); // count in how many documents each word occurs

		
		// fill Hashmap with words
		for (IGSDocument doc : documents) {
			
			// count every word
			for (String word : doc.words) {
				doc.vnorm = new HashMap<String, Double>();	// use to count occurences in current doc at first
				
				String trimmed = word.trim();
				
				allWords.put(trimmed, 0.0);				
				
				if (doc.vnorm.get(trimmed) == null) {
					doc.vnorm.put(trimmed, 1.0);
					
					if (nj.get(trimmed) == null) nj.put(trimmed, 1.0);
					else nj.put(trimmed, nj.get(trimmed)+1);
				}
				else {
					doc.vnorm.put(trimmed, doc.vnorm.get(trimmed)+1);
				}
				
			}
			// calculate TF depending on word count and number of words
			for (Entry<String, Double> e : doc.vnorm.entrySet()) {
				e.setValue(e.getValue() / doc.words.length);
			}
		}
		
		// do IDF, normalize
		int N = documents.size();	// number of documents
				
		
		for (IGSDocument doc : documents) {
			
			doc.vnorm = new HashMap(allWords); // use to count occurences in current doc at first
			
			// TODO: = tf * idfs = tf * log ( (1 + N) / (1 + nj) )
			//doc.tfidfs = doc.vnorm
			
			int length = doc.words.length;
						
			for (String word : doc.words) {
				//doc.vnorm.putIfAbsent(key, value)
				
				if (doc.vnorm.put(word.trim(), doc.vnorm.get(word.trim())) == 0.0) 
					nj.put(word.trim(), nj.get(word.trim()+1));
			}
		}

		
		//Math.log((1+N)/(1+nj));	// TF-IDF
		
		return new HashMap<String, Double>();
	}


  
  public static void main(String[] _args) throws Exception
  {
	  
    //read all training documents
    List<IGSDocument> training = readDocuments("training1");
    
    //read all test documents
    List<IGSDocument> test = readDocuments("test");
    
    
    //classify each test document based on training set for different k 
    System.out.println("k\ttotal error ");
    for(int k=1; k<71; k++) System.out.println(k+"\t"+knnClassify(training,test,k)); 
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

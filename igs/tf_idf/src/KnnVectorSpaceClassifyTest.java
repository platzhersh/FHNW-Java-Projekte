import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class KnnVectorSpaceClassifyTest {

	// enable/disable stopwords/stemming filtering
	static final boolean filter = true;

	private final static boolean debug = false;

	/**
	 * 
	 * @param train
	 *            all " known " training documents
	 * @param test
	 *            all " unknown " test documents
	 * @param k
	 *            parameter for the knn majority decision
	 * 
	 * @return an overall error value as sum over all test case errors test case
	 *         error 0 : exact classification C == C1 0.5 : tie classification C
	 *         in {C1 .. Cn} 1 : false classification C != C1
	 * 
	 * 
	 */
	@SuppressWarnings(" unchecked")
	static double knnClassify(List<IGSDocument> train, List<IGSDocument> test,
			int k) {

		double error = 0;

		calcTfIdf(train);
		calcTfIdf(test);

		// Cosine similarity
		for (IGSDocument d : test) {
			LinkedList<IGSDocument> nn = new LinkedList<IGSDocument>();

			HashMap<IGSDocument, Double> comp = new HashMap<IGSDocument, Double>();
			for (IGSDocument e : train)
				comp.put(e, compareDocs(d, e));

			
			List sortList = sortMap(comp);

			// Skip first element (d == e)
			sortList.remove(0);

			for (int i = 0; i < k && i < sortList.size(); i++)
				nn.add((IGSDocument) ((Map.Entry) sortList.get(i)).getKey());

			HashMap<String, Integer> docClass = new HashMap<String, Integer>();

			for (IGSDocument f : nn) {
				if (docClass.containsKey(f.documentClass))
					docClass.put(f.documentClass,
							docClass.get(f.documentClass) + 1);
				else
					docClass.put(f.documentClass, 1);
			}

			sortList = sortMap(docClass);

			float docError = 0;

			docError += d.documentClass.equals(((Map.Entry) (sortList.get(0)))
					.getKey()) ? 0 : 1;

			if (docError == 1) {
				for (int i = 0; i < sortList.size(); i++) {
					if (d.documentClass.equals(((Map.Entry) (sortList.get(i)))
							.getKey())) {
						docError = 0.5f;
						break;
					}
				}
			}

			error += docError;
		}

		return error;
	}

	private static void calcTfIdf(List<IGSDocument> list) {
		HashMap<String, Double> vector = new HashMap<String, Double>();

		// Add all words to comparison vector
		for (IGSDocument d : list) {
			for (String w : d.words)
				vector.put(w, 0.0);
		}

		// calculate tfidfs
		for (IGSDocument d : list) {

			d.vnorm = new HashMap<>(vector);

			for (String w : d.words) {
				// Term Frequency
				double tf = 0;
				for (String c : d.words) {
					if (c.equals(w))
						tf++;
				}

				// Normalize
				tf = tf / (double) d.words.length;

				// Count of documents containing term
				int ntd = 0;
				for (IGSDocument e : list) {
					for (String t : e.words) {
						if (t.equals(w)) {
							ntd++;
							break;
						}
					}
				}

				// Inverse Document Frequency
				int n = list.size();
				double idf = Math.log(n / (double) ntd);

				// Result
				double tfidf = tf * idf;
				d.tfidfs.put(w, tfidf);
				d.vnorm.put(w, tfidf);
			}
		}
	}

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

	public static double compareDocs(IGSDocument one, IGSDocument two) {
		Double[] o = new Double[one.vnorm.size()];
		Double[] t = new Double[two.vnorm.size()];

		one.vnorm.values().toArray(o);
		two.vnorm.values().toArray(t);

		double dot = 0;
		double root1 = 0;
		double root2 = 0;
		for (int i = 0; i < o.length; i++) {
			dot += o[i] * t[i];

			root1 += o[i] * o[i];
			root2 += t[i] * t[i];
		}

		double len1 = Math.sqrt(root1);
		double len2 = Math.sqrt(root2);

		return dot / (len1 * len2);
	}

	public static void main(String[] _args) throws Exception {

		// read all training documents
		List<IGSDocument> training = readDocuments("training1");

		// read all test documents
		List<IGSDocument> test = readDocuments("test");

		// classify each test document based on training set for different k
		System.out.println("k\ttotal error ");
		for (int k = 1; k < 71; k++)
			System.out.println(k + "\t" + knnClassify(training, test, k));
	}

	/**
	 * read input documents and apply stemming
	 * 
	 * @param dirName
	 *            the directory for all documents
	 * @return a list of read documents from the directory
	 */
	static List<IGSDocument> readDocuments(String dirName) {
		List<IGSDocument> res = new LinkedList<IGSDocument>();
		File actual = new File(dirName);
		for (File f : actual.listFiles()) {
			IGSDocument doc = new IGSDocument(f, filter);
			res.add(doc);
			if (debug)
				System.out.println(doc);
		}

		return res;
	}
}

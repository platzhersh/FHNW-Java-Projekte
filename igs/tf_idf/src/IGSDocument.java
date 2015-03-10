import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

//this class represents a students document
public class IGSDocument implements Comparable<IGSDocument> {

	
	//the document, word by word
	String[] orig;
	
	//the document after stopword filtering and stemming
	String[] words;
	
	//the "true" class of a given document
	String documentClass;
	
	//the unique document identifier
	String id;
	
	HashMap<String, Double> tfidfs = new HashMap<String, Double>();
	HashMap<String, Double> vnorm = new HashMap<String, Double>();

	IGSDocument(File file, boolean filter) {
		this.id = file.getName();
		this.documentClass = file.getName().split("_")[0];
		
		try {
			
			
			ArrayList<String> _words = new ArrayList<String>(100);
			
			TokenStream stream = new WhitespaceAnalyzer(Version.LUCENE_CURRENT).tokenStream(null, new FileReader(file));
			CharTermAttribute cattr = stream.addAttribute(CharTermAttribute.class);
			stream.reset();
			while (stream.incrementToken()) {
				_words.add(cattr.toString().toLowerCase().replaceAll("[^a-zφόδ]", ""));
			}
			
			this.orig = _words.toArray(new String[_words.size()]);
			
			if (filter) {
				TokenStream streamf = new GermanAnalyzer(Version.LUCENE_CURRENT).tokenStream(null, new FileReader(file));
				CharTermAttribute cattrf = streamf.addAttribute(CharTermAttribute.class);
				streamf.reset();
				ArrayList<String> _filteredwords = new ArrayList<String>(100);
				while (streamf.incrementToken()) {
					_filteredwords.add(cattrf.toString());
				}
				streamf.end();
				streamf.close();
				
				this.words = _filteredwords.toArray(new String[_filteredwords.size()]);
				
			} else {
				this.words = new String[this.orig.length];
				System.arraycopy(this.orig, 0, this.words, 0, this.orig.length);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("ID:\t"+this.id+"\n");
		sb.append("Class:\t"+this.documentClass+"\n");
		sb.append("orig:\t["+IGSDocument.implodeArray(this.orig)+"]\n");
		sb.append("words:\t["+IGSDocument.implodeArray(this.words)+"]");
		
		return sb.toString();
	}
	
	public static String implodeArray(String[] inputArray) {

		String glueString = "|";
		/** Output variable */
		String output = "";

		if (inputArray.length > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(inputArray[0]);

			for (int i=1; i<inputArray.length; i++) {
				sb.append(glueString);
				sb.append(inputArray[i]);
			}

			output = sb.toString();
		}

		return output;
		}

	@Override
	public int compareTo(IGSDocument o) {
		if (o==null) return -1;
		return this.id.compareTo(o.id);
	}
}

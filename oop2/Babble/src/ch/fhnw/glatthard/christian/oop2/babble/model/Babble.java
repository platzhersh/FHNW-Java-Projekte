package ch.fhnw.glatthard.christian.oop2.babble.model;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/***
 * Business Logic
 * Defines the backend of the application
 * @author chregi
 *
 */
public class Babble {
	
	// To Do: clean MVC architecture -> Lecture_04_Swing2 (26-30 / 37)
	
	// Initialization of global variables
	public int windowSize;	// can be changed in view
	
	private String bof = null;
	private String eof = null;
	
	// HashMap of all Patterns
	static HashMap<String,BabblePattern> patternList = new HashMap<String,BabblePattern>();
	
	/**
	 * This method scans the given Text and stores the patterns and also the possible followers of them
	 * @param text Text to analyze
	 * @param windowSize number of chars belonging to 1 pattern
	 */
	public void analyzeText(String text, int windowSize){
		
		// empty HashMap
		patternList.clear();
		
		// Mark Beginning and End of File (EOF & BOF) so we know when we reach the end of text while babbling
		text = "~"+text+"~";
		bof = text.substring(0, windowSize);
		eof = text.substring(text.length()-windowSize);
		
		/* DEBUGGING */
		System.out.println("BOF: "+bof);
		System.out.println("EOF: "+eof);
		
		for(int i = 0; (i+windowSize) <= text.length(); i++) {
			String pattern = text.substring(i, i + windowSize);
			if(!(patternList.containsKey(pattern))) {	// unnecessary?
				patternList.put(pattern, new BabblePattern());
			}

			if(i+windowSize < text.length()) {
			patternList.get(pattern).addNext(text.charAt(i+windowSize));
			}
			
		}
		System.out.println(patternList.keySet().toString());
	}
	
	/***
	 * Like THE method of the whole application. Randomly puts together a Babble Text regarding windowSize.
	 * @param text			text to babble
	 * @param windowSize	windowSize for the Patterns, smaller patterns -> more nonsense text
	 * @return
	 */
	public String babble(String text, int windowSize) {

		String output = null;
		
		// Start the babble text with BOF (we'll remove the leading '~' later..)
		output = bof;
			
		int i = 1;
		String patternText = bof;
		Character nextChar;
		BabblePattern patternObject; 
		do {
			patternObject = patternList.get(patternText);
			nextChar = patternObject.getNext();
			//output.add(nextChar.toString());
			output += nextChar.toString();
			patternText = patternText.substring(1) + nextChar.toString();
			i++;
		} while ((nextChar != '~') && (i < 4000));
		
		// remove ~ Chars
		output = output.substring(1,output.length()-1);
		return output;
	}
	
	public void printPatterns() {
		System.out.println((patternList.keySet().toString()));
		//for (BabblePattern pattern : patternList)
		}
	
	
	/***
	 * Reads in a file and returns it's value as a String
	 * @param filePath absolute path to the file you want to read in
	 * @return file content as String
	 */
	public String fileToString(String filePath) {
		
		String fileContent;
		
		StringBuffer fileData = new StringBuffer(1000);
        FileInputStream FIS = null;
		try {
			FIS = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(FIS));
        char[] buf = new char[1024];
        int numRead=0;
        try {
			while((numRead=reader.read(buf)) != -1){
			    String readData = String.valueOf(buf, 0, numRead);
			    fileData.append(readData);
			    buf = new char[1024];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        fileContent = fileData.toString();
        return fileContent;
	}
	
	public Integer getPatternCount(){
		Integer count = patternList.size();
		return count;
	}
	
}

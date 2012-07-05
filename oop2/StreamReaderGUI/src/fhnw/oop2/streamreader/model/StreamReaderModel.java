package fhnw.oop2.streamreader.model;

import java.io.File;

public class StreamReaderModel {
	
	public StreamReaderModel() {
		
	}
	
	private File file;
	
	
	public String getInfo(String s) {
		if (validateFile(s)) {
			return "blubb";
		}
	}
	
	public boolean validateFile(String filePath) {
		
		file = new File(filePath);
		try {
			if (file.exists()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			errorMessage.setText("Fehler!" + e.getMessage());
		}
	}

}

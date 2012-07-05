package fhnw.oop2.streamreader.main;

import fhnw.oop2.streamreader.controller.StreamReaderController;
import fhnw.oop2.streamreader.model.StreamReaderModel;
import fhnw.oop2.streamreader.view.StreamReaderView;

public class Main {

	
	static StreamReaderController controller;
	static StreamReaderView view;
	static StreamReaderModel model; 
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		model = new StreamReaderModel();
		view = new StreamReaderView();
		controller = new StreamReaderController(model, view);
		
		view.setVisible(true);
	}

}

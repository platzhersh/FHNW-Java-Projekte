package fhnw.oop2.streamreader.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fhnw.oop2.streamreader.model.StreamReaderModel;
import fhnw.oop2.streamreader.view.StreamReaderView;

public class StreamReaderController {
	
	private StreamReaderModel model;
	private StreamReaderView view;
	
	/* Constructor */
	public StreamReaderController(StreamReaderModel model, StreamReaderView view) {
		this.model = model;
		this.view = view;
		
		addListener();
	}
	
	private void addListener() {
		this.view.setInfoButtonListener(new InfoButtonListener();)
	}
	
	/* 
	 * Action Listener Klassen 
	 */
	
    class InfoButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
       		if (view.getFileName().compareTo("") != 0) {
       			model.getInfo(view.getFileName());
       		}
        }
    }
}

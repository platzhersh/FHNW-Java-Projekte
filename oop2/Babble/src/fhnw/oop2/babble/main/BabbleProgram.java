package fhnw.oop2.babble.main;

import fhnw.oop2.babble.control.*;
import fhnw.oop2.babble.model.*;
import fhnw.oop2.babble.view.*;

public class BabbleProgram {
	
	private static BabbleProgram mainProgram;
	private BabbleView view;
	private BabbleControl control;
	private Babble model;
	

	public static void main(String[] args) {
			// Singleton pattern
			if (mainProgram == null) mainProgram = new BabbleProgram();
			}
	
	private BabbleProgram() {
		model = new Babble();
		view = new BabbleView(model);
		control = new BabbleControl(model, view);
		}
	
	public static BabbleProgram getMainProgram() {
		return mainProgram;
		}

	public void endMainProgram() {
		view.setVisible(false);
		System.exit(0);
		}


		
		/*
		// TODO Auto-generated method stub
		
		Babble babble = new Babble();
		babble.startGUI();
		
		/* 1. GUI starten (Babble Button inaktiv)
		   2. Bei Auswahl von File dieses auf Gültigkeit prüfen (txt)
		   3. File einlesen und zeitgleich analysieren (don't forget EOF, BOF), Analyseergebnisse anzeigen
		   4. Babble Button aktiv
		   5. Bei Drücken des Babble Buttons den Babble Text generieren
		*/
		/*
		String text = "Well, when you're sitting there In your silk upholstered chair talking to some rich folks that you know Well I hope you won't see me In my ragged company You know I could never be alone.";
		//String text = "aha aha aha";
		babble.getPatterns(text, 3);
		System.out.println("Number of Patterns: " + patternList.size());
		System.out.println(babble.patternList.get("ell").nextPattern.toString());
	}
	*/
}

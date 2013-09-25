package ch.fhnw.edu.basic.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ch.fhnw.edu.basic.domain.FlashCard;
import ch.fhnw.edu.basic.domain.Questionnaire;

public class QuestionnaireInitializer {
	private enum Number {
		eins, zwei,drei, vier, fünf, sechs, sieben, acht, neun, zehn
	}
	
	private static Logger logger = Logger.getLogger(QuestionnaireInitializer.class);
	
	public static List<Questionnaire> createQuestionnaires() {
		List<Questionnaire> questionnaires = new ArrayList<Questionnaire>();
		
		questionnaires.add(getQuestionnaire("firstQ", "Test Fragebogen 1", "Just For Testing"));
		questionnaires.add(getQuestionnaire("secondQ", "Test Fragebogen 2", "Just For Testing"));
		
		logger.debug(questionnaires.size() + " Questionnaires initialized successfully");
		return questionnaires;
	}

	private static Questionnaire getQuestionnaire(String shortname, String title, String description) {
		Questionnaire questionnaire = new Questionnaire(shortname, title, description);
		for (int i=0; i<10; i++) {
			FlashCard entry = new FlashCard("Frage " + (i+1), "Antwort " + Number.values()[i]);
			questionnaire.addFlashCard(entry);
		}
		return questionnaire;
	}
}

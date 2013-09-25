package ch.fhnw.edu.basic.domain;

import java.util.ArrayList;
import java.util.List;

public class Questionnaire {
	private String subject;
	private String title;
	private String description;
	private List<FlashCard> flashcards;
	
	public Questionnaire(String subject, String title, String description) {
		this.subject = subject;
		this.title = title;
		this.description = description;
		this.flashcards = new ArrayList<FlashCard>();
	}
	
	public String getSubject() {
		return subject;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
	
	public void addFlashCard(FlashCard entry) {
		flashcards.add(entry);
	}
	
	public List<FlashCard> getFlashCards() {
		return flashcards;
	}
}

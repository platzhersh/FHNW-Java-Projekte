package ch.fhnw.edu.basic.domain;

public class FlashCard {
	private String question;
	private String answer;
	
	public FlashCard(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}
}

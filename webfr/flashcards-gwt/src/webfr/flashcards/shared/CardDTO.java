package webfr.flashcards.shared;

import java.io.Serializable;

public class CardDTO implements Serializable {
	
	private String question;
	private String answer;
	
	@SuppressWarnings("unused")
	private CardDTO() { }
	
	public CardDTO(String question, String answer) {
		if (question == null || answer == null) {
			throw new IllegalArgumentException();
		}
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

package ch.fhnw.edu.flashcard.web;

public class HelloResponse {
	private String from, to, language;
	
	public HelloResponse(String from, String to, String language) {
		this.from = from;
		this.to = to;
		this.language = language;
	}
	
	public String getFrom(){
		return this.from;
	};
	public String getTo(){
		return this.to;
	};
	public String getLanguage(){
		return this.language;
	};
}

package webfr.flashcards.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CardBoxDTO implements Serializable {
	private int id;
	private String title;
	private String description;
	private Date lastChange;
	private LinkedList<CardDTO> cards;
	
	public CardBoxDTO() {
		lastChange = new Date();
		cards = new LinkedList<CardDTO>();
	}
	
	public CardBoxDTO(int id, String title, String description) {
		this();
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		if (title == null) throw new IllegalArgumentException();
		if (!this.title.equals(title)) {
			this.title = title;
			adjustLastChange();
		}
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String desc) {
		if (desc == null) throw new IllegalArgumentException();
		if (!description.equals(desc)) {
			description = desc;
			adjustLastChange();
		}
	}

	public Date getLastChange() {
		return lastChange;
	}
	
	public List<CardDTO> getCards() {
		return cards;
	}
	
	private void adjustLastChange() {
		lastChange = new Date();
	}
	
	public int getId() {
		return id;
	}

}

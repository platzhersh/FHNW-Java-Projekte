package ch.fhnw.edu.flashcard.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="mySimpleBean")
@RequestScoped
public class SimpleBean {
	public String getMessage() {
		return "Hello from JSF";
	}

}

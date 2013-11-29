package webfr.simple.client.view;

import com.google.gwt.event.dom.client.ClickHandler;

public interface WelcomeDisplay extends Display {
	void setOneButtonHandler(ClickHandler handler);
	void setTwoButtonHandler(ClickHandler handler);
}

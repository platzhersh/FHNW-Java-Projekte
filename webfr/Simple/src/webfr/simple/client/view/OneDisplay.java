package webfr.simple.client.view;

import com.google.gwt.event.dom.client.ClickHandler;

public interface OneDisplay extends Display {

	
	void setCounterButtonHandler(ClickHandler handler);
	void setCounterButtonNumber(Integer n);

}

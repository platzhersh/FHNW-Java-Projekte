package webfr.simple.client.view;

import com.google.gwt.event.dom.client.ClickHandler;

public interface OneDisplay extends Display {
	void setIncNumberHandler(ClickHandler handler);
	void setBackHandler(ClickHandler handler);
	void setNumber(int num);
}

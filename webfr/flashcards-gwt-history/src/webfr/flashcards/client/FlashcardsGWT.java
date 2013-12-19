package webfr.flashcards.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FlashcardsGWT implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
    // Use RootPanel.get() to get the entire body element
    new AppController(RootPanel.get("loginFormContainer")).start();
	}
}

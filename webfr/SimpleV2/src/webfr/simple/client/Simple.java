package webfr.simple.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Simple implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel r = RootPanel.get("appContainer");
		AppController appViewer = new AppController(r);
		appViewer.present();
	}
}

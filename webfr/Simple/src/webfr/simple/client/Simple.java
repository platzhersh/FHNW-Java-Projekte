package webfr.simple.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Simple implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		AppController appViewer = new AppController();
		RootPanel r = RootPanel.get("appContainer");
		Element re = r.getElement();
		NodeList<Node> nl = re.getChildNodes();
		int numOfNodes = re.getChildCount();
		for (int i = 0; i < numOfNodes; i++) {
			Node n = nl.getItem(i);
			re.removeChild(n);
		}
		appViewer.present(r);
	}
}

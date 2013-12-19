package ch.fhnw.webfr.sayt.client;

import java.util.List;

import ch.fhnw.webfr.sayt.shared.Address;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SearchAsYouType implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side service.
	 */
	private final SaytServiceAsync saytService = GWT.create(SaytService.class);
	
	private SearchPanel sp;
	
	int requestCount = 0;
	
	Timer timer = new Timer() {
	      public void run() {
	        Window.alert ("Timer expired!");
	      }
	    };
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		sp = new SearchPanel(this);
		
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("gwtContent").add(sp);
	}
	
	public void cancelTimer() {
		timer.cancel();
	}

	public void sayt(String text) {
		cancelTimer();
		// Kick off an RPC
		saytService.findAddressMatchingAny(text, new SearchHandler());
		if ("M".equals(sp.textBox.getText())) {
		
			timer.schedule(5000);
		}
	}
	
	
	class SearchHandler implements AsyncCallback<List<Address>> {

		int requestNo;
		
		
		@Override
		public void onFailure(Throwable caught) {
			RootPanel err = RootPanel.get("errors");
			err.clear();
			err.add(new Label(caught.getMessage()));
		}

		@Override
		public void onSuccess(List<Address> result) {
			cancelTimer();
			sp.replaceAddresses(result);
		}
		
		private boolean isCurrent() {
			return requestCount <= requestNo;
		}
				
	}
	
}

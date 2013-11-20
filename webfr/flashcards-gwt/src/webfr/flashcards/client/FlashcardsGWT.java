package webfr.flashcards.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FlashcardsGWT implements EntryPoint {
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// This method will contain code to show 
		// the application in the browser
	  
	  
		final LoginForm welcome = new LoginForm("Change this text");
//
//    // Add the nameField and sendButton to the RootPanel
//    // Use RootPanel.get() to get the entire body element
		RootPanel.get("loginFormContainer").add(welcome);
//
//    // Create the popup dialog box
		final MyDialogBox dialogBox = new MyDialogBox();
//
//    // Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler {
//      /**
//       * Fired when the user clicks on the sendButton.
//       */
			public void onClick(ClickEvent event) {
			sendNameToServer();
			}
//
//      /**
//       * Send the name from the nameField to the server and wait for a response.
//       */
			private void sendNameToServer() {
			String textToServer = welcome.getText();
//
//        // Then, we send the input to the server.
			dialogBox.setTextToServer(textToServer);
			dialogBox.setServerResponse("", false);
			greetingService.greetServer(textToServer, new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
//            // Show the RPC error message to the user
					dialogBox.setText("Remote Procedure Call - Failure");
					dialogBox.setServerResponse(SERVER_ERROR, true);
					dialogBox.center();
				}
//
				public void onSuccess(String result) {
					dialogBox.setText("Remote Procedure Call");
					dialogBox.setServerResponse(result, false);
					dialogBox.center();
				}
			});
		}
	}
//
//    // Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
//    // TODO: register handler with your login form
		welcome.addClickHandler(handler);
	  
	}
}

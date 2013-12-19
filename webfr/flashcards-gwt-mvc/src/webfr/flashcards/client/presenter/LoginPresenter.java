package webfr.flashcards.client.presenter;

import webfr.flashcards.client.AppController;
import webfr.flashcards.client.FlashcardsService;
import webfr.flashcards.client.FlashcardsServiceAsync;
import webfr.flashcards.client.view.MyDialogBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class LoginPresenter implements Presenter {
  public interface LoginDisplay extends Display {
    String getUser();
    void resetUser();
    HandlerRegistration addClickHandler(ClickHandler ch);
  }
  
  public LoginPresenter(AppController ac, LoginDisplay display) {
    login = display;
  }
  
  private final LoginDisplay login;
 

  // Create the popup dialog box
  private final MyDialogBox dialogBox = new MyDialogBox();
  
  // Create a handler for the sendButton and nameField
  private class LoginHandler implements ClickHandler {
    /**
     * Fired when the user clicks on the sendButton.
     */
    public void onClick(ClickEvent event) {
      sendNameToServer();
    }

    /**
     * Send the name from the nameField to the server and wait for a response.
     */
    private void sendNameToServer() {
      String textToServer = login.getUser();

      // Then, we send the input to the server.
      dialogBox.setTextToServer(textToServer);
      dialogBox.setServerResponse("", false);
      flashcardsService.greetServer(textToServer, new AsyncCallback<String>() {
        public void onFailure(Throwable caught) {
          // Show the RPC error message to the user
          dialogBox.setText("Remote Procedure Call - Failure");
          dialogBox.setServerResponse(SERVER_ERROR, true);
          dialogBox.center();
        }

        public void onSuccess(String result) {
          dialogBox.setText("Remote Procedure Call");
          dialogBox.setServerResponse(result, false);
          dialogBox.center();
        }
      });
    }
  }

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
  private final FlashcardsServiceAsync flashcardsService = GWT.create(FlashcardsService.class);

  @Override
  public void bind() {
    // Add a handler to send the name to the server
    login.addClickHandler(new LoginHandler());
  }

  @Override
  public void present(HasWidgets container) {
    container.clear();
    container.add(login.asWidget());
  }


}

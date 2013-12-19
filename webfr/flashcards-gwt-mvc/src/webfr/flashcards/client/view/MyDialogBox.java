package webfr.flashcards.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MyDialogBox extends DialogBox {
  
  private Label textToServerLabel = new Label();
  private HTML serverResponseLabel = new HTML();
  
  public MyDialogBox() {
    setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
    
    
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
    dialogVPanel.add(textToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    setWidget(dialogVPanel);

    // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        hide();
      }
    });

  }
  
  public void setTextToServer(String text) {
    textToServerLabel.setText(text);
  }
  
  public void setServerResponse(String text, boolean error) {
    if (error) {
      serverResponseLabel.addStyleName("serverResponseLabelError");
    } else {
      serverResponseLabel.removeStyleName("serverResponseLabelError");
    }
    serverResponseLabel.setHTML(text);
  }
}

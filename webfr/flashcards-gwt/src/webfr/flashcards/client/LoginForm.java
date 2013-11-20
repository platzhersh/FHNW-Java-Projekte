package webfr.flashcards.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginForm extends Composite implements HasText {

	private static LoginFormUiBinder uiBinder = GWT
			.create(LoginFormUiBinder.class);

	interface LoginFormUiBinder extends UiBinder<HTMLPanel, LoginForm> {
	}

	public LoginForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField TextBox textbox;
	
	@UiField Button button;

	public LoginForm(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		textbox.setText(firstName);
	}

	public void addClickHandler(ClickHandler handler) {
		button.addClickHandler(handler);
	}

	public void setText(String text) {
		textbox.setText(text);
	}

	public String getText() {
		return textbox.getText();
	}

}

package webfr.flashcards.client.view;

import webfr.flashcards.client.presenter.LoginPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite implements LoginPresenter.LoginDisplay {
	private static LoginFormUiBinder uiBinder = GWT
			.create(LoginFormUiBinder.class);

	interface LoginFormUiBinder extends UiBinder<Widget, LoginView> {
	}
	
	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Button button;
	@UiField TextBox textBox;

	public LoginView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		textBox.setText(firstName);
	}

	public void setText(String text) {
	}

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return button.addClickHandler(handler);
  }

  @Override
  public String getUser() {
    return textBox.getText();
  }

  @Override
  public void resetUser() {
    textBox.setText("");
  }

}

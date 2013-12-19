package webfr.simple.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WelcomeView extends Composite implements WelcomeDisplay {

	private static WelcomeViewUiBinder uiBinder = GWT
			.create(WelcomeViewUiBinder.class);

	interface WelcomeViewUiBinder extends UiBinder<Widget, WelcomeView> {
	}

	public WelcomeView() {
		initWidget(uiBinder.createAndBindUi(this));
		one.setText("1");
	}

	@UiField
	Button one;
	
	@Override
	public void setOneButtonHandler(ClickHandler handler) {
		one.addClickHandler(handler);
	}


}

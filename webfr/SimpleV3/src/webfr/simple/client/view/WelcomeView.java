package webfr.simple.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WelcomeView extends Composite implements WelcomeDisplay {

	private static WelcomeViewUiBinder uiBinder = GWT
			.create(WelcomeViewUiBinder.class);

	private ClickHandler oneClick;
	private ClickHandler twoClick;
	
	interface WelcomeViewUiBinder extends UiBinder<Widget, WelcomeView> {
	}

	public WelcomeView() {
		initWidget(uiBinder.createAndBindUi(this));
		one.setText("1");
		two.setText("2");
	}

	@UiField
	Button one;
	@UiField
	Button two;

	@UiHandler("one")
	void onOneClick(ClickEvent e) {
		if (oneClick != null) {
			oneClick.onClick(e);
		}
	}

	@UiHandler("two")
	void onTwoClick(ClickEvent e) {
		if (twoClick != null) {
			twoClick.onClick(e);
		}
	}

	@Override
	public void setOneButtonHandler(ClickHandler handler) {
		if (handler != null) {
			oneClick = handler;
		}
		
	}

	@Override
	public void setTwoButtonHandler(ClickHandler handler) {
		if (handler != null) {
			twoClick = handler;
		}
	}

	@Override
	public void setTwoButtonText(int i) {
		two.setText(Integer.toString(i));
	}

}

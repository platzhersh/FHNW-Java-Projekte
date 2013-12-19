package webfr.simple.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class OneView extends Composite implements OneDisplay {

	private static OneViewUiBinder uiBinder = GWT.create(OneViewUiBinder.class);

	interface OneViewUiBinder extends UiBinder<Widget, OneView> {
	}

	@UiField Button incButton;
	@UiField Button backButton;

	public OneView() {
		initWidget(uiBinder.createAndBindUi(this));
		backButton.setText("Back to Simple Project entry page");
	}

	@Override
	public void setIncNumberHandler(ClickHandler handler) {
	  incButton.addClickHandler(handler);
	}

	@Override
	public void setBackHandler(ClickHandler handler) {
	  backButton.addClickHandler(handler);
	}

	@Override
	public void setNumber(int num) {
		incButton.setText(Integer.toString(num));
	}

}

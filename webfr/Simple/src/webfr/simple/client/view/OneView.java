package webfr.simple.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class OneView extends Composite implements OneDisplay {

	private static OneViewUiBinder uiBinder = GWT.create(OneViewUiBinder.class);

	interface OneViewUiBinder extends UiBinder<Widget, OneView> {
	}

	public OneView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button btnHome;
	
	@UiField
	Button btnCounter;

	public OneView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		btnHome.setText(firstName);
	}

	@UiHandler("btnHome")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

	public void setText(String text) {
		btnHome.setText(text);
	}

	public String getText() {
		return btnHome.getText();
	}

	@Override
	public void setCounterButtonHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCounterButtonNumber(Integer n) {
		this.btnCounter.setText(n.toString());
		
	}

}

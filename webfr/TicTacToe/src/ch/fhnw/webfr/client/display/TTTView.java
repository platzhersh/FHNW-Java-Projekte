package ch.fhnw.webfr.client.display;

import ch.fhnw.webfr.client.presenter.FieldClickHandler;
import ch.fhnw.webfr.client.presenter.TTTPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TTTView extends Composite implements TTTPresenter.TTTDisplay {
	
	private static TTTUiBinder uiBinder = GWT.create(TTTUiBinder.class);
	@UiField Button B1;
	@UiField Button B2;
	@UiField Button B3;
	@UiField Button B4;
	@UiField Button B5;
	@UiField Button B6;
	@UiField Button B7;
	@UiField Button B8;
	@UiField Button B9;
	@UiField Label label;
	
	private FieldClickHandler fieldClicked;
	
	interface TTTUiBinder extends UiBinder<Widget, TTTView> {}
	
	public TTTView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("B1")
	void onB1Click(ClickEvent event) {
		fieldClicked.fieldClicked(1);
	}
	@UiHandler("B2")
	void onB2Click(ClickEvent event) {
		fieldClicked.fieldClicked(2);
	}
	@UiHandler("B3")
	void onB3Click(ClickEvent event) {
		fieldClicked.fieldClicked(3);
	}
	@UiHandler("B4")
	void onB4Click(ClickEvent event) {
		fieldClicked.fieldClicked(4);
	}
	@UiHandler("B5")
	void onB5Click(ClickEvent event) {
		fieldClicked.fieldClicked(5);
	}
	@UiHandler("B6")
	void onB6Click(ClickEvent event) {
		fieldClicked.fieldClicked(6);
	}
	@UiHandler("B7")
	void onB7Click(ClickEvent event) {
		fieldClicked.fieldClicked(7);
	}
	@UiHandler("B8")
	void onB8Click(ClickEvent event) {
		fieldClicked.fieldClicked(8);
	}
	@UiHandler("B9")
	void onB9Click(ClickEvent event) {
		fieldClicked.fieldClicked(9);
	}
	
	@Override
	public void setField(int field, char mark) {
		String text = "" + mark;
		switch(field) {
		case 1: B1.setText(text); break;
		case 2: B2.setText(text); break;
		case 3: B3.setText(text); break;
		case 4: B4.setText(text); break;
		case 5: B5.setText(text); break;
		case 6: B6.setText(text); break;
		case 7: B7.setText(text); break;
		case 8: B8.setText(text); break;
		case 9: B9.setText(text); break;
		}
	}
	
	@Override
	public void setFieldClickHandler(FieldClickHandler handler) {
		fieldClicked = handler;
	}
	
	@Override
	public void setText(String text) {
		label.setText(text);
	}
	
	
}

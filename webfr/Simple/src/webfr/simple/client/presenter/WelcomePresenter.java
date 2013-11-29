package webfr.simple.client.presenter;

import webfr.simple.client.AppController;
import webfr.simple.client.view.OneView;
import webfr.simple.client.view.WelcomeDisplay;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

public class WelcomePresenter implements Presenter {

	protected WelcomeDisplay display;
	protected AppController controller;

	protected ClickHandler oneHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) { doOne(); }
	};
	protected ClickHandler twoHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) {	doTwo(); }
	};

	public WelcomePresenter(WelcomeDisplay d, AppController a) {
		this.display = d;
		this.controller = a;
		bind();
	}

	@Override
	public void present(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());		
	}

	@Override
	public void bind() {
		display.setOneButtonHandler(oneHandler);
		display.setTwoButtonHandler(twoHandler);
	}

	protected void doOne() {
		OnePresenter one = new OnePresenter(new OneView(), controller);
		controller.present((HasWidgets) one);
	}

	protected void doTwo() {
		Window.alert("Two clicked");
	}

}

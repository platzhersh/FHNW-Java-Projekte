package webfr.simple.client.presenter;

import webfr.simple.client.AppController;
import webfr.simple.client.event.IncEvent;
import webfr.simple.client.event.IncEventHandler;
import webfr.simple.client.view.WelcomeDisplay;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

public class WelcomePresenter implements Presenter {

	protected WelcomeDisplay display;
	private AppController app;

	protected ClickHandler oneHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) { doOne(); }
	};
	protected ClickHandler twoHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) {	doTwo(); }
	};

	public WelcomePresenter(WelcomeDisplay d, final AppController app) {
		this.display = d;
		this.app = app;
		app.getEventBus().addHandler(IncEvent.TYPE, new IncEventHandler() {

			@Override
			public void onIncCounter() {
				display.setTwoButtonText(app.getClicks());
			}
			
		});
		bind();
	}

	@Override
	public void present(HasWidgets container) {
		container.clear();
		display.setTwoButtonText(app.getClicks());
		container.add(display.asWidget());		
	}

	@Override
	public void bind() {
		display.setOneButtonHandler(oneHandler);
		display.setTwoButtonHandler(twoHandler);
	}

	protected void doOne() {
		History.newItem("one");
	}

	protected void doTwo() {
		Window.alert("Two clicked");
	}

}

package webfr.simple.client.presenter;

import webfr.simple.client.AppController;
import webfr.simple.client.event.IncEvent;
import webfr.simple.client.event.IncEventHandler;
import webfr.simple.client.view.OneDisplay;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class OnePresenter implements Presenter {
	
	protected OneDisplay display;
	private AppController app;

	protected ClickHandler incHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) { doInc(); }
	};
	protected ClickHandler backHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) {	doBack(); }
	};

	public OnePresenter(OneDisplay d, final AppController app) {
		this.display = d;
		this.app = app;
		display.setNumber(app.getClicks());
		this.app.getEventBus().addHandler(IncEvent.TYPE, new IncEventHandler() {

			@Override
			public void onIncCounter() {
				display.setNumber(app.getClicks());
			}
			
		});
		bind();
	}


	@Override
	public void bind() {
		display.setIncNumberHandler(incHandler);
		display.setBackHandler(backHandler);
		display.setNumber(0);
	}

	@Override
	public void present(HasWidgets container) {
		container.clear();
		display.setNumber(app.getClicks());
		container.add(display.asWidget());		
	}
	
	private void doInc() {
		app.incrementClicks();
		IncEvent event = new IncEvent();
		app.getEventBus().fireEvent(event);
	}
	
	private void doBack() {
		History.newItem("welcome");
	}

}

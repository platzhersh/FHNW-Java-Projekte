package webfr.simple.client;

import webfr.simple.client.presenter.OnePresenter;
import webfr.simple.client.presenter.Presenter;
import webfr.simple.client.presenter.WelcomePresenter;
import webfr.simple.client.view.OneView;
import webfr.simple.client.view.WelcomeView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class AppController implements ValueChangeHandler<String> {

	private Presenter welcome, one, current, presenter;
	private int clicks = 0;
	private EventBus eventbus = new SimpleEventBus();
	private HasWidgets container;

	public AppController(HasWidgets container) {
		eventbus = new SimpleEventBus();
		this.container = container;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);
		current = welcome;
	}

	public void present() {
		String token = History.getToken();
		
		if (token.isEmpty()) {
			History.newItem("welcome");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	public int getClicks() {
		return clicks;
	}
	
	public int incrementClicks() {
		return ++clicks;
	}
	
	public Presenter getCurrentPresenter() {
		return current;
	}
	
	public Presenter getWelcomePresenter() {
		return welcome;
	}
	
	public Presenter getOnePresenter() {
		return one;
	}
	
	public HasWidgets getContainer() {
		return container;
	}
	
	public EventBus getEventBus() {
		return eventbus;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		
		if (token != null) {
			presenter = null;

			if ("welcome".equals(token) || token.isEmpty()) {
				presenter = new WelcomePresenter(new WelcomeView(), this);
				if (presenter != null) {
					presenter.present(container);
				}
			} else if ("one".equals(token)) {
				
				GWT.runAsync(new RunAsyncCallback() {
					
					@Override
					public void onFailure(Throwable reason) {
						// warn about download failure
						System.out.println("blubb");
					}
					
					@Override
					public void onSuccess() {
						presenter = new OnePresenter(new OneView(), AppController.this);
						presenter.present(container);
					}
					
				});
				
			}
		}
	}
	
}

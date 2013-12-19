package webfr.simple.client;

import webfr.simple.client.presenter.OnePresenter;
import webfr.simple.client.presenter.Presenter;
import webfr.simple.client.presenter.WelcomePresenter;
import webfr.simple.client.view.OneView;
import webfr.simple.client.view.WelcomeView;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class AppController implements ValueChangeHandler<String> {

	private Presenter welcome, one, current;
	private int clicks = 0;
	private EventBus eventbus = new SimpleEventBus();
	private HasWidgets container;

	public AppController(HasWidgets container) {
		eventbus = new SimpleEventBus();
		this.container = container;
		History.addValueChangeHandler(this);
		bind();
	}

	private void bind() {
		welcome = new WelcomePresenter(new WelcomeView(), this);
		one = new OnePresenter(new OneView(), this);
		current = welcome;
	}

	public void present() {
		current.present(container);
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
		Presenter p = null;
		if (token == null || "".equals(token) || "start".equals(token) || "welcome".equals(token)) {
			p = new WelcomePresenter(new WelcomeView(), this);
		} else if ("one".equals(token)) {
			p = new OnePresenter(new OneView(), this);
		}
		
		if (p != null) p.present(container);
	}
	
	
}

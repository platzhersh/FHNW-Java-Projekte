package webfr.simple.client;

import webfr.simple.client.presenter.Presenter;
import webfr.simple.client.presenter.WelcomePresenter;
import webfr.simple.client.view.WelcomeView;

import com.google.gwt.user.client.ui.HasWidgets;

public class AppController {

	private Presenter presenter;
	private HasWidgets container;

	public AppController() {
		presenter = new WelcomePresenter(new WelcomeView(), this);
	}
	

	public void present(final HasWidgets c, final Presenter p) {
		this.container = c;
		this.presenter = p;
		presenter.present(container);
	}
	public void present(final HasWidgets c) {
		this.container = c;
		presenter.present(container);
	}

}

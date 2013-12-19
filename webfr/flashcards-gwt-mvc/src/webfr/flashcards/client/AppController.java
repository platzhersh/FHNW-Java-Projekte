package webfr.flashcards.client;

import webfr.flashcards.client.presenter.LoginPresenter;
import webfr.flashcards.client.presenter.WelcomePresenter;
import webfr.flashcards.client.view.LoginView;

import com.google.gwt.user.client.ui.RootPanel;

public class AppController {

	private final RootPanel root;
	private LoginPresenter login;
	private WelcomePresenter overview;
	
	private boolean isLoggedIn = false;

	public AppController(RootPanel root) {
	  this.root = root;
	  init();
	}
	
	public void start() {
	  present();
	}
	
	private void present() {
	  if (isLoggedIn) {
	    overview.present(root);
	  } else {
	    login.present(root);
	  }
	}
	
	private void init() {
	  login = new LoginPresenter(this, new LoginView());
	  login.bind();
	}

}

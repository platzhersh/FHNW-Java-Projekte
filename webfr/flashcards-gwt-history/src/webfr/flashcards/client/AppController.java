package webfr.flashcards.client;

import webfr.flashcards.client.presenter.BookmarkPresenter;
import webfr.flashcards.client.presenter.CardPresenter;
import webfr.flashcards.client.presenter.LoginPresenter;
import webfr.flashcards.client.presenter.WelcomePresenter;
import webfr.flashcards.client.view.BookmarkView;
import webfr.flashcards.client.view.CardView;
import webfr.flashcards.client.view.LoginView;
import webfr.flashcards.client.view.WelcomeView;
import webfr.flashcards.shared.Bookmark;

import com.google.gwt.user.client.ui.RootPanel;

public class AppController {

	private final RootPanel container;
	private LoginPresenter login;
	private WelcomePresenter overview;
	private BookmarkPresenter bookmark;
	private CardPresenter card;
	
	private boolean isLoggedIn = false;

	public AppController(RootPanel root) {
	  this.container = root;
	  init();
	}
	
	public void start() {
	  show("Overview", null);
	}
	
	public void show(String view, Bookmark bm) {
    if (!isLoggedIn) {
      login.present(container);
      isLoggedIn = true; // simply log user in no matter who she/he is
    } else if ("Overview".equals(view)) {
      overview.present(container);
    } else if ("Bookmark".equals(view)) {
      bookmark.setBookmark(bm);
      bookmark.present(container);
    } else if ("Card".equals(view)) {
      card.setBookmark(bm);
      card.present(container);
    }
	}
	
	private void init() {
	  login = new LoginPresenter(this, new LoginView());
	  login.bind();
	  overview = new WelcomePresenter(this, new WelcomeView());
	  overview.bind();
	  bookmark = new BookmarkPresenter(this, new BookmarkView());
	  bookmark.bind();
	  card = new CardPresenter(new CardView());
	  card.bind();
	}

}

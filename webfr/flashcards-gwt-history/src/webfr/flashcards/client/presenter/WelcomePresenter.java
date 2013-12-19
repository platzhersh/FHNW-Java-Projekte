package webfr.flashcards.client.presenter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import webfr.flashcards.client.AppController;
import webfr.flashcards.client.view.DataProviderUtility;
import webfr.flashcards.shared.Bookmark;
import webfr.flashcards.shared.Cardbox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;


public class WelcomePresenter implements Presenter {
	public interface WelcomeDisplay extends Display {
		void setCardBoxes(List<Cardbox> boxes);
		Cardbox getSelectedCardBoxes();
		void setLearnButtonHandler(ClickHandler handler);
		void setChangeButtonHandler(ClickHandler handler);
		void setNewButtonHandler(ClickHandler handler);
	}
	
	private List<Cardbox>boxes = new LinkedList<Cardbox>();
	protected WelcomeDisplay display;
	private AppController controller;
  
  protected ClickHandler changeButtonHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) { doChangeCardBox(event); }
	};
  protected ClickHandler learnButtonHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) {	doLearnCardBox(event); }
	};
  protected ClickHandler newButtonHandler = new ClickHandler() {
		@Override public void onClick(ClickEvent event) {	doNewCardBox(event);	}
	};
	protected AsyncCallback<List<Cardbox>> rpcCallback = new AsyncCallback<List<Cardbox>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
		}

		@Override
		public void onSuccess(List<Cardbox> result) {
			boxes = result;
			display.setCardBoxes(boxes);
		}
	};
	
	public WelcomePresenter(AppController ac, WelcomeDisplay d) {
		this.display = d;
		this.controller = ac;
		bind();
	}

	@Override
	public void present(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		boxes = DataProviderUtility.provideCardboxes();
		display.setCardBoxes(boxes);
	}
	
	@Override
	public void bind() {
		display.setChangeButtonHandler(changeButtonHandler);
		display.setLearnButtonHandler(learnButtonHandler);
		display.setNewButtonHandler(newButtonHandler);
	}

	protected void doNewCardBox(ClickEvent e) {
		Window.alert(e.getSource().toString());
	}

	protected void doLearnCardBox(ClickEvent e) {
	  Cardbox selection = display.getSelectedCardBoxes();
	  Bookmark bm = new Bookmark(selection, null, 0, new Date());
		controller.show("Bookmark", bm);
	}

	protected void doChangeCardBox(ClickEvent e) {
		Window.alert(e.getSource().toString());
	}
}

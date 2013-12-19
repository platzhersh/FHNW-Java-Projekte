package webfr.flashcards.client.presenter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import webfr.flashcards.client.view.DataProviderUtility;
import webfr.flashcards.shared.Cardbox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;


public class WelcomePresenter implements Presenter {
	public interface WelcomeDisplay extends Display {
		void setCardBoxes(List<Cardbox> boxes);
		Set<Cardbox> getSelectedCardBoxes();
		void setLearnButtonHandler(ClickHandler handler);
		void setChangeButtonHandler(ClickHandler handler);
		void setNewButtonHandler(ClickHandler handler);
	}
	
	private List<Cardbox>boxes = new LinkedList<Cardbox>();
	protected WelcomeDisplay display;
  
  protected ClickHandler changeButtonHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) { doChangeCardBox(); }
	};
  protected ClickHandler learnButtonHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) {	doLearnCardBox(); }
	};
  protected ClickHandler newButtonHandler = new ClickHandler() {
		@Override public void onClick(ClickEvent event) {	doNewCardBox();	}
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
	
	public WelcomePresenter(WelcomeDisplay d) {
		this.display = d;
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

	protected void doNewCardBox() {
		Window.alert("New clicked");
	}

	protected void doLearnCardBox() {
		Window.alert("Learn clicked");
	}

	protected void doChangeCardBox() {
		Window.alert("Change clicked");
	}
}

package webfr.flashcards.client.presenter;

import webfr.flashcards.shared.Bookmark;
import webfr.flashcards.shared.Card;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasWidgets;

public class CardPresenter implements Presenter {
  public interface CardDisplay extends Display {
    void setQuestion(String question);
    void setAnswer(String answer);
    void setAnswerVisible(boolean visible);
    void setShowAnswerHandler(ClickHandler h);
    void setCorrectHandler(ClickHandler h);
    void setWrongHandler(ClickHandler h);
  }
  
  private CardDisplay display;
  private Bookmark bookmark;
  
  public CardPresenter(CardDisplay d) {
    display = d;
  }

  public void setBookmark(Bookmark bm) {
    bookmark = bm;
  }
  
  @Override
  public void bind() {
    display.setShowAnswerHandler(showAnswerHandler);
    display.setCorrectHandler(correctHandler);
    display.setWrongHandler(wrongHandler);
  }

  @Override
  public void present(HasWidgets container) {
    showNextCard();
    container.clear();
    container.add(display.asWidget());
  }
  
  private ClickHandler showAnswerHandler = new ClickHandler() {

    @Override
    public void onClick(ClickEvent event) {
      display.setAnswerVisible(true);
    }
    
  };
  
  private ClickHandler correctHandler = new ClickHandler() {

    @Override
    public void onClick(ClickEvent event) {
      bookmark.correctAnswer();
      showNextCard();
    }
    
  };
  
  private ClickHandler wrongHandler = new ClickHandler() {

    @Override
    public void onClick(ClickEvent event) {
      bookmark.wrongAnswer();
      showNextCard();
    }
    
  };
  
  private void showNextCard() {
    Card c = bookmark.getCurrentCard();
    display.setAnswerVisible(false);
    display.setQuestion(c.getQuestion());
    display.setAnswer(c.getAnswer());
  }

}

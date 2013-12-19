package webfr.flashcards.client.view;

import webfr.flashcards.client.presenter.CardPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;

public class CardView extends Composite implements CardPresenter.CardDisplay {

  private static CardViewUiBinder uiBinder = GWT.create(CardViewUiBinder.class);
  @UiField Label questionText;
  @UiField Label answerLabel;
  @UiField Label answerText;
  @UiField Button correctButton;
  @UiField Button showAnswerButton;
  @UiField Button wrongButton;

  interface CardViewUiBinder extends UiBinder<Widget, CardView> {
  }

  public CardView() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public void setQuestion(String question) {
    questionText.setText(question);
  }

  @Override
  public void setAnswer(String answer) {
    answerText.setText(answer);
  }

  @Override
  public void setAnswerVisible(boolean visible) {
    answerText.setVisible(visible);
    answerLabel.setVisible(visible);
    showAnswerButton.setVisible(!visible);
    correctButton.setVisible(visible);
    wrongButton.setVisible(visible);
  }

  @Override
  public void setShowAnswerHandler(ClickHandler h) {
    showAnswerButton.addClickHandler(h);
  }

  @Override
  public void setCorrectHandler(ClickHandler h) {
    correctButton.addClickHandler(h);
  }

  @Override
  public void setWrongHandler(ClickHandler h) {
    wrongButton.addClickHandler(h);
  }

}

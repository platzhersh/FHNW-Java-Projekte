package webfr.flashcards.client.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import webfr.flashcards.client.presenter.BookmarkPresenter;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.Button;

public class BookmarkView extends Composite implements BookmarkPresenter.BookmarkDisplay {

  private static BookmarkViewUiBinder uiBinder = GWT.create(BookmarkViewUiBinder.class);
  @UiField Label title;
  @UiField Label description;
  @UiField DateLabel lastupdate;
  @UiField NumberLabel<Integer> nofcards;
  @UiField NumberLabel<Integer> progress;
  @UiField Button reset;
  @UiField Button learn;

  interface BookmarkViewUiBinder extends UiBinder<Widget, BookmarkView> {
  }

  public BookmarkView() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public void setTitle(String title) {
    this.title.setText(title);
  }
  @Override
  public void setDescription(String desc) {
    description.setText(desc);
  }

  @Override
  public void setLastLearned(Date date) {
    lastupdate.setValue(date);
  }

  @Override
  public void setProgess(int progress) {
    this.progress.setValue(progress);
  }

  @Override
  public void setNofCards(int no) {
    nofcards.setValue(no);
  }

  @Override
  public void setResetHandler(ClickHandler handler) {
    reset.addClickHandler(handler);
  }

  @Override
  public void setLearnHandler(ClickHandler handler) {
    learn.addClickHandler(handler);
  }

}

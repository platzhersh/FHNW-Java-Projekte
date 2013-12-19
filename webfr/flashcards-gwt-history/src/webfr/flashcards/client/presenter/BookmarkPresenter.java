package webfr.flashcards.client.presenter;

import java.util.Date;

import webfr.flashcards.client.AppController;
import webfr.flashcards.shared.Bookmark;
import webfr.flashcards.shared.Cardbox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasWidgets;

public class BookmarkPresenter implements Presenter {
  
  public interface BookmarkDisplay extends Display {
    void setTitle(String title);
    void setDescription(String desc);
    void setLastLearned(Date date);
    void setProgess(int progress);
    void setNofCards(int no);
    void setResetHandler(ClickHandler handler);
    void setLearnHandler(ClickHandler handler);
  }

  private BookmarkDisplay display;
  private Bookmark bookmark;
  private AppController controller;
  
  public BookmarkPresenter(AppController ac, BookmarkDisplay bd) {
    controller = ac;
    display = bd;
  }
  
  public void setBookmark(Bookmark bm) {
    bookmark = bm;
  }
  
  @Override
  public void bind() {
    display.setResetHandler(resetClickHandler);
    display.setLearnHandler(learnClickHandler);
  }

  @Override
  public void present(HasWidgets container) {
    container.clear();
    container.add(display.asWidget());
    Cardbox box = bookmark.getBox();
    display.setDescription(box.getDescription());
    display.setTitle(box.getTitle());
    display.setLastLearned(bookmark.getLastLearned());
    display.setNofCards(box.getCards().size());
    display.setProgess(bookmark.getProgress());
  }

  private ClickHandler resetClickHandler = new ClickHandler() {

    @Override
    public void onClick(ClickEvent event) {
      bookmark.reset();
      display.setProgess(bookmark.getProgress());
      display.setLastLearned(bookmark.getLastLearned());
    }
    
  };
  
  private ClickHandler learnClickHandler = new ClickHandler() {

    @Override
    public void onClick(ClickEvent event) {
      controller.show("Card", bookmark);
    }
    
  };
}


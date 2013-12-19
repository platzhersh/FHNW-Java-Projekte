package webfr.flashcards.client.view;

import java.util.List;
import java.util.Set;

import webfr.flashcards.client.presenter.WelcomePresenter;
import webfr.flashcards.shared.Cardbox;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;


public class WelcomeView extends Composite implements WelcomePresenter.WelcomeDisplay {

	private static WelcomeViewUiBinder uiBinder = GWT.create(WelcomeViewUiBinder.class);
	
	@UiField Button changeButton;
	@UiField Button learnButton;
	@UiField Button newButton;
	
	static class CardBoxOverviewCell extends AbstractCell<Cardbox> {
		public CardBoxOverviewCell() {
	    super("click"); // cell will consume click events
		}

		@Override
		public void render(Context context, Cardbox cardbox, SafeHtmlBuilder sb) {
			if (cardbox == null) {
		    return;
		  }
	    // each cell has a line at the bottom to separate itself from the
	    // next cell. As the cell will display two items (title and desc), it
	    // will consist of table with one row and two columns
	    sb.appendHtmlConstant("<table frame=\"below\" width=\"100%\">");

	    sb.appendHtmlConstant("<tr>");															// the only row
	    sb.appendHtmlConstant("<td width=\"25%\" valign=\"top\">"); // the first column takes 25% of the width of the cell
	    sb.appendHtmlConstant("<p class=\"title\">");								// it will format according to the .title style
	    sb.appendEscaped(cardbox.getTitle());												// it shows the title of the cardbox
	    sb.appendHtmlConstant("</p></td>");
	    
	    sb.appendHtmlConstant("<td valign=\"top\">");								// the second column takes up the rest of the width
	    sb.appendHtmlConstant("<p class=\"description\">");					// it will format according to the .description style
	    sb.appendEscaped(cardbox.getDescription());									// it shows the description of the cardbox
	    sb.appendHtmlConstant("</p></td></tr>");										// close tags
	    sb.appendHtmlConstant("</table>");
		}
	}
	
	private CardBoxOverviewCell cell = new CardBoxOverviewCell();
	
	@UiField(provided=true) CellList<Cardbox> cellList = new CellList<Cardbox>(cell);
	
	private ClickHandler newButtonClick;
	private ClickHandler changeButtonClick;
	private ClickHandler learnButtonClick;

	interface WelcomeViewUiBinder extends UiBinder<Widget, WelcomeView> {}

	public WelcomeView() {
		initWidget(uiBinder.createAndBindUi(this));
		cellList.setEmptyListWidget(new Label("keine Karteien vorhanden..."));
		cellList.setLoadingIndicator(new Label("Daten werden geladen..."));
		cellList.setSelectionModel(new MultiSelectionModel<Cardbox>());
	}

	@Override
	public void setCardBoxes(List<Cardbox> boxes) {
		cellList.setRowData(boxes);
	}

	@Override
	public Set<Cardbox> getSelectedCardBoxes() {
		@SuppressWarnings("unchecked")
		MultiSelectionModel<Cardbox> sm = (MultiSelectionModel<Cardbox>)cellList.getSelectionModel();
		return sm.getSelectedSet();
	}

	@Override
	public void setLearnButtonHandler(ClickHandler handler) {
		learnButtonClick = handler;
	}

	@Override
	public void setChangeButtonHandler(ClickHandler handler) {
		changeButtonClick = handler;
	}

	@Override
	public void setNewButtonHandler(ClickHandler handler) {
		newButtonClick = handler;
	}

	@UiHandler("changeButton")
	void onChangeButtonClick(ClickEvent event) {
		if (changeButtonClick != null) {
			changeButtonClick.onClick(event);
		}
	}
	
	@UiHandler("learnButton")
	void onLearnButtonClick(ClickEvent event) {
		if (learnButtonClick != null) {
			learnButtonClick.onClick(event);
		}
	}
	
	@UiHandler("newButton")
	void onNewButtonClick(ClickEvent event) {
		if (newButtonClick != null) {
			newButtonClick.onClick(event);
		}
	}

}

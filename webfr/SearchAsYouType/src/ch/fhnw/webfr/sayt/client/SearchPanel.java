package ch.fhnw.webfr.sayt.client;

import java.util.LinkedList;
import java.util.List;

import ch.fhnw.webfr.sayt.shared.Address;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SearchPanel extends Composite {

	private SearchAsYouType presenter;
	
	private static SearchPanelUiBinder uiBinder = GWT.create(SearchPanelUiBinder.class);
	@UiField TextBox textBox;
	@UiField(provided=true) CellList<Address> addressList = new CellList<Address>(new AbstractCell<Address>(){
		@Override
		public void render(Context context, Address address, SafeHtmlBuilder sb) {
			sb.appendEscaped(address.getFirstname() + " " + address.getLastname()
					+ ", " + address.getStreet() + " " + address.getStreetnr() + ", "
					+ address.getPlz() + " " + address.getCity());
		}
	});

	interface SearchPanelUiBinder extends UiBinder<Widget, SearchPanel> {
	}

	public SearchPanel(SearchAsYouType presenter) {
		this.presenter = presenter;
		initWidget(uiBinder.createAndBindUi(this));
		addressList.setEmptyListWidget(new Label("keine Treffer"));
		addressList.setLoadingIndicator(new Label("Suche..."));
		replaceAddresses(new LinkedList<Address>());
	}

	public void replaceAddresses(List<Address>addresses) {
		addressList.setRowData(addresses);
	}
	
	@UiHandler("textBox")
	void onTextBoxKeyUp(KeyUpEvent event) {
		presenter.sayt(textBox.getText());
	}
}

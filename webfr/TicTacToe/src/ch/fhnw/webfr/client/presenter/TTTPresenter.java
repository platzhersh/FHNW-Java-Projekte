package ch.fhnw.webfr.client.presenter;

import java.util.LinkedList;

import ch.fhnw.webfr.client.TTTModel;
import ch.fhnw.webfr.client.display.TTTView;
import ch.fhnw.webfr.client.presenter.TTTPresenter.TTTDisplay;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

public class TTTPresenter {

	public interface TTTDisplay extends IsWidget {
		void setField(int field, char mark);
		void setFieldClickHandler(FieldClickHandler handler);
		void setText(String text);
	}
	
	TTTModel m;
	TTTDisplay d;
	private P_ClickHandler fieldClickHandler = new P_ClickHandler();
	private P_RefreshHandler refreshHandler = new P_RefreshHandler();
	private P_BackHandler backHandler = new P_BackHandler();
	private int plays;
	private char[] aiPlays = new char[9];
	private LinkedList<ClickCommand> cmd = new LinkedList<ClickCommand>();
	
	public TTTPresenter(TTTModel model, TTTView view) {
		this.m = model;
		this.d = view;
	}

	public void bind() {
		d.setFieldClickHandler(fieldClickHandler);
		Window.addWindowClosingHandler(refreshHandler);
		History.addValueChangeHandler(backHandler);
	}
	public void present(HasWidgets container) {
		container.clear();
		container.add(d.asWidget());
	}
	
	

	private class P_RefreshHandler implements ClosingHandler {

		@Override
		public void onWindowClosing(ClosingEvent event) {
			m = new TTTModel();
		}
		
	}
	
	private class P_ClickHandler implements FieldClickHandler {
		
		@Override
		public void fieldClicked(int field) {
			History.newItem(String.valueOf(++plays));
			char c = m.getField(field);
			if (c == 'x' || c == 'o') {
				d.setText("All your base are belong to nobody!");
			} else {
				m.setField(field);
				d.setField(field, 'x');
				cmd.add(new ClickCommand(field, 'x'));
				boolean found = false;
				while (!found) {
					int f = (int)(1 + Math.random() * 9);
					char ui = m.getField(f);
					char ai = aiPlays[f];
					if (ui != 'x' && ai != 'o') {
						d.setField(f, 'o');
						aiPlays[f] = 'o';
						cmd.add(new ClickCommand(field, 'o'));
						found = true;
					}
				}
			}
		}
	};
	
	private class P_BackHandler implements ValueChangeHandler<String> {

		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			String token = event.getValue();
			int play = Integer.parseInt(token);
			for (int i = cmd.size(); i == play; i--) {
				cmd.removeLast();
			}
			m = new TTTModel();
			for (ClickCommand c : cmd) {
				d.setField(c.field, c.c);
				if (c.c == 'x') {
					m.setField(c.field);
				}
			}
		}
	}
	
	private class ClickCommand {
		public int field;
		public char c;
		public ClickCommand(int field, char c) {
			this.field = field;
			this.c = c;
		}
	}
	
}

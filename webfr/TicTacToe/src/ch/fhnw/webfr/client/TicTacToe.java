package ch.fhnw.webfr.client;

import ch.fhnw.webfr.client.display.TTTView;
import ch.fhnw.webfr.client.presenter.TTTPresenter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class TicTacToe implements EntryPoint {
	
	@Override
	public void onModuleLoad() {

		RootPanel r = RootPanel.get("gameContainer");
		TTTPresenter p = new TTTPresenter(new TTTModel(),new TTTView());
		p.bind();		
		p.present(r);
	}	
	

}

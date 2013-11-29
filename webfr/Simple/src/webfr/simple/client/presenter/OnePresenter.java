package webfr.simple.client.presenter;

import webfr.simple.client.AppController;
import webfr.simple.client.view.OneDisplay;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasWidgets;

public class OnePresenter implements Presenter {
	
	protected OneDisplay display;
	protected AppController controller;
	protected int counter;
	
	public OnePresenter(OneDisplay d, AppController a) {
		this.display = d;
		this.counter = 0;
		this.controller = a;
		bind();
	}
	
	protected ClickHandler counterHandler = new ClickHandler() {
		@Override	public void onClick(ClickEvent event) { incrementCounter(); }
	};

	protected void incrementCounter() {
		
	}
	
	@Override
	public void bind() {
		// TODO
	}

	@Override
	public void present(HasWidgets container) {
		// TODO Auto-generated method stub
		
	}

}

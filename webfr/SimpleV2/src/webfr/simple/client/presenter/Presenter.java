package webfr.simple.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

public interface Presenter {
	void bind();
	void present(HasWidgets container);
}

package webfr.simple.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class IncEvent extends GwtEvent<IncEventHandler> {
	public static Type<IncEventHandler> TYPE = new Type<IncEventHandler>();

	@Override
	public Type<IncEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(IncEventHandler handler) {
		handler.onIncCounter();
	}

}

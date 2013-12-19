package ch.fhnw.webfr.sayt.client;

import java.util.List;

import ch.fhnw.webfr.sayt.shared.Address;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SaytServiceAsync {

	void findAddressMatchingAny(String text, AsyncCallback<List<Address>> callback);

}

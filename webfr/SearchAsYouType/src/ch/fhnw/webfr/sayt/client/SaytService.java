package ch.fhnw.webfr.sayt.client;

import java.util.List;

import ch.fhnw.webfr.sayt.shared.Address;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("sayt")
public interface SaytService extends RemoteService {
	List<Address> findAddressMatchingAny(String text);
}

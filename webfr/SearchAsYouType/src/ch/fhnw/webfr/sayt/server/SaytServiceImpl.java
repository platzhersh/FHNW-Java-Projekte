package ch.fhnw.webfr.sayt.server;

import java.util.List;

import ch.fhnw.webfr.sayt.client.SaytService;
import ch.fhnw.webfr.sayt.shared.Address;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SaytServiceImpl extends RemoteServiceServlet implements SaytService {

	private static final long serialVersionUID = -8221381813850397653L;

	@Override
	public List<Address> findAddressMatchingAny(String text) {
		try {
			return AddressProvider.getInstance().lookupPartialName(text);
		} catch (InterruptedException e) {
			throw new RuntimeException("Ooops, this was an InterruptedException");
		}
	}

}

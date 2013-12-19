package ch.fhnw.webfr.sayt.server;

import java.util.LinkedList;
import java.util.List;

import ch.fhnw.webfr.sayt.shared.Address;

/**
 * Service that to lookup an address from an address list.
 * @author Christoph Denzler
 *
 */
public class AddressProvider {
	
	/**
	 * The singleton instance of the address service
	 */
	private static AddressProvider instance;

	/**
	 * @return Get the singleton instance of the address service.
	 */
	public static AddressProvider getInstance() {
		if (instance == null) {
			instance = new AddressProvider();
		}
		return instance;
	}
	
	/**
	 * List of addresses
	 */
	private List<Address> addresses = new LinkedList<Address>();
	
	/**
	 * Hide default constructor
	 */
	private AddressProvider() {
		initAddresses();
	}
	
	/**
	 * Initialize the list of addresses with some default values.
	 * All addresses are invented.
	 */
	private void initAddresses() {
		addresses.add(new Address("Moritz", "Leuenberger", "Impleniaplatz", 1, 3000, "Bern"));
		addresses.add(new Address("Dominque", "Gauthier", "Le Chat-Botté", 12, 1002, "Genf"));
		addresses.add(new Address("Vreni", "Schmidli", "zwischen den Wegen", 15, 9523, "Neu St. Johann"));
		addresses.add(new Address("Moritz", "Twenty", "Bahnhofstrasse", 1, 8001, "Zürich"));
		addresses.add(new Address("Stefan", "Leuenberger", "Dählhölzli", 153, 3005, "Bern"));
		addresses.add(new Address("Andrea", "Leuenberger", "Obersee", 121, 6300, "Luzern"));
	}

	/**
	 * Get a list of addresses which match the given text in either first or last names.
	 * @param text a junk of text to match in first and last names of addresses.
	 * @return the found addresses or empty array if nothing has been found.
	 * @throws InterruptedException 
	 */
	public List<Address> lookupPartialName(String text) throws InterruptedException {
		int waittime = 10;
		System.out.println("partial match on: " + text);
		List<Address> ret = new LinkedList<Address>();
		
		// not a very intelligent implementation, does not scale.
		if (text != null && !"".equals(text)) {
			if ("M".equals(text)) {
				waittime = 60000;	// wait for one minute
			} else if (text.length() == 1) {
				waittime = 4000;  // wait for four seconds
			}
			System.out.println(" waiting for " + waittime + "ms.");
			Thread.sleep(waittime);

			text = text.toLowerCase();
			for (Address address : addresses) {
				String first = address.getFirstname().toLowerCase();
				String last = address.getLastname().toLowerCase();
				
				if (first.contains(text) || last.contains(text)) {
					ret.add(address);
				}
			}
		} else {
			for(int i = 0; i < 10 && i < addresses.size(); i++) {
				ret.add(addresses.get(i));
			}
		}
		
		return ret;
	}
}

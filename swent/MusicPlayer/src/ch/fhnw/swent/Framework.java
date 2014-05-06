package ch.fhnw.swent;

import java.util.Iterator;
import java.util.Set;

public class Framework {

	public interface Filter {
		public boolean ok(Item item);
	}
	
	public boolean allOk(Set<Filter> filters, Item item) {
		Iterator<Filter> it = filters.iterator();
		boolean ok = true;
		while (it.hasNext() && ok) { ok = it.next().ok(item); }
		return ok;
	}
	
}

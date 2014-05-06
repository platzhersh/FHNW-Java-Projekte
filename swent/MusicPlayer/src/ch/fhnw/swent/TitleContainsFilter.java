package ch.fhnw.swent;

import ch.fhnw.swent.Framework.Filter;

public class TitleContainsFilter implements Filter {

	String str;
	
	public TitleContainsFilter(String s) {
		this.str = s;
	}
	
	@Override
	public boolean ok(Item item) {
		return item.getTitle().contains(str);
	}

}

package edu.spring.domain.renderer;

import edu.spring.domain.provider.MessageProvider;

public class StandardErrorXMLRenderer implements MessageRenderer {

	MessageProvider mp;
	
	public void render() {
		StringBuilder sb = new StringBuilder();
		sb.append("<message>");
		sb.append(mp.getMessage());
		sb.append("</message>");
		System.err.println(sb);
		
	}

	public void setMessageProvider(MessageProvider provider) {
		this.mp = provider;
	}

}

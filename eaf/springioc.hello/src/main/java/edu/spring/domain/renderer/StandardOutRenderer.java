package edu.spring.domain.renderer;

import edu.spring.domain.provider.MessageProvider;

public class StandardOutRenderer implements MessageRenderer {

	MessageProvider mp;
	
	public void render() {
		System.out.println(mp.getMessage());		
	}

	public void setMessageProvider(MessageProvider provider) {
		this.mp = provider;
	}

}

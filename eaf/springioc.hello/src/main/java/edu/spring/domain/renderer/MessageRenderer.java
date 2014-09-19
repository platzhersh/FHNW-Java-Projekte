package edu.spring.domain.renderer;

import edu.spring.domain.provider.MessageProvider;

public interface MessageRenderer {
	public void render();
	public void setMessageProvider(MessageProvider provider);	
}

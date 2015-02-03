package ch.fhnw.edu.rental.services.impl.util;

import java.util.Formatter;
import java.util.Map;

public interface MailTemplateHelper {
	/**
	 * Create the dynamic content based on the given template and the model.
	 * <p/>
	 * The template itself can be a string according to the format string defined in {@link Formatter}
	 * or a simple template name used by template framework like velocity or freemaker.
	 * 
	 * @param model to merge into the template
	 * @param template the template 
	 * 
	 * @return the merged content
	 */
	String mergeTemplate(Map<String, Object> model, String template);
}

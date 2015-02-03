package ch.fhnw.edu.rental.services.impl.util;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;


@Component(value = "velocityMailTemplateHelper")
public class MailTemplateHelperVelocity implements MailTemplateHelper {
	
	@Value("${mail.pathVelocityTemplates}")
	private String pathVelocityTemplates;
	
	//@Value("${mail.reminder.template}")
	//private String template; 
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	
	
	@Override	
	public String mergeTemplate(Map<String, Object> model, String template) {
		String templateLocation = pathVelocityTemplates + template;
		String encoding = "latin1";
		try {
			velocityEngine.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, encoding, model);	
	}
	
	
}

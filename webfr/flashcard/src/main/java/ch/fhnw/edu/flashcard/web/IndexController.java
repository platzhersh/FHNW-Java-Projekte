package ch.fhnw.edu.flashcard.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.fhnw.edu.flashcard.domain.Questionnaire;

@Controller
@RequestMapping("/")
public class IndexController {
	
	Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) { 
		uiModel.addAttribute("questionnaires", Questionnaire.findAllQuestionnaires()); 
		log.info("index called");
		return "index";
	}
}

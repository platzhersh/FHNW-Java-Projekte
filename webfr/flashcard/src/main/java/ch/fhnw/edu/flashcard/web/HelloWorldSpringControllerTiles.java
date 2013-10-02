package ch.fhnw.edu.flashcard.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tiles")
public class HelloWorldSpringControllerTiles {
	@RequestMapping(value="/say/{to}/{from}")
	public String sayHello(@PathVariable("to") String to, @PathVariable("from") String from, 
				@RequestParam(value="lang", required=false) String lang,
				Model uiModel
				) {
		
			String message;
			if (lang != null && lang.equals("de")) {
				message = "Hallo an " + to + " von " + from;
			}
			else {
				message = "Hello to " + to + " from " + from;
			}
			
			uiModel.addAttribute("message", message);
			return "hello";
	}
}

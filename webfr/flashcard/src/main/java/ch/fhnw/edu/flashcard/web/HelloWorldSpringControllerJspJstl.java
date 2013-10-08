package ch.fhnw.edu.flashcard.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/jspjstl")
public class HelloWorldSpringControllerJspJstl {
	
	@RequestMapping(value="/say/{to}/{from}")
	public String sayHello(@PathVariable("to") String to, @PathVariable("from") String from, 
				@RequestParam(value="lang", required=false) String lang,
				Model uiModel
				) {
			HelloResponse hello = new HelloResponse(from, to, lang);
			
			uiModel.addAttribute("answer", hello);
			
			return "jspjstl";
	}
}

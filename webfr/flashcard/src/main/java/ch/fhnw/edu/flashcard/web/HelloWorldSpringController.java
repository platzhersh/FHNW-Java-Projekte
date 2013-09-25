package ch.fhnw.edu.flashcard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/hello")
public class HelloWorldSpringController {
	@ResponseBody
	@RequestMapping(value="/say/{to}/{from}")
	public String sayHello(@PathVariable("to") String to, @PathVariable("from") String from, @RequestParam(value="lang", required=false) String lang) {
			if (lang != null && lang.equals("de")) {
				return "Hallo an " + to + " von " + from;
			}
			else {
				return "Hello to " + to + " from " + from;
			}
	}
}

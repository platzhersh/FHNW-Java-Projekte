package ch.fhnw.edu.flashcard.web;

import ch.fhnw.edu.flashcard.domain.Questionnaire;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/questionnaires")
@Controller
public class QuestionnaireController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Questionnaire questionnaire, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, questionnaire);
            return "questionnaires/create";
        }
        uiModel.asMap().clear();
        questionnaire.persist();
        return "redirect:/questionnaires/" + encodeUrlPathSegment(questionnaire.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Questionnaire());
        return "questionnaires/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("questionnaire", Questionnaire.findQuestionnaire(id));
        uiModel.addAttribute("itemId", id);
        return "questionnaires/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("questionnaires", Questionnaire.findQuestionnaireEntries(firstResult, sizeNo));
            float nrOfPages = (float) Questionnaire.countQuestionnaires() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("questionnaires", Questionnaire.findAllQuestionnaires());
        }
        return "questionnaires/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Questionnaire questionnaire, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, questionnaire);
            return "questionnaires/update";
        }
        uiModel.asMap().clear();
        questionnaire.merge();
        return "redirect:/questionnaires/" + encodeUrlPathSegment(questionnaire.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Questionnaire.findQuestionnaire(id));
        return "questionnaires/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Questionnaire questionnaire = Questionnaire.findQuestionnaire(id);
        questionnaire.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/questionnaires";
    }

	void populateEditForm(Model uiModel, Questionnaire questionnaire) {
        uiModel.addAttribute("questionnaire", questionnaire);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}

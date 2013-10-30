package ch.fhnw.edu.flashcard.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Configurable;

import ch.fhnw.edu.flashcard.domain.Questionnaire;

@Configurable
@FacesConverter("ch.fhnw.edu.flashcard.web.converter.QuestionnaireConverter")
public class QuestionnaireConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Questionnaire.findQuestionnaire(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Questionnaire ? ((Questionnaire) value).getId().toString() : "";
    }
}

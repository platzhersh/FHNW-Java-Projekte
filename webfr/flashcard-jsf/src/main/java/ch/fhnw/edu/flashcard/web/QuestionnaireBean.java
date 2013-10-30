package ch.fhnw.edu.flashcard.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ch.fhnw.edu.flashcard.domain.Questionnaire;

@ManagedBean
@SessionScoped
public class QuestionnaireBean implements Serializable  {
	private static final long serialVersionUID = 1L;

	private transient Questionnaire questionnaire;
	private transient List<Questionnaire> allQuestionnaires;

	public String findAllQuestionnaires() {
        allQuestionnaires = Questionnaire.findAllQuestionnaires();
        return null;
    }
	
	public List<Questionnaire> getQuestionnaires() {
		findAllQuestionnaires();
		return allQuestionnaires;
	}

	public Questionnaire getQuestionnaire() {
        if (questionnaire == null) {
            questionnaire = new Questionnaire();
        }
        return questionnaire;
    }

	public String persist() {
        if (questionnaire.getId() != null) {
            questionnaire.merge();
        } else {
            questionnaire.persist();
        }
        reset();
        findAllQuestionnaires();
        // using implicit navigation rule
        return "/pages/main?faces-redirect=true";
    }
	
	public String create() {
		questionnaire = new Questionnaire();
        // using implicit navigation rule
		return "/pages/questionnaires/create";
	}
	
	public String show(Questionnaire q) {
		questionnaire = q;
        // using implicit navigation rule
		return "/pages/questionnaires/show";
	}

	public String update(Questionnaire q) {
		questionnaire = q;
        // using implicit navigation rule
		return "/pages/questionnaires/update";
	}

	public String delete(Questionnaire q) {
		if (q != null) {
			q.remove();
		}
        reset();
        findAllQuestionnaires();
        // using implicit navigation rule
        return "/pages/main?faces-redirect=true";
    }

	public void reset() {
        questionnaire = null;
    }

}

package ch.fhnw.edu.flashcard.web;

import ch.fhnw.edu.flashcard.domain.Questionnaire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Questionnaire, String> getQuestionnaireToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ch.fhnw.edu.flashcard.domain.Questionnaire, java.lang.String>() {
            public String convert(Questionnaire questionnaire) {
                return new StringBuilder().append(questionnaire.getSubject()).append(' ').append(questionnaire.getDescription()).toString();
            }
        };
    }

	public Converter<Long, Questionnaire> getIdToQuestionnaireConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, ch.fhnw.edu.flashcard.domain.Questionnaire>() {
            public ch.fhnw.edu.flashcard.domain.Questionnaire convert(java.lang.Long id) {
                return Questionnaire.findQuestionnaire(id);
            }
        };
    }

	public Converter<String, Questionnaire> getStringToQuestionnaireConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, ch.fhnw.edu.flashcard.domain.Questionnaire>() {
            public ch.fhnw.edu.flashcard.domain.Questionnaire convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Questionnaire.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getQuestionnaireToStringConverter());
        registry.addConverter(getIdToQuestionnaireConverter());
        registry.addConverter(getStringToQuestionnaireConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}

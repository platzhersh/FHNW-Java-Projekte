package ch.fhnw.edu.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ch.fhnw.edu.basic.domain.FlashCard;
import ch.fhnw.edu.basic.domain.Questionnaire;
import ch.fhnw.edu.basic.util.QuestionnaireInitializer;

@SuppressWarnings("serial")
public class BasicServlet extends HttpServlet {
	private List<Questionnaire> questionnaires;

	private Logger logger = Logger.getLogger(this.getClass());

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=ISO-8859-1");
		// get parameters from the request
		String answerRequest = request.getParameter("answer");
		String questionnaireRequest = request.getParameter("questionnaire");

		if ((questionnaireRequest != null) && (answerRequest == null)) {
			logger.debug("questionnaire request: " + questionnaireRequest);
			handleQuestionnaireRequest(request, response, questionnaireRequest);
		} else if (answerRequest != null) {
			logger.debug("answer request: " + answerRequest);
			String answer = request.getParameter("answer");
			handleAnswerRequest(response, questionnaireRequest, answer);
		} else {
			logger.debug("index request");
			handleIndexRequest(request, response);
		}
	}

	private void handleIndexRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// create html response
		PrintWriter writer = response.getWriter();
		writer.append("<html><head><title>Example</title></head><body>");
		writer.append("<h3>Fragebögen</h3>");
		for (Questionnaire questionnaire : questionnaires) {
			String url = "?questionnaire=" + questionnaire.getSubject();
			writer.append("<a href=" + response.encodeURL(url) + ">"
					+ questionnaire.getTitle() + "</a><br/>");
		}
		writer.append("</body></html>");
	}

	private void handleAnswerRequest(HttpServletResponse response,
			String questionnaire, String answer) throws IOException {

		// create html response
		String backUrl = "?questionnaire=" + questionnaire;
		PrintWriter writer = response.getWriter();
		writer.append("<html><head><title>Example</title></head><body>");
		writer.append("<h3>" + answer + "</h3>");
		writer.append("<a href=" + response.encodeURL(backUrl)
				+ ">Zurück zum Fragebogen</a>");
		writer.append("</body></html>");
	}

	private void handleQuestionnaireRequest(HttpServletRequest request,
			HttpServletResponse response, String shortname) throws IOException {

		List<FlashCard> entries = new ArrayList<FlashCard>();
		Questionnaire questionnaire = findQuestionnaireByKey(shortname);
		if (questionnaire != null) {
			entries = questionnaire.getFlashCards();
		}

		// create html response
		int counter = 1;
		PrintWriter writer = response.getWriter();

		writer.append("<html><head><title>Example</title></head><body>");
		writer.append("<h3>Fragen</h3><table>");

		for (FlashCard entry : entries) {
			String answerUrl = "?questionnaire=" + questionnaire.getSubject()
					+ "&answer=" + entry.getAnswer();
			answerUrl = answerUrl.replace(" ", "%20");
			writer.append("<tr>" + "<td>" + counter++ + "</td><td><a href="
					+ response.encodeURL(answerUrl) + ">" + entry.getQuestion()
					+ "</a></td></tr>");
		}
		String url = request.getContextPath()+request.getServletPath();
		writer.append("</table><p><a href=" + response.encodeURL(url)
				+ ">Index</p>");
		writer.append("</body></html>");
	}

	private Questionnaire findQuestionnaireByKey(String subject) {
		for (Questionnaire q : questionnaires) {
			if (q.getSubject().equals(subject)) {
				return q;
			}
		}
		return null;
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		questionnaires = QuestionnaireInitializer.createQuestionnaires();
	}

}

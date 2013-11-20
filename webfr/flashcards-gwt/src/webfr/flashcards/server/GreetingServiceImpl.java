package webfr.flashcards.server;

import java.util.List;

import webfr.flashcards.client.GreetingService;
import webfr.flashcards.server.model.Card;
import webfr.flashcards.server.model.Cardbox;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (input == null || input.length() < 4) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

    if ("showdata".equals(input)) {
      StringBuffer sb = new StringBuffer();
  		sb.append("CARDBOXES\n");
  		sb.append("---------\n");
  		
  		try {
        List<Cardbox> cbl = GreetingServiceImpl.dal.getCardboxes();
        for (Cardbox box : cbl) {
          sb.append("\tTitle: " + box.getTitle() + "\n");
          sb.append("\tDescription: " + box.getDescription() + "\n");
          sb.append("\tVersion: " + box.getVersion() + "\n");
          for (Card card : box.getCards()) {
            sb.append("\t\tQ: " + card.getQuestion() + "\n");
            sb.append("\t\tA: " + card.getAnswer() + "\n");
          }
        }
      } catch (Exception e) {
        sb.append(e.getStackTrace().toString());
        e.printStackTrace();
      }
  
  		return sb.toString();
    } else {
      String serverInfo = getServletContext().getServerInfo();
      String userAgent = getThreadLocalRequest().getHeader("User-Agent");

      // Escape data from the client to avoid cross-site script vulnerabilities.
      input = escapeHtml(input);
      userAgent = escapeHtml(userAgent);

      return "Hello, " + input + "!<br><br>I am running " + serverInfo
          + ".<br><br>It looks like you are using:<br>" + userAgent;

    }
	}
	
  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   * 
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;");
  }

	private static ServiceLayer dal = new ServiceLayer();
}

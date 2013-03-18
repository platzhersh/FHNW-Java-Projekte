package bank.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		name="webclient", 
		urlPatterns={"/*"}
)
public class WebClient extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<div>Bank Webclient</div>");
		out.println("<title>Bank Webclient</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Bank Webclient</h1>");
		out.println("</body>");
		out.println("</html>");
		}


}

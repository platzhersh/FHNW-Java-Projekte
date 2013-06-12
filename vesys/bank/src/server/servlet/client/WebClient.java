package server.servlet.client;

import helpers.Command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import bank.Bank;
import bank.driver.DriverSocket;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.MyBank;

@WebServlet(
		name="webclient", 
		urlPatterns={"/*"}
)
public class WebClient extends HttpServlet {
	
	private DriverSocket driver;
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("Connect socket");
		this.driver = new DriverSocket();
		String[] s = { "localhost", "4200"};
		this.driver.connect(s);
		System.out.println("Socket connected");
		
		System.out.println("get BankAccounts");
		Bank b = driver.getBank();
		Set<String> accs = b.getAccountNumbers();
		System.out.println("BankAccounts received");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Bank Webclient</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<h1>Bank Webclient</h1>");
		out.println("<div class=\"status\"></div>");
		out.println("accounts: "+accs.toString());
		out.println("<form method=\"GET\" action=\"webclient\">");
		out.println("<input type=\"submit\">");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		}


}

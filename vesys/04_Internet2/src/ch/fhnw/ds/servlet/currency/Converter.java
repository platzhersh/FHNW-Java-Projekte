/*
 * Copyright (c) 2000-2009 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

// javac -classpath .;%CATALINA_HOME%\lib\servlet-api.jar servlets\Converter.java

package ch.fhnw.ds.servlet.currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		name="CurrencyConverter", 
		urlPatterns={"/convert"}
)
public class Converter extends HttpServlet {

	public void doGet(HttpServletRequest request,
					HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String amount = request.getParameter("amt");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		
		String res = computeResult(amount, from, to);

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Currency Converter</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h1>Currency Converter</h1>");
		out.println(amount + " " + from + " = ");
		out.println(res);	
		out.println("</body>");
		out.println("</html>");
	}

	
	private String computeResult(String amount, String from, String to){
		String TOKEN = "<td width=\"47%\" align=\"left\">";
		try {
			String query = "http://www.xe.com/currencyconverter/convert/?Amount="+amount+"&From="+from+"&To="+to;
			System.out.println(query);
			URL url = new URL(query);
			URLConnection c = url.openConnection();
			c.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.1)");
			BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String line = r.readLine();
			while(line != null){
				int pos = line.indexOf(TOKEN, 0);
				if(pos != -1){
					pos = line.indexOf(">", pos);
					String res = line.substring(pos+1);
					return res.substring(0, res.indexOf("&"));
				}
				line = r.readLine();
			}
			return "no result found";
		} catch (Exception e) {
			String msg = e.getMessage();
			if("".equals(msg)) return e.toString(); else return msg;
		}
	}

}

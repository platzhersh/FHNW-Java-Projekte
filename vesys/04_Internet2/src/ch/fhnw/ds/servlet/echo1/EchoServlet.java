/*
 * Copyright (c) 2000-2010 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.ds.servlet.echo1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EchoServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println(">> EchoServlet");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body><pre>");
		Enumeration<?> e;

		out.println("Properties:");
		out.println("getMethod:        " + request.getMethod());
		out.println("getContentLength: " + request.getContentLength());
		out.println("getContentType:   " + request.getContentType());
		out.println("getProtocol:      " + request.getProtocol());
		out.println("getRemoteAddr:    " + request.getRemoteAddr());
		out.println("getRemotePort:    " + request.getRemotePort());
		out.println("getRemoteHost:    " + request.getRemoteHost());
		out.println("getRemoteUser:    " + request.getRemoteUser());
		out.println("getServerName:    " + request.getServerName());
		out.println("getAuthType:      " + request.getAuthType());
		out.println("getQueryString:   " + request.getQueryString());
		out.println("getRequestURI:    " + request.getRequestURI());
		out.println("getServletPath:   " + request.getServletPath());

		out.println("\nHeaders:");
		e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			out.println(name + " = " + request.getHeader(name));
		}

		out.println("\nParameters:");
		e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			out.println(name + " = " + request.getParameter(name));
		}

		out.println("</pre></body></html>");
		System.out.println("<< EchoServlet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}


/*

<?xml version="1.0" encoding="ISO-8859-1"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
                    http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
version="3.0">

  <servlet>
    <servlet-name>EchoServlet</servlet-name>
    <servlet-class>EchoServlet</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>EchoServlet</servlet-name>
    <url-pattern>/echo</url-pattern>
  </servlet-mapping>

</web-app>

*/
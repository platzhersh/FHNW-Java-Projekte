/*
 * Copyright (c) 2000-2013 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.ds.xmlrcp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;


@WebServlet(
	urlPatterns="/*",
	initParams={ 
		@WebInitParam(name="enabledForExtensions", value="true"),
		@WebInitParam(name="enabledForExceptions", value="true") 
	}
)
public class XmlRpcServlet extends org.apache.xmlrpc.webserver.XmlRpcServlet {
	{ System.out.println("XmlRpcServlet() called"); }
}

package ch.fhnw.ds.internet;

import java.net.URL;

class ParseURL {
	public static void main(String[] args) throws Exception {
		String s = "ftp://java-tutor.de:8080/java/faq/index.html?key=val#Lang";
		//String s = "file:///c:/temp/data.txt";
		//String s = "https://www.fhnw.ch";
		
		URL url = new URL(s);
		System.out.println(s);
		System.out.println("Protocol:    " + url.getProtocol());// http
		System.out.println("Host:        " + url.getHost()); 	// java-tutor.com
		System.out.println("Port:        " + url.getPort()); 	// 80
		System.out.println("File:        " + url.getFile()); 	// /java/faq/index.html?key=val
		System.out.println("Path:        " + url.getPath()); 	// /java/faq/index.html
		System.out.println("Query:       " + url.getQuery()); 	// key=val
		System.out.println("Ref:         " + url.getRef()); 	// Lang

		System.out.println("Authority:   " + url.getAuthority()); 
		System.out.println("DefaultPort: " + url.getDefaultPort()); 
		System.out.println("UserInfo:    " + url.getUserInfo()); 
	}
}

/* => rt.jar

doc
file
ftp
gopher
http
jar
mailto
netdoc
verbatim
systemresource

https

*/

package ch.fhnw.ds.internet;

import static java.lang.System.out;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class EchoGet {
	
	public static void main(String[] args) throws Exception{
		URL url = new URL("http://localhost:81/echo/xxx?a=1");
		URLConnection c = url.openConnection();
		HttpURLConnection hc = (HttpURLConnection)c;
		hc.disconnect();
		
		out.println( "Date :           " + new Date(c.getDate()) );
		out.println( "Last Modified :  " + new Date(c.getLastModified()) );
		out.println( "Content type :   " + c.getContentType() );
		out.println( "Content length : " + c.getContentLength() ); 
		
//		Object res = c.getContent();
//		out.println( "Content:         " + res);
	}
	
}

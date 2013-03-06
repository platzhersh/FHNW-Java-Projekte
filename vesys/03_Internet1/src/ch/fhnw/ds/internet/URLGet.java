package ch.fhnw.ds.internet;

import static java.lang.System.out;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class URLGet {
	
	public static void main(String[] args) throws Exception{
		test1();
	}
	
	static void test1() throws Exception {
		URL url = new URL("http://www.fhnw.ch");
		//URL url = new URL("http://www.gruntz.ch/img/javalogo.gif");
		//URL url = new URL("http://www.nch.com.au/acm/11k16bitpcm.wav");
		URLConnection c = url.openConnection();
		
		out.println( "Date :           " + new Date(c.getDate()) );
		out.println( "Last Modified :  " + new Date(c.getLastModified()) );
		out.println( "Content type :   " + c.getContentType() );
		out.println( "Content length : " + c.getContentLength() ); 
		
		Object res = c.getContent();
		out.println( "Content:         " + res);
	}
	
}

/*

Date :           Tue Mar 05 07:13:46 CET 2013
Last Modified :  Tue Nov 29 07:33:07 CET 2011
Content type :   text/html
Content length : 1963
Content:         sun.net.www.protocol.http.HttpURLConnection$HttpInputStream@63cd0037

Date :           Tue Mar 05 07:14:09 CET 2013
Last Modified :  Sun Jun 29 19:58:56 CEST 2003
Content type :   image/gif
Content length : 1079
Content:         sun.awt.image.URLImageSource@315fb94a

Date :           Tue Mar 05 07:14:21 CET 2013
Last Modified :  Wed Oct 08 22:44:14 CEST 2008
Content type :   audio/x-wav
Content length : 304578
Content:         sun.applet.AppletAudioClip@7cec59b

*/

package ch.fhnw.ds.internet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class URLPost {
	
	public static void main(String[] args) throws Exception {
		URL  url = new URL("http://localhost:8888/test");
//		URL  url = new URL("http://www.gruntz.ch/img/javalogo.gif");
		HttpURLConnection c = (HttpURLConnection)url.openConnection();
		
		System.out.println(c); // sun.net.www.protocol.http.HttpURLConnection
								// extends  java.net.HttpURLConnection
		
		System.out.println("getDoInput  = " + c.getDoInput());
		System.out.println("getDoOutput = " + c.getDoOutput());

		c.setRequestMethod("POST");
		
		c.setDoOutput(true);
		c.setDoInput(true);
		// c.setUseCaches(false); // => Cache-Control: no-cache
									//  Pragma: no-cache
		
		//((HttpURLConnection)c).setChunkedStreamingMode(256);
		
		String msg = "Hello World from URLPost!";
		
		//((HttpURLConnection)c).setFixedLengthStreamingMode(1038);
		
		
		c.connect();
		
		Thread.sleep(1000);
		
		System.out.println("method = "+((HttpURLConnection)c).getRequestMethod());
		OutputStream os = c.getOutputStream();
		System.out.println("method = "+((HttpURLConnection)c).getRequestMethod());
		System.out.println("OutputStream = "+os.getClass().getName());
									// PosterOutputStream if contentlength is not specified
									// sun.net.www.protocol.http.HttpURLConnection$StreamingOutputStream 
									// 		for chunked encoding or fixed size encoding
		OutputStreamWriter wr = new OutputStreamWriter(os);
		wr.write(msg);
		// write(wr, 1000);
		wr.flush();
		
		InputStream is = c.getInputStream();
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line = rd.readLine();
		while(line != null){
			System.out.println(line); line = rd.readLine(); 
		}
	}

	static void write(OutputStreamWriter wr, int size) throws Exception {
		for(int i=0; i<size; i++){
			wr.write("x");
			if(i % 80 == 0) {
				wr.write("\n");
				wr.flush();
				Thread.sleep(1000);
			}
		}
		
	}

}

package ch.fhnw.glatthard.christian.oop2.swb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Model {
	
	
	
	public Model(){

	}
	
	/*public void getHTML1(){
		
		
			Socket s = null;
			OutputStreamWriter out = null;
			BufferedReader inReader = null;
			String lineIn;
			StringBuffer urlInhalt = new StringBuffer();
			// Network errors are always possible
			try {
			// Set up the socket
			s = new Socket(txtIP.getText(), new Integer(txtPort.getText()));
			// Send our request, using the HTTP 1.0 protocol
			out = new OutputStreamWriter(s.getOutputStream());
			out.write("GET / HTTP/1.0\n");
			out.write("User-Agent: Browser0\n");
			out.write("Host: " + txtIP.getText() + ":" + txtPort.getText() + "\n");
			out.write("Accept: text/html, /*\n\n");
			out.flush();

	
			
			// Set up the reader classes
			InputStream in1 = s.getInputStream();
			InputStreamReader in2 = new InputStreamReader(in1);
			inReader = new BufferedReader(in2);
			while ((lineIn = inReader.readLine()) != null) {
			urlInhalt.append(lineIn + "\n");
			}
			// Show the result in "txtInhalt"
			txtInhalt.setText(urlInhalt.toString());
			}
			// If an error occurred, show the error message in txtInhalt
			catch (Exception err) {
			txtInhalt.setText("ERROR: " + err.toString());
			} finally {
			try {
			if (out != null) out.close();
			if (inReader != null) inReader.close();
			if (s != null) s.close();
			} catch (Exception e) {
			
			}
		
	
	public void getHTML2(){
		
	}*/
}

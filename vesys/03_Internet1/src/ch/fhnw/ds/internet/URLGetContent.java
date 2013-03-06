package ch.fhnw.ds.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ContentHandler;
import java.net.ContentHandlerFactory;
import java.net.URL;
import java.net.URLConnection;

public class URLGetContent {
	public static void main(String[] args) throws Exception {
		URLConnection.setContentHandlerFactory(new Factory());

		URL url = new URL("http://localhost:8080/a1.acc");
		URLConnection c = url.openConnection();
		System.out.println("c.getContentType() = " + c.getContentType());
		Object obj = c.getContent();
		System.out.println(obj);
	}
	
	static class Factory implements ContentHandlerFactory {
		@Override
		public ContentHandler createContentHandler(String mimetype) {
			System.out.println("createContentHandler("+mimetype+")");
			if(mimetype.equals("text/x-account")){
				return new AccountContentHandler();
			}
			return null;
		};
	}
	
	static class AccountContentHandler extends ContentHandler {
		@Override
		public Object getContent(URLConnection urlc) throws IOException {
			BufferedReader r = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			return new Account(r.readLine(), r.readLine(), r.readLine());
		}
		
	}
	
	static class Account {
		private String number;
		private String owner;
		private double balance;

		Account(String number, String owner, String balance){
			this.number = number;
			this.owner = owner;
			this.balance = Double.parseDouble(balance);
		}
		
		@Override
		public String toString(){
			return "Account "+number+" ["+owner+"] = "+balance;
		}
	}

}

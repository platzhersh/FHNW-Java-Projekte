package ch.fhnw.ds.xmlrcp.currency;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CurrencyImpl  {

	public String convert(String amount, String from, String to){
		String TOKEN = "<td width=\"47%\" align=\"left\">";
		try {
			String query = "http://www.xe.com/currencyconverter/convert/?Amount="+amount+"&From="+from+"&To="+to;
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

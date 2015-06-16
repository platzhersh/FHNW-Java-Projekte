import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main {

	public static void main(String[] args) {
		 try {

             String url = "http://api.swissrugbystats.ch/leagues/.json";

             URL obj = new URL(url);
             HttpURLConnection con = (HttpURLConnection) obj.openConnection();

             // optional default is GET
             con.setRequestMethod("GET");

             int responseCode = con.getResponseCode();
             System.out.println("\nSending 'GET' request to URL : " + url);
             System.out.println("Response Code : " + responseCode);

             BufferedReader in = new BufferedReader(
                     new InputStreamReader(con.getInputStream()));
             String inputLine;
             StringBuffer response = new StringBuffer();

             while ((inputLine = in.readLine()) != null) {
                 response.append(inputLine);
             }
             in.close();

             //print result
             System.out.println(response.toString());

         } catch (Exception e) {
             System.err.println("Error on fetching leagues: " + e.toString());
         }
         finally {
             System.out.println("Error");
         }

	}

}

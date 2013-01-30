package beispiel1;

// -----> Import der benötigten Klassen:
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
// <----- 

/**
 * last change: 04.11.2004
 * run: java -cp class beispiel1.DemoClient_1 127.0.0.1 1111
 */
public class DemoClient_1{
 
  /**
  * @args: Kommandozeilen-Parameter
  */
  public static void main (String args[]) {
    try {
	if(args.length<2) {
	  System.out.println("usage: host port");
	  System.exit(1);
		}

     // Den Server kontaktieren:
     Socket server = new Socket ( args[0], Integer.parseInt(args[1]) );     
            
     System.out.println ("Server kontaktiert...");
            
     // Streams öffnen:
     PrintWriter out = 
         new PrintWriter ( server.getOutputStream() );
     BufferedReader in = new BufferedReader (
        new InputStreamReader ( 
            server.getInputStream()
        )
     );        
     
     // Kommunikationsszenario abarbeiten:
     String befehl_1 = "Hallo Server";
  /* "Hallo Server" */
     System.out.println ("sende: " + befehl_1);   
     out.println ( befehl_1 );
     out.flush();

  /* "Hallo Client" */
     String antwort_1 = in.readLine();
     System.out.println ( "Server antwortet: " + antwort_1 );
  
  /* "Wieviel Uhr ist es?" */
     String befehl_2 = "Wieviel Uhr ist es?";
     System.out.println ("sende: " + befehl_2);
     out.println ( befehl_2 );
     out.flush();
  
  /* "13:21:23" */
     String antwort_2 = in.readLine();
     System.out.println ( "Server antwortet: " + antwort_2 );
    } catch (Exception e) {
        // Fehler:
        System.out.println ( "Fehler: " + e );
    }
  }

}
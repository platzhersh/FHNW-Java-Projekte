/**
  * editor:   gyp
  * Dieser Server kann einen Client auf ein mal bedienen
  */


package beispiel1;

// -----> Import der benötigten Klassen:
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Date;
import java.text.SimpleDateFormat;
// <-----

public class DemoServer_1 {
   
  /**
    * erzeugt einen neuen DemoServer_1 
    * @portNummer: Der Port, über den Server und Client kommunizieren
    */
  public DemoServer_1(int portNummer) throws IOException {
    // portNummer belegen:
    ServerSocket ss = new ServerSocket ( portNummer );
    System.out.println ("Server erfolgreich auf Port " + portNummer + " gestartet!");
       
    while (true) {
      // Auf Client-Anfragen warten:        
      Socket client_Anfrage = ss.accept();
       
      // Client-Anfrage bearbeiten:
      bearbeite_Anfrage ( client_Anfrage );
    }
  }

  private void bearbeite_Anfrage ( Socket anfrage ) throws IOException {
    // Streams initialisieren:
    BufferedReader in = new BufferedReader (
            new InputStreamReader ( 
                anfrage.getInputStream()
            )
      );
    PrintWriter out = new PrintWriter ( anfrage.getOutputStream() );
        
    // Kommunikationsszenario abarbeiten:        
    // ==> "Hallo Server"
    String hallo = in.readLine();
    System.out.println ("Client sagt: " + hallo);
    String antwort_1 = "Hallo Client!";
    System.out.println ("sende: " + antwort_1);
    // ==> "Hallo Client"
    out.println ( antwort_1 );
    out.flush();
    // ==> "Wieviel Uhr ist es?"
    String frage = in.readLine();
    System.out.println ("Client fragt: " + frage);
    String antwort_2 = aktuelle_Uhrzeit();
    System.out.println ("sende: " + antwort_2);
    // ==> "13:32:22"
    out.println ( antwort_2 );
    out.flush();
        
    // Kommunikationskanal schließen:
    in.close();
    out.close();
    anfrage.close();
  }
        
    
  /**
    * @args: Kommandozeilen-Parameter
    */
  public static void main (String args[]) {
    // Server starten:
    try {
      new DemoServer_1 ( 1111 );
    } catch (IOException e) {
      // Ein I/O-Fehler ist aufgetreten:
      System.out.println ("Fehler: " + e);
    }            
  } 

    //--------------------------------------------------> Hilfs-Methoden:
   
    private String aktuelle_Uhrzeit () {
        Date jetzt = new Date();
        SimpleDateFormat formatter
            = new SimpleDateFormat ("HH:mm:ss '-' dd.MM.yyyy");

        // verlangsamt die Antwort künstlich um 10 sek.
        try {
            Thread.sleep( 10000 );
        } catch (InterruptedException e) {}
        
        // Rückgabe:
        return formatter.format( jetzt );
    }
   
}
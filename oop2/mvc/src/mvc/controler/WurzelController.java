/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mvc.model.*;
import mvc.view.*;

/**
 * Der Controller muss beide die View und das Model kennen
 * da dieser f�r die Kommunikation zwischen den Beiden sorgt
 */
public class WurzelController {

    private WurzelView view;
    private WurzelModel model;

    public WurzelController(WurzelView view, WurzelModel model){
        this.model = model;
        this.view = view;

        addListener();
    }

    /**
     * Die Listener, die wir aus den Internen Klassen generieren
     * werden der View bekannt gemacht, sodass diese mit
     * uns (dem Controller) kommunizieren kann
     */
    private void addListener(){
        this.view.setWurzelBerechnenListener(new WurzelBerechnenListener());
        this.view.setResetFormListener(new ResetFormListener());
    }

    /**
     * Inneren Listener Klassen implementieren das Interface ActionListener
     *
     * 1: Hier wird erst der eingegebene Wert aus der View geholt
     * 2: Der Wert wird dem Model übergeben und die Wurzel berechnet
     * 3: Die berechnete Wurzel wird aus dem Model geladen und
     * 4: Wieder der View zum Darstellen übergeben
     *
     * ACHTUNG: Fehlerprüfung muss noch implementeirt werden
     */
    class WurzelBerechnenListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
       		long wert = Long.valueOf(view.getEingabe());
       		model.berechneWurzel(wert);
       		view.setErgebnis(String.valueOf(model.getWurzel()));

        }
    }

    /**
     * Hier wird dem View und dem Model gesagt ihre gespeicherten
     * Werte zu löschen.
     */
    class ResetFormListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            view.resetView();
            model.zurueckSetzen();
        }
    }
}

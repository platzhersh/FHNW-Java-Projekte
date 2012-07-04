/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;


/**
 * Das Model ist komplett unabhängig von den anderen
 * Klassen und weiss nicht was um ihn herum geschieht.
 * Es ist völlig egal ob man dieses Model aus einem
 * Fenster oder einer Konsolen Eingabe verwendet -
 * beides würde funktionieren.
 */
public class WurzelModel {

    long _value;

    public WurzelModel(){
        zurueckSetzen();
    }

    public void zurueckSetzen(){
        this._value = 0;
    }

    public void berechneWurzel(long wert){
    	this._value =  (long)java.lang.Math.sqrt(wert);
    }

    public long getWurzel(){
        return this._value;
    }
}

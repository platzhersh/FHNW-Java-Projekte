/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import mvc.model.WurzelModel;

/* Die View-Klasse enth‰lt nur die Pr‰sentation
 * hier sollte man keinerlei Programmlogik finden
 * alle Berechnungen und Reaktionen auf Benutzeraktionen
 * sollten allesammt im Controller stehen
 */
public class WurzelView extends JFrame{

    private JLabel lbl1 = new JLabel("Eingabe: ");
    private JTextField txtEingabe = new JTextField(3);
    private JButton cmdCalc = new JButton("Wurzel Berechnen >");
    private JTextField txtErg = new JTextField(5);
    private JButton cmdClear = new JButton("Zur¸cksetzen");

    public WurzelView(){
        super("Wurzel Berechnen");
        initForm();
    }

    /**
     * Die JForm initialisieren und alle Steuerelemente
     * darauf positionieren
     */
    private void initForm(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setBounds(200, 200, 500, 100);

        this.add(lbl1);
        this.add(txtEingabe);
        this.add(cmdCalc);
        this.add(txtErg);
        this.add(cmdClear);
    }

    public void resetView(){
        this.txtEingabe.setText("");
        this.txtErg.setText("");
    }

    public String getEingabe(){
    	if (this.txtEingabe.getText().compareTo("") == 0) {
    		return "0";
    	} 
    	else {
    		return this.txtEingabe.getText();
    	}
    }

    public void setErgebnis(String erg){
        this.txtErg.setText(erg);
    }

    /**
     * Funktionen bereitstellen, mit denen man sp√§ter aus
     * dem Controller die n√∂tigen Listener hinzuf√ºgen kann
     */
    public void setWurzelBerechnenListener(ActionListener l){
        this.cmdCalc.addActionListener(l);
    }

    public void setResetFormListener(ActionListener l){
        this.cmdClear.addActionListener(l);
    }
}

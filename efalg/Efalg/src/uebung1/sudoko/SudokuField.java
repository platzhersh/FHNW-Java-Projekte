package uebung1.sudoko;

import javax.swing.JTextField;

public class SudokuField extends JTextField {
	
	int i;
	int j;
	boolean fix;
	
	// Konstruktor
	public SudokuField(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	// Getter & Setter
	public int getValue() {
		return Integer.parseInt(super.getText());
	}
	
	public void setValue(int v) {
		super.setText(Integer.toString(v));
	}
	
	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}

}

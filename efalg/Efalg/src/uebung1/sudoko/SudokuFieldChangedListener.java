package uebung1.sudoko;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class SudokuFieldChangedListener implements KeyListener {

	SudokuController controller;
	
	// Konstruktor
	public SudokuFieldChangedListener(SudokuController c) {
		controller = c;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		SudokuField txt = (SudokuField) e.getSource();
		
		System.out.println("Released: " + txt.getText());
		
		if (txt.getText() != null) {
			try {
				int a = Integer.parseInt(txt.getText());
				if (a < 1 || a > 9) {
					txt.setText("");
					throw new IllegalArgumentException();					
				}
				else {
					controller.model.set(txt.getI(), txt.getJ(), a);
				}
			} catch (Exception ex) {
				System.err.println(ex.toString());
			} finally {
				txt.setText(controller.model.getText(txt.getI(), txt.getJ()));
				controller.view.validate();
				controller.view.repaint();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//
	}
	
}

package uebung1.sudoko;

import java.awt.EventQueue;

import uebung1.warnsdorf.WarnsdorfController;

public class Sudoku {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					SudokuController controller = new SudokuController();
					controller.view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

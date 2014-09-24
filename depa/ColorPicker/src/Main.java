import java.awt.EventQueue;

import controller.ColorPickerController;

import view.ColorPickerView;


public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ColorPickerView frame = new ColorPickerView();
					frame.setVisible(true);
					ColorPickerController controller = new ColorPickerController(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
}

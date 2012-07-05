import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Der Button wurde gedrückt");
	}

}

package patterns.command;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DialogCommandFrame extends JFrame {

	public static void main(String args[]) {
		DialogCommandFrame frame = new DialogCommandFrame();
		frame.setTitle("Swing Actions");
		frame.setSize(500, 400);
		frame.setLocation(400, 200);
		frame.setVisible(true);
	}

	public DialogCommandFrame() {
		JMenuBar mb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		Action a = new ShowDialogAction();
		fileMenu.add(a);
		fileMenu.add(new ToggleDialogAction(a));
		fileMenu.add(new ExitAction());

		mb.add(fileMenu);
		setJMenuBar(mb);

		add(new JButton(a));
	}
}

class ShowDialogAction extends AbstractAction {
	public ShowDialogAction() {
		super("Open Dialog");
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog((Component) e.getSource(),
				"An action generated this dialog");
	}
}

class ExitAction extends AbstractAction {
	public ExitAction() {
		super("Exit");
	}

	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}

class ToggleDialogAction extends AbstractAction {
	Action a;

	ToggleDialogAction(Action a) {
		super("Disable Dialog");
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		this.a = a;
	}

	public void actionPerformed(ActionEvent e) {
		a.setEnabled(!a.isEnabled());
		if (a.isEnabled()) {
			this.putValue(Action.NAME, "Disable Dialog");
			this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		} else {
			this.putValue(Action.NAME, "Enable Dialog");
			this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		}
	}

}

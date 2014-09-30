package patterns.observer.copyright;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class CopyRightGUI extends JFrame {
	private static final String fhnw = "FHNW";
	private static final String input = "(c)";

	public static void main(String[] args) {
		String given = (args.length > 0) ? args[0] : fhnw;
		String typeThat = (args.length > 1) ? args[1] : input;
		JFrame f = new CopyRightGUI(given, typeThat);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

	CopyRightGUI(final String given, final String typeThat) {
		final TextModel m = new TextModel();
		CorrectionListener cl = new CorrectionListener(m);
		TextListener tl = new TextListener();
		m.addListener(cl);
		m.addListener(tl);
		int pos = 0;
		for(char ch : given.toCharArray()) m.insert(pos++, ch);
		setLayout(new GridLayout(2, 2, 8, 0));
		add(new JLabel("Next change (press button)"));
		add(new JLabel("Current Text"));
		add(newInsertButton(typeThat, m));
		add(tl);
	}

	private JButton newInsertButton(String typeThat, TextModel wm) {
		JButton key = new JButton(buttonText(typeThat.charAt(0), 0));
		key.addActionListener(new ActionListener() {
			int pos = 0;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				char ch = typeThat.charAt(pos);
				wm.insert(pos, ch);
				pos++;
				if (pos >= typeThat.length()) {
					key.setEnabled(false);
				} else {
					key.setText(buttonText(typeThat.charAt(pos), pos));
				}
			}
		});
		return key;
	}

	private String buttonText(char nextChar, int pos) {
		return "insert \"" + nextChar + "\" at position " + pos;
	}
}

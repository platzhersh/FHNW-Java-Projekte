package patterns.observer.copyright.sol;

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
		final TextModel m1 = new TextModelWrong();
		CorrectionListener cl1 = new CorrectionListener(m1);
		TextListener tl1 = new TextListener();
		m1.addListener(cl1);
		m1.addListener(tl1);
		
		//final TextModel m2 = new TextModelCorrect3();
		final TextModel m2 = new TextModelCorrect1();
		CorrectionListener cl2 = new CorrectionListener(m2);
		TextListener tl2 = new TextListener();
		m2.addListener(cl2);
		m2.addListener(tl2);
		
		int pos = 0;
		for(char ch : given.toCharArray()) {
			m1.insert(pos, ch);
			m2.insert(pos, ch);
			pos++;
		}
		setLayout(new GridLayout(3, 2, 8, 0));
		add(new JLabel("Next change (press button)"));
		add(new JLabel("Current Text"));
		add(newInsertButton(typeThat, m1, m2));
		add(tl1);
		add(new JLabel(""));
		add(tl2);
		
	}

	private JButton newInsertButton(final String typeThat, final TextModel m1, final TextModel m2) {
		final JButton key = new JButton(buttonText(typeThat.charAt(0), 0));
		key.addActionListener(new ActionListener() {
			int pos = 0;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				char ch = typeThat.charAt(pos);
				m1.insert(pos, ch);
				m2.insert(pos, ch);
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

package patterns.observer.copyright;

import javax.swing.JLabel;

public class TextListener extends JLabel implements Listener {

	TextModel text;
	
	public TextListener(TextModel m) {
		text = m;
	}
	
	@Override
	public void notifyDelete(int from, int len) {
		setText(text.toString());
	}

	@Override
	public void notifyInsert(int pos, char ch) {
		setText(text.toString());
	}
}

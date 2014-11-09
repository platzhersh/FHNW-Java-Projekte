package patterns.observer.copyright.sol;

import javax.swing.JLabel;

public class TextListener extends JLabel implements Listener {

	@Override
	public void notifyDelete(int from, int len) {
		String h = getText().substring(0, from);
		String t = getText().substring(from + len);
		setText(h + t);
	}

	@Override
	public void notifyInsert(int pos, char ch) {
		String h = getText().substring(0, pos);
		String t = getText().substring(pos);
		setText(h + ch + t);
	}
}

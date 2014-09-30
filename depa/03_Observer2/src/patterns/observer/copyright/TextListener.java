package patterns.observer.copyright;

import javax.swing.JLabel;

public class TextListener extends JLabel implements Listener {

	@Override
	public void notifyDelete(int from, int len) {
		System.out.println(String.format("notifyDelete(%d, %d)", from, len));
		String h = getText().substring(0, from);
		String t = getText().substring(from + len);
		setText(h + t);
	}

	@Override
	public void notifyInsert(int pos, char ch) {
		System.out.println(String.format("notifyInsert(%d, %c)", pos, ch));
		String h = getText().substring(0, pos);
		String t = getText().substring(pos);
		setText(h + ch + t);
	}
}

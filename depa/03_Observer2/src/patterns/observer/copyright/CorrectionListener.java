package patterns.observer.copyright;

public class CorrectionListener implements Listener {

	private TextModel text;

	public CorrectionListener(TextModel m) {
		text = m;
	}

	@Override
	public void notifyDelete(int from, int len) {
	}

	@Override
	public void notifyInsert(int pos, char ch) {
		if (pos >= 2) {
			String s = text.getSubstring(pos - 2, 3).toUpperCase();
			if ("(C)".equals(s)) {
				System.out.println("correct on: " + text);
				text.delete(pos - 2, 3);
				text.insert(pos - 2, '©');
				System.out.println("corrected text: " + text);
			}
		}
	}

}

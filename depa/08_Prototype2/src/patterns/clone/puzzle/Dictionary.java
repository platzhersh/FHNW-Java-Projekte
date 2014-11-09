package patterns.clone.puzzle;

public class Dictionary implements Cloneable {
	public String language;
	public final int size;
	public String[] words;

	public Dictionary(String language, int size) {
		this.language = language;
		this.size = size;
		this.words = new String[size];
		for (int i = 0; i < size; i++)
			this.words[i] = "sample word " + i;
	}

	@Override
	public Object clone() {
		try {
			Dictionary d = (Dictionary) super.clone();
			if (words != null) {
				d.words = words.clone();
			}
			return d;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
}

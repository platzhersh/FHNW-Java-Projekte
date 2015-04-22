import java.util.concurrent.atomic.AtomicReference;


public class NumberRange {

	final class Pair {
		public final int lower;
		public final int upper;

		public Pair(int l, int u) {
			this.lower = l;
			this.upper = u;
		}

	}
	// INVARIANT: lower <= upper is guaranteed through compareAndSet
	private final AtomicReference<Pair> pairReference = new AtomicReference<Pair>(new Pair(0,0));

	public int getLower() {
		return pairReference.get().lower;
	}

	public void setLower(int l) {
		while (true) {
			int u = pairReference.get().upper;
			if (l > u)
				throw new IllegalArgumentException();
			if (compareAndSet(l, u))
				return;
		}
	}

	private boolean compareAndSet(int l, int u) {
		return pairReference.compareAndSet(pairReference.get(), new Pair(l, u));
	}

	public int getUpper() {
		return pairReference.get().upper;
	}

	public void setUpper(int u) {
		while (true) {
			int l = pairReference.get().lower;
			if (u < l)
				throw new IllegalArgumentException();
			if (compareAndSet(l, u))
				return;
		}
	}

	public boolean contains(int i) {
		return pairReference.get().lower <= i && i <= pairReference.get().upper;
	}
}

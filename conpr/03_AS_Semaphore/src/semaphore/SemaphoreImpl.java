package semaphore;

public final class SemaphoreImpl implements Semaphore {
	private int value;

	public SemaphoreImpl(int initial) {
		if (initial < 0) throw new IllegalArgumentException();
		value = initial;
	}

	@Override
	public int available() {
		return value;
	}

	@Override
	public void acquire() {

	}

	@Override
	public void release() {

	}
}

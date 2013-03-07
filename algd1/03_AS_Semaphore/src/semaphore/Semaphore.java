package semaphore;

public interface Semaphore {
	int available();

	void acquire();

	void release();
}

package lecture;

import net.jcip.annotations.GuardedBy;

public class Stack {
	private Object lock = new Object();
	
	@GuardedBy("lock")
	private int top = 0;

	@GuardedBy("lock")
	private int[] data = new int[10];

	public void push(int x) {
		synchronized (lock) {
			data[top] = x;
			top++;
		}
	}

	public int pop() {
		synchronized (lock) {
			top--;
			return data[top];
		}
	}
}

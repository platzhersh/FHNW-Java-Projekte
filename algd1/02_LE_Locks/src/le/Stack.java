package le;

import net.jcip.annotations.GuardedBy;

public class Stack {
	@GuardedBy("this")
	private int top = 0;

	@GuardedBy("this")
	private int[] data = new int[10];

	public void push(int x) {
		synchronized (this) {
			data[top] = x;
			top++;
		}
	}

	public int pop() {
		synchronized (this) {
			top--;
			return data[top];
		}
	}
}

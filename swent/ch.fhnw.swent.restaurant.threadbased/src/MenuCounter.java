import java.util.NoSuchElementException;

/*
 * Created on Mar 8, 2014
 */
/**
 * @author Wolfgang Weck
 */
public final class MenuCounter {
	private int size = 0;
	private Node head = null, tail = null;
	private ServicePerson p;
	private int serviceTime;

	public MenuCounter(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public void registerServicePerson(ServicePerson p) {
		this.p = p;
	}

	public synchronized void arrive(Guest g) {
		if (tail != null) {
			tail.next = new Node(g);
			tail = tail.next;
		} else {
			head = new Node(g);
			tail = head;
		}
		size++;
		if (size == 1) p.wake();
	}

	public synchronized Guest nextGuest() {
		if (head == null) throw new NoSuchElementException();
		Guest g = head.g;
		head = head.next;
		if (head == null) tail = null;
		size--;
		return g;
	}

	public synchronized int queueLength() {
		return size;
	}

	public int serviceTime() {
		return serviceTime;
	}

	public synchronized void close() {
		p.close();
	}

	private static class Node {
		private Guest g;
		private Node next = null;

		Node(Guest g) {
			this.g = g;
		}
	}
}

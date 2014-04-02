public final class EventQueue {
	private static Node head;
	private static int now = 0;

	public static void schedule(Event e, int delay) {
		if (delay < 0) throw new IllegalArgumentException();
		final int dueTime = now + delay;
		if (head != null && dueTime >= head.dueTime) {
			Node n = head;
			while (n.next != null && n.next.dueTime <= dueTime) {
				n = n.next;
			}
			n.next = new Node(e, dueTime, n.next);
		} else {
			head = new Node(e, dueTime, head);
		}
	}

	public static void run() {
		while (head != null) {
			Node n = head;
			head = head.next;
			if (now > n.dueTime) throw new IllegalStateException();
			now = n.dueTime;
			n.event.handle(now);
		}
	}

	public static int simulationTime() {
		return now;
	}

	private static class Node {
		private final Event event;
		private final int dueTime;
		private Node next;

		Node(Event event, int dueTime, Node next) {
			this.event = event;
			this.dueTime = dueTime;
			this.next = next;
		}
	}
}

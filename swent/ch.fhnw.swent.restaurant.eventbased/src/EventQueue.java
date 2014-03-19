public final class EventQueue {
	private static Node head;
	private static int now = 0;

	public static void schedule(Event e, int delay) {
		if (delay < 0) throw new IllegalArgumentException();
		final int dueTime = now + delay;

		

	}

	public static void run() {




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

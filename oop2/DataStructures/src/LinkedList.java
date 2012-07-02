
public class LinkedList {
	public Node head;
	
	public LinkedList(int value) {
		head = new Node(value);
	}
	
	public void add(int value) {
		Node current = head;
		Node toInsert = new Node(value);
		
		while (current.getNext() != null) {
			current = current.getNext();
		}
		
		current.setNext(toInsert);
	}
	
	public int length() {
		//System.out.println(this.head.getValue());
		
		Node current = head;
		int counter = 1;
		
		while (current.getNext() != null) {
			current = current.getNext();
			counter++;
		}
		return counter;
	}
	
}

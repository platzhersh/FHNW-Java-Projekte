package queue;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * LinkedQueue
 * <p/>
 * Insertion in the Michael-Scott nonblocking queue algorithm
 * 
 * @author Brian Goetz and Tim Peierls
 */
public class LinkedQueue<E> {

    public static void main(String[] args) {
        LinkedQueue<String> q = new LinkedQueue<String>();
        q.put("one");
        q.put("two");
        System.out.println(q.get());
        q.put("three");
        q.put("four");
        System.out.println(q.get());
        System.out.println(q.get());
        System.out.println(q.get());
    }

    private static class Node<E> {
        private E item;
        private final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }
    }

    private final AtomicReference<Node<E>> head;
    private final AtomicReference<Node<E>> tail;

    public LinkedQueue() {
        Node<E> dummy = new Node<E>(null, null);
        head = new AtomicReference<Node<E>>(dummy);
        tail = new AtomicReference<Node<E>>(dummy);
    }

    public boolean put(E item) {
        Node<E> newNode = new Node<E>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext != null) {
                    // Queue in intermediate state, advance tail
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    // In quiescent state, try inserting new node
                    if (curTail.next.compareAndSet(null, newNode)) {
                        // Insertion succeeded, try advancing tail
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }

    public E get() {
    	
    	if (null == head.get().next) throw new NoSuchElementException();
    	
    	while (true) {
    		Node<E> h = head.get();
        	Node<E> n = h.next.get();
        	Node<E> nn = null;
        	if (null != n.next) nn = n.next.get();
        	
        	// TODO: fix tail & header references 
        	
    		if (head.compareAndSet(h, n)) {
    			h.item = null;	// GC reasons
    			return n.item;
    		}
    	}
    	
    }

}

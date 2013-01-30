package ch.fhnw.christian.glatthard.algd2;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayHeap heap = new ArrayHeap();
		
		System.out.println(heap.insertKey(11));
		System.out.println(heap.insertKey(12));
		System.out.println(heap.insertKey(13));
		System.out.println(heap.insertKey(14));
		System.out.println(heap.insertKey(193));
		
		System.out.println("Values");
		
		System.out.println(heap.getNodeKey(0));
		System.out.println(heap.getNodeKey(1));
		System.out.println(heap.getNodeKey(2));
		System.out.println(heap.getNodeKey(3));
		System.out.println(heap.getNodeKey(4));
		
		heap.siftDown(0, heap.size());
		
		System.out.println("Values");
		
		System.out.println(heap.getNodeKey(0));
		System.out.println(heap.getLeftChild(0).getKey());
		System.out.println(heap.getRightChild(0).getKey());
	}

}

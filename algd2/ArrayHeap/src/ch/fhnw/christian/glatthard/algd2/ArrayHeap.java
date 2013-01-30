package ch.fhnw.christian.glatthard.algd2;

public class ArrayHeap {

	private ArrayHeapNode[] heap;
	private int elements = 0;
	
	public ArrayHeap() {
		heap = new ArrayHeapNode[10];
	}
	
	public int insertKey(int value) {
		int pos = this.elements;
		this.heap[pos] = new ArrayHeapNode(value);
		this.elements++;
		return pos;
	}
	
	public int siftDown(int pos, int maxPos) {
		
		if ((this.getLeftChildPos(pos) <= maxPos) && (this.getRightChildPos(pos) <= this.size())) {
		
			ArrayHeapNode r = this.heap[pos];
			int cPos;
	
			ArrayHeapNode left = this.getLeftChild(pos);
			ArrayHeapNode right = this.getRightChild(pos);
			
			cPos = right.getKey() > left.getKey() ? this.getRightChildPos(pos) : this.getLeftChildPos(pos);
			
			this.heap[pos] = this.getNode(cPos);
			this.heap[cPos] = r;
			
			return this.siftDown(cPos, maxPos);
		}
		else {
			return pos;
		}

	}
	
	public int size() {
		return elements;
	}
	
	public ArrayHeapNode getNode(int pos) {
		return this.heap[pos];
	}
	
	public int getNodeKey(int pos) {
		return this.heap[pos].getKey();
	}
	
	public int getLeftChildPos(int pos){
		return 2*pos+1;
	}
	public int getRightChildPos(int pos){
		return 2*pos+2;
	}
	
	public ArrayHeapNode getLeftChild(int pos){
		return this.heap[this.getLeftChildPos(pos)];
	}
	public ArrayHeapNode getRightChild(int pos){
		return this.heap[this.getRightChildPos(pos)];
	}
	public ArrayHeapNode getParent(int pos){
		return pos % 2 == 0 ? heap[(pos-2)/2] : heap[(pos-1)/2];
	}
	
}

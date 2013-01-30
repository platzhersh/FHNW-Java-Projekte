package ch.fhnw.christian.glatthard.algd2.graph;

import ch.fhnw.christian.glatthard.algd2.list.DoubleLinkedList;

public class Vertex<T> {
	
	private Integer id;
	public Integer indeg, outdeg;
	private T value;
	private DoubleLinkedList<Edge<T>> list;
	DoubleLinkedList<Edge<T>>.DLLIterator it;
	
	public DoubleLinkedList<Edge<T>> getList() {
		return list;
	}
	
	public Vertex(Integer id, T val) {
		this.id = id;
		this.indeg = this.outdeg = 0;
		this.value = val;
		this.list = new DoubleLinkedList<Edge<T>>();
	}
	
	public Integer indeg() {
		return this.indeg;
	}
	public Integer outdeg() {
		return this.outdeg;
	}
	
	public T getValue() {
		return this.value;
	}
	
	public Integer getID() {
		return this.id;
	}
	
	public void addEdge(Vertex<T> end) {
		Edge<T> e = new Edge<T>(end);
		list.addTail(e);
		this.outdeg++;
		end.indeg++;
		
	}
	
	
	/***
	 * Remove first occurence of Edge where end-point is Vertex with the given ID
	 * @param end
	 */
	public void removeEdge(Vertex<T> end) {
		it = list.new DLLIterator(list);
		Edge<T> e;
		boolean run = true;
		while (it.hasNext() && run == true) {
			e = it.next();
			if (e.getEnd().getID() == end.getID()) 
			{
				it.remove(); 
				run = false; 
				e.getEnd().indeg--; 
				this.outdeg--;
			}
		}
	}
}

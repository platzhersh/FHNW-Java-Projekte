package ch.fhnw.christian.glatthard.algd2.graph;

import ch.fhnw.christian.glatthard.algd2.list.DoubleLinkedList;

public class Graph<V> {

	private DoubleLinkedList<Vertex<V>> list;
	private Integer vId; 
	private Integer size;
	DoubleLinkedList<Vertex<V>>.DLLIterator itr;
	
	public Graph() {
		this.list = new DoubleLinkedList<Vertex<V>>();
		this.vId = 0;
		this.size = 0;
	}
	
	public Integer size() {
		return this.size;		
	}
	
	public void addVertex(V value) {
		Vertex<V> v = new Vertex<V>(this.vId, value);
		list.addTail(v);
		this.vId++;
		this.size++;
		
	}

	public Vertex<V> getVertexByID(Integer id) {
		itr = list.new DLLIterator(list);
		Vertex<V> res = null;
		boolean search = true;
		//System.out.println("List Head: ("+list.getHead().getID()+":"+list.getHead().getValue()+")");
		//System.out.println("List Tail: ("+list.getTail().getID()+":"+list.getTail().getValue()+")");
		while ((itr.hasNext()) && (search == true)) {
			Vertex<V> tmp = itr.next();
			//System.out.println(tmp.getID()+" - "+tmp.getValue());
			//if (itr.hasNext()) System.out.println("has next");
			//else System.out.println("doesn't have next");
			if (tmp.getID() == id) {
				res = tmp;
				search = false;
			}
		}
		return res;
	}
	
	public String toString(){
		String g = new String();
		g = "-----------------------------\n";
		Vertex<V> v;
		itr = list.new DLLIterator(list);
		DoubleLinkedList<Edge<V>>.DLLIterator itrN;
		while (itr.hasNext()) {
			v = itr.next();
			g += ("("+v.getID()+": "+v.getValue().toString()+") in/out: "+v.indeg+"/"+v.outdeg+"\n");
			itrN = v.getList().new DLLIterator(v.getList());
			while (itrN.hasNext()) {
				g += ("  --> ["+itrN.next().getEnd().getID()+"]\n");	
			}
			
		}
		g += "-----------------------------";;
		return g;
	}
}

package ch.fhnw.edu.efficientalgorithms.graph.auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnionFind<T> {

	private ArrayList<TDecorator<T>> content;
	
	public UnionFind() {
		content = new ArrayList<TDecorator<T>>();
	}
	
	public boolean contains(T t) {
		boolean res = false;
		for (TDecorator<T> td : content) {
			res = td.getValue().equals(t) || res;
		}
		return res;
	}
	
	public boolean add(T t) {
		if (!contains(t)) {
			content.add(new TDecorator<T>(t));
			return true;
		}
		return false;
	}
	
	public TDecorator<T> find(T t) {
		for (TDecorator<T> td : content) {
			if (td.getValue().equals(t)) return td;
		}
		return null;
	}
	
	public List<T> toList() {
		ArrayList<T> list = new ArrayList<T>();
		for (TDecorator<T> td : content) {
			list.add(td.getValue());
		}
		return list;
	}
	
	public boolean connected(T t1, T t2) {
		TDecorator<T> td1 = find(t1);
		TDecorator<T> td2 = find(t2);
		if (td1 != null && td2 != null) {
			for (TDecorator<T> td : td1.getConnections()) {
				if (td.getValue().equals(t2)) return true;
			}
		}
		return false;
	}
	
	public void connect(T t1, T t2) {
		
	}
	
	
	public class TDecorator<S> {
		private S inner;
		private ArrayList<TDecorator<S>> connections;
		
		public TDecorator(S s) {
			inner = s;
			connections = new ArrayList<TDecorator<S>>();
		}
		
		public S getValue() {
			return inner;
		}
		
		public ArrayList<TDecorator<S>> getConnections() {
			return connections;
		}
		
		public boolean connectedTo(TDecorator<S> t) {
			return connections.contains(t);
		}

	}
}

package ch.fhnw.edu.efficientalgorithms.graph.auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/***
 * 
 * Used in Kruskal algorithm
 * current version is very inefficient, try to improve by using Weighted quick-union with path compression
 * see: https://www.cs.princeton.edu/~rs/AlgsDS07/01UnionFind.pdf
 * see: http://algs4.cs.princeton.edu/15uf/UF.java.html
 * 
 * @author chregi
 *
 * @param <T>
 */
public class UnionFind<T> {

	private ArrayList<TDecorator<T>> content;
	
	public UnionFind() {
		content = new ArrayList<TDecorator<T>>();
	}
	
	public synchronized boolean contains(T t) {
		boolean res = false;
		for (TDecorator<T> td : content) {
			res = td.getValue().equals(t) || res;
		}
		return res;
	}
	
	public synchronized boolean add(T t) {
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
	
	public synchronized boolean connected(T t1, T t2) {
		TDecorator<T> td1 = find(t1);
		TDecorator<T> td2 = find(t2);
		return td1.connectedTo(td2);
	}
	
	public synchronized void connect(T t1, T t2) {
		TDecorator<T> td1 = find(t1);
		TDecorator<T> td2 = find(t2);
		
		// connect all vertices connected to td1 to td2
		for (int i = 0; i < td1.getConnections().size(); i++) {
			TDecorator<T> td1V = td1.getConnections().get(i);
			td1V.connectTo(td2);
			td2.connectTo(td1V);
			for (int j = 0; j < td2.getConnections().size(); j++) {
				td2.getConnections().get(j).connectTo(td1);
				td1.connectTo(td2.getConnections().get(j));
				td2.getConnections().get(j).connectTo(td1V);
				td1V.connectTo(td2.getConnections().get(j));
			}
		}		

		// connect td1 to td2
		td1.connectTo(td2);
		td2.connectTo(td1);
		
		
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
		
		public synchronized ArrayList<TDecorator<S>> getConnections() {
			return connections;
		}
		
		public synchronized boolean connectTo(TDecorator<S> t) {
			if (!connections.contains(t)) {
				connections.add(t);
				return true;
			}
			return false;
		}
		
		public synchronized boolean connectedTo(TDecorator<S> t) {
			return connections.contains(t);
		}

	}
}

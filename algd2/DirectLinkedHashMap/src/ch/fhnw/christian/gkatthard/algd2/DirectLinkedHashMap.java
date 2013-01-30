package ch.fhnw.christian.gkatthard.algd2;

import java.util.LinkedList;

public class DirectLinkedHashMap<T> implements HashMap<T>{
	
	private int m_size;
	private LinkedList<Element> list;
	@Override
	public int getSize() {
		return m_size;
	}
	
	public DirectLinkedHashMap() {
		list = new LinkedList<Element>();
	}

	@Override
	public boolean contains(int key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T get(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(int key, T data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int key) {
		// TODO Auto-generated method stub
		
	}

	private class Element {
		private T m_data;
		private Integer m_key;
		public Element(int key, T data) {
			m_data = data;
			m_key = key;
		}
		public boolean equals(Object o) {
			return m_key.equals(((Element)o).m_key);
		}
	}

	
}

package ch.fhnw.christian.glatthard.algd2;

public class MyHashArray<T> {
	private T[] m_HashTable;
	private int m_m;
	
	public MyHashArray(int size) {
		m_m = size;
		m_HashTable = (T[])new Object[m_m];
	}
	
	public void einfuegen(int key, T obj) {
		m_HashTable[h(key)] = obj;
	}
	
	private int h (int key) {
		return key % m_m;
	}
	
	public void entfernen (int key){
		m_HashTable[h(key)] = null;
	}
	
	public T suchen (int key) {
		return m_HashTable[h(key)];
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < m_m; i++) {
			output.append("[Index \""+i+"\", Object \""+m_HashTable[i]+"\"]; \n");
		}
		output.delete(output.length() - 3, output.length());
		return output.toString();
	}
}

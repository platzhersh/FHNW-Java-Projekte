import java.util.concurrent.atomic.AtomicReferenceArray;

public class LockFreeArrayList {
	
	//ArrayList<AtomicReference<String>> l;
	AtomicReferenceArray<String> l2;
	
	public LockFreeArrayList(){
		this(10);
	}
	
	public LockFreeArrayList(int size) {
		//l = new ArrayList<>();
		l2 = new AtomicReferenceArray<String>(10);
	}
	
	/***
	 * 
	 * Setzt String an Position i in Arraylist und gibt alten Wert zurück
	 * 
	 * @param i
	 * @param neu
	 * @return 
	 */
	public String getAndSet(int i, String neu) {
//		
//		if (i > l.size()) throw new IllegalArgumentException();
//		
//		while (true) {
//			AtomicReference<String> oldRef = l.get(i);
//			String oldString = oldRef.get();
//			if (!oldRef.compareAndSet(oldString, neu)) continue;
//			return oldString;
//		}
		
		while(true) {
			String old = l2.get(i);
			if (!l2.compareAndSet(i, old, neu)) continue;
			return old;
		}
	}
	
	public String get(int i) {
		if (i > l2.length()) throw new ArrayIndexOutOfBoundsException();
		return l2.get(i);
	}
	
	public void swap(int i, int j) {
		while (true) {
			String oldi = l2.get(i);
//			String oldj = l2.get(j);
//			
//			if (l2.compareAndSet(i, oldi, oldj)) {
//				if (!l2.compareAndSet(j, oldj, oldi)) continue;
//				return;
//			}
			
			if (l2.compareAndSet(i, oldi, getAndSet(j, oldi))) return;
			// else redo getAndSet ( wie au immer...)
			
		}
	}
	
	

}

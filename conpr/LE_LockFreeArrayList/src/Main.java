
public class Main {
	
	public static void main(String[] args) {
		LockFreeArrayList lfl = new LockFreeArrayList();
		System.out.println(lfl.getAndSet(0, "diniMueter"));
		System.out.println(lfl.getAndSet(0, "diniMueter 2"));
		
		System.out.println(lfl.getAndSet(1, "dinVater"));
		System.out.println(lfl.getAndSet(1, "dinVater 2"));
		
		lfl.swap(0,1);
		System.out.println(lfl.get(0));
		System.out.println(lfl.get(1));
		
		
	}
	
}

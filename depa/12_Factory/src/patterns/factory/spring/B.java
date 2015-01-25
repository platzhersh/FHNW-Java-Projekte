package patterns.factory.spring;

public class B {
	private A a;
	
	public B() {
		System.out.println("B() called");
	}


	public A getA() {
		return a;
	}

	public void setA(A a) {
		System.out.println("B.setA called");
		this.a = a;
	}
	
	

}

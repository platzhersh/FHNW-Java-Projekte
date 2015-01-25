package patterns.factory.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	private static ClassPathXmlApplicationContext ctx;

	static {
		ctx = new ClassPathXmlApplicationContext("patterns/factory/spring/context.xml");
	}

	public static final void main(String[] args) {
		A a = (A)ctx.getBean("beanA");
		System.out.println(a.getValue());

		B b = a.getB();
		A a2 = b.getA();
		System.out.println("a =               " + a);
		System.out.println("a.getB().getA() = " + a2);
	}

}

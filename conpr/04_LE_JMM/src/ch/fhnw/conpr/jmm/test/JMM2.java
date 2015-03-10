package ch.fhnw.conpr.jmm.test;

public class JMM2 {
	private static class Person {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	private static volatile Person p = null;

	public static void main(String[] args) {
		new Thread("T1") {
			public void run() {
				synchronized(this) {
					p = new Person();
					p.setName("Meier");
				}
			}
		}.start();

		new Thread("T2") {
			public void run() {
				if (p != null) {
					System.out.println(p.getName());
				}
			}
		}.start();
	}
}

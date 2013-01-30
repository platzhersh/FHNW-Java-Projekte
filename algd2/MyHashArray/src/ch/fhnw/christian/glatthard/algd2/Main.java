package ch.fhnw.christian.glatthard.algd2;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyHashArray<String> myHashArray = new MyHashArray<String>(7);
		myHashArray.einfuegen(1, "eins");
		myHashArray.einfuegen(55, "fuenfundfuenfzig");
		myHashArray.einfuegen(87, "siebenundachtzig");
		myHashArray.einfuegen(10, "zehn");
		System.out.println(myHashArray);
		System.out.println(myHashArray.suchen(1));
		myHashArray.entfernen(1);
		System.out.println(myHashArray.suchen(1));
	}

}

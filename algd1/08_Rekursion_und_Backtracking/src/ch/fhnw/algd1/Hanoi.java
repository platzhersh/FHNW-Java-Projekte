package ch.fhnw.algd1;
import java.util.List;
import java.util.Stack;


public class Hanoi {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Hanoi h = new Hanoi(4);
		h.move(4,'a','b','c');

	}
	
	Stack<Integer> a, b, c;
	
	/***
	 * Sets up a Tower of Hanoi Game
	 * @param n number of plates
	 */
	public Hanoi(int n) {
		a = new Stack<Integer>();
		b = new Stack<Integer>();
		c = new Stack<Integer>();
		while (n > 0) {
			a.push(n--);
		}
	}
	
	public Stack<Integer> mapChar2Stack(char c) throws Exception {
		switch (c) {
			case 'a': return this.a; 
			case 'b': return this.b;
			case 'c': return this.c;
			default: throw new Exception("Invalid Char.");
		}
	}
	
	public void printStacks() {
		System.out.println("A: "+a.toString());
		System.out.println("B: "+b.toString());
		System.out.println("C: "+c.toString());
		System.out.println("-----------------------------");
	}
	
	public void move (int n, char from, char to, char via) throws Exception {
		Stack<Integer> f = mapChar2Stack(from);
		Stack<Integer> t = mapChar2Stack(to);
		Stack<Integer> v = mapChar2Stack(via);
	
		printStacks();
		
		if (n < 2) {
			t.push(f.pop()); printStacks();
		} else if (n < 3) {
			v.push(f.pop()); printStacks();
			t.push(f.pop()); printStacks();
			t.push(v.pop()); printStacks();
		} else if (n == 3) {
			move(2,from,via,to);
			move(1,from,to,via);
			move(2,via,to,from);
		} else {
			// TODO
		}
	}

}

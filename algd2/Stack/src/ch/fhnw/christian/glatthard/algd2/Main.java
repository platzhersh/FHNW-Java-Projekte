package ch.fhnw.christian.glatthard.algd2;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(1);
		System.out.println(stack.top());
		stack.push(2);
		System.out.println(stack.top());
		stack.push(3);
		System.out.println(stack.top());
		
		System.out.println(stack.isEmpty());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.isEmpty());
	}

}

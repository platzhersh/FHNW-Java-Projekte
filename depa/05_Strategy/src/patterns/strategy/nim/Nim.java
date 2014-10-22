package patterns.strategy.nim;

import java.util.Random;
import java.util.Scanner;

public class Nim {

	public static void main(String[] args) {
		int numberOfStones = 20;
		Scanner s = new Scanner(System.in);
		Player player1 = new HumanPlayer("Meier", s);
		Player player2 = new HumanPlayer("Müller", s);
//		Player player1 = new ArtificialIntelligentPlayer("iRobot1");
//		Player player2 = new ArtificialIntelligentPlayer("iRobot2");

		Player currentPlayer = player1;
		while(numberOfStones > 0) {
			int n = currentPlayer.makeMove(numberOfStones);
			if(n < 1 || n > 3 || n > numberOfStones) throw new IllegalStateException();
			
			numberOfStones = numberOfStones - n;
			if(numberOfStones == 0) {
				System.out.println(currentPlayer.getName() + " lost. Sorry.");
			}
			// switch players
			currentPlayer = (currentPlayer == player1)? player2 : player1;
		}
		
		s.close();
	}

	interface Player {
		String getName();
		int makeMove(int numberOfStones);
	}

	static class HumanPlayer implements Player {
		private String name;
		private Scanner s;
		public HumanPlayer(String name, Scanner s) {
			this.name = name;
			this.s = s;
		}
		public int makeMove(int numberOfStones) {
			System.out.println(name +  " make your next move");
			System.out.println("on the table are "+numberOfStones + " stones");
				int n = s.nextInt();
				while (n < 1 || n > 3 || n > numberOfStones) {
					System.out.println("Input invalid. Make your next move");
					n = s.nextInt();
				}
				return n;
		}
		public String getName() {
			return name;
		}
	}

	static class RandomPlayer implements Player {
		public int makeMove(int numberOfStones) {
			Random r = new Random();
			int n = 1 + r.nextInt(Math.min(3, numberOfStones));
			System.out.println("Random takes " + n +  " stones");
			return n;
		}
		public String getName() {
			return "Random";
		}
	}

	static class ArtificialIntelligentPlayer implements Player {
		private String name;
		public ArtificialIntelligentPlayer(String name) {
			this.name = name;
		}
		public int makeMove(int numberOfStones) {
			System.out.println(name +  " make your next move");
			System.out.println("on the table are "+numberOfStones + " stones");
			int n = (numberOfStones-1) % 4;
			if(n == 0) n = 1;
			System.out.println("iRobot takes " + n +  " stones");
			return n;
		}
		public String getName() {
			return name;
		}
	}
}

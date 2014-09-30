package uebung1.warnsdorf;


public class ChessBoardField implements Comparable<ChessBoardField>{
	int i;
	int j;
	int count;
	
	boolean visited;
	
	/* Konstruktoren */
	
	public ChessBoardField() {
		visited = false;
	}
	
	public ChessBoardField(int cordI, int cordJ) {
		visited = false;
		i = cordI;
		j = cordJ;
	}
	
	public ChessBoardField(int cordI, int cordJ, int value) {
		visited = false;
		i = cordI;
		j = cordJ;
		count = value;
	}
	
	
	/* Getter & Setter */
	
	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int c) {
		count = c;
	}
	
	public void setVisited(boolean v){
		visited = v;
	}
	
	public boolean visited(){
		return visited;
	}

	@Override
	public int compareTo(ChessBoardField o) {
		if (this.count > o.count) return 1;
		if (this.count == o.count) return 0;
		return -1;
	}
}

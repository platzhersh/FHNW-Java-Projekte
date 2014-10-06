package uebung1.sudoko;

public class SudokuController {

	public SudokuModel model;
	public SudokuView view;
	public boolean running;
	
	public SudokuController() {
		model = new SudokuModel(9);
		view = new SudokuView(model, this);
	}
	
	public void solve() {
		
	}
	
	public boolean solve(int i, int j) {
		
		return false;
	}
	
}

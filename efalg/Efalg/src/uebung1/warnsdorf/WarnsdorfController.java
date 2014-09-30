package uebung1.warnsdorf;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WarnsdorfController {


	public WarnsdorfModel model;
	public WarnsdorfView view;
	public boolean running;
	
	public WarnsdorfController(int size) {
		running = false;
		model = new WarnsdorfModel(size);
		view = new WarnsdorfView(model,this);
	}
	
	public void start(int i, int j) {
		running = true;
		boolean result = goOn(i,j);
	}
	
	public boolean goOn(int i, int j) {
		view.fields[i][j].setBackground(new Color(120,120,200));
		model.board.get(i, j).setVisited(true);
		List<ChessBoardField> list = model.board.getNeighbors(i, j);
		updateNeighbors(list);
		
		view.validate();
		view.repaint();
		
		// sort
		Collections.sort(list, new Comparator<ChessBoardField>() {

            public int compare(ChessBoardField cbf1, ChessBoardField cbf2) 
            {
                return cbf1.compareTo(cbf2);
            }
        });
		
		for (int k = 0; k < list.size(); k++) {
			ChessBoardField c = list.get(k);
			if (goOn(c.getI(),c.getJ())) return true;
		}

		// remove highlight
		Color background = (i+j)%2==0 ? new Color(255,255,255) : new Color(0,0,0);
		view.fields[i][j].setBackground(background);
		model.board.get(i, j).setVisited(false);
		return false;
		
	}
	
	
	
	public void updateNeighbors(List<ChessBoardField> list) {		
		for (ChessBoardField c : list) {
			c.setCount(c.getCount()-1);
			view.fields[c.getI()][c.getJ()].updateText();
		}
	}
	
	public void generateChessBoard(int size) {
		model.generateChessBoard(size);
		view.generateChessBoard();
		view.validate();
		view.repaint();
		running = false;
	}
	
	
	

}

package uebung1.warnsdorf;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import uebung1.warnsdorf.WarnsdorfView.ChessBoardFieldLabel;

public class ChessBoardFieldClickListener implements MouseListener {

	WarnsdorfController controller;
	
	public ChessBoardFieldClickListener(WarnsdorfController c) {
		controller = c;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e);
		ChessBoardFieldLabel lbl = (ChessBoardFieldLabel) e.getSource();
		if(!controller.running) controller.start(lbl.getI(), lbl.getJ());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

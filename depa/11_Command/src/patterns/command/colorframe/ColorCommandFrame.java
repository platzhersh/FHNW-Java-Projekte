package patterns.command.colorframe;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import patterns.command.RedAction;
import patterns.command.YellowAction;

public class ColorCommandFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6822331384242299913L;

	public static void main(String[] args) {
		JFrame f = new ColorCommandFrame();
		f.setSize(400, 300);
		f.setVisible(true);
	}

	private JPanel coloredPanel = new JPanel();

	public ColorCommandFrame() {
		super("ColorCommandFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initButtons();
		initPopupMenu();
		initMenu();
	}

	public void initButtons() {
		JButton yellowButton = new JButton(new YellowAction(coloredPanel));
		JButton redButton = new JButton(new RedAction(coloredPanel));
		coloredPanel.add(yellowButton);
		coloredPanel.add(redButton);
		add(coloredPanel);
	}

	public void initPopupMenu() {
		JMenuItem yellowPopupItem = new JMenuItem(new YellowAction(coloredPanel));
		JMenuItem redPopupItem = new JMenuItem(new RedAction(coloredPanel));
		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(yellowPopupItem);
		popupMenu.add(redPopupItem);

		coloredPanel.addMouseListener(new MouseAdapter() {
			public @Override
			void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			public @Override
			void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	public void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu colorMenu = new JMenu("Color");
		colorMenu.setMnemonic(KeyEvent.VK_C);
		JMenuItem yellowMenuItem = new JMenuItem(new YellowAction(coloredPanel));
		JMenuItem redMenuItem = new JMenuItem(new RedAction(coloredPanel));
		colorMenu.add(yellowMenuItem);
		colorMenu.add(redMenuItem);
		menuBar.add(colorMenu);
		setJMenuBar(menuBar);
	}
}

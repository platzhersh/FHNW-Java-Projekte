package patterns.command;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class YellowAction extends AbstractAction {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6615589056037868933L;

	private JPanel coloredPanel;
	
	public static final String name = "yellow";
	public static final String description = "yellow";
	//public static final ImageIcon icon = new ImageIcon("yellow_bullet.gif");
	private ImageIcon icon = new ImageIcon(this.getClass().getResource("yellow_bullet.gif"));
	
	public YellowAction(JPanel jp) {
		super(name);
        putValue(SHORT_DESCRIPTION, description);
        putValue(SMALL_ICON, icon);
        
        coloredPanel = jp;		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		coloredPanel.setBackground(Color.YELLOW);
	}

}

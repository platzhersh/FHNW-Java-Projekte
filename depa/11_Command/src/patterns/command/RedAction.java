package patterns.command;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class RedAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 827654040108358463L;

	private JPanel coloredPanel;
	
	public static final String name = "red";
	public static final String description = "red";
	//public static final ImageIcon icon = new ImageIcon("red_bullet.gif");
	private ImageIcon icon = new ImageIcon(this.getClass().getResource("red_bullet.gif"));

	
	public RedAction(JPanel jp) {
		super(name);
        putValue(SHORT_DESCRIPTION, description);
        putValue(SMALL_ICON, icon);
        
        coloredPanel = jp;		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		coloredPanel.setBackground(Color.RED);
	}

}

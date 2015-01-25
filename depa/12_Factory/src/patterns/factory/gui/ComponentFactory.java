package patterns.factory.gui;

public interface ComponentFactory {
	
	/** Base type for all component interfaces. */
	interface Component {}

	/** A button with a label and an action listener. */
	interface Button extends Component { }
	
	/** A label with a text. */
	interface Label extends Component { } 

	/** A text field where the text can be read and set. */
	interface Field extends Component {
		String getText();
		void setText(String text);
	}

	/** A program frame which controls components in a grid layout. */
	interface Frame{
		void setVisible(boolean visible);
		void add(Component c);
		void setGrid(int w, int h);	
	}
	
	/** The listener interface for receiving action events.	 */
	public interface ActionListener  {
	    public void actionPerformed(Component source);
	}

	
	/** Creates a button with a given label and listener. */
	Button createButton(String label, ActionListener listener);
	
	/** Creates a label with a given name. */
	Label createLabel(String label);
	
	/** Creates a text field. The returned result implements interface Field. */
	Field createField(int width, boolean enabled);
	
	/** Creates a frame with a given title. */	
	Frame createFrame(String title);
}


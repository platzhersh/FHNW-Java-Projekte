package patterns.observer.swing;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.awt.*;

// Remark:
// Since the area is bound to a TextField-Document, the CR is suppressed.


public class TextFieldDemo extends JFrame {
	public static void main(String[] args){
		JFrame f = new TextFieldDemo();
		f.setSize(300,300);
		f.setVisible(true);
	}

	TextFieldDemo(){
		JTextField fld1 = new JTextField(10);
		JTextField fld2 = new JPasswordField(10);
		JTextArea  area  = new JTextArea(5, 20);
		area.setLineWrap(true);
		
		fld2.setDocument(fld1.getDocument());
		area.setDocument(fld1.getDocument());

		JTextField fld3 = new CounterField(fld1.getDocument());

		
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(fld1);
		c.add(fld2);
		c.add(area);
		c.add(fld3);
	}
	
}

class CounterField extends JTextField {
	CounterField(final Document d){
		super(4);
		setEditable(false);
		d.addDocumentListener(
			new DocumentListener(){
				public void changedUpdate(DocumentEvent e){
					setText("" + d.getLength());
				}
				public void insertUpdate(DocumentEvent e){
					setText("" + d.getLength());
				}
				public void removeUpdate(DocumentEvent e){
					setText("" + d.getLength());
				}
			}
		);
	}
}
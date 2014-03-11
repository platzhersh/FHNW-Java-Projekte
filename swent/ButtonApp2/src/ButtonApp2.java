/*
 * Created on Mar 3, 2009
 */
import java.awt.Button;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ButtonApp2 extends Frame implements ActionListener {
	private TextField value;
	private Button clear;
	private Button duplicate;
	private Button add3;

	public static void main(String[] args) {
		Frame f = new ButtonApp2();
		f.setSize(200, 100);
		f.setVisible(true);
	}

	public ButtonApp2() {
		setTitle("Button Example");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setLayout(new FlowLayout());
		value = new TextField(20);
		value.setText("0");
		add(value);
		init();
	}

	private int getValue() {
		return Integer.parseInt(value.getText());
	}

	private void setValue(int x) {
		value.setText("" + x);
	}

	private void init() {
		clear = new Button("Clear");
		clear.addActionListener(this);
		add(clear);
		duplicate = new Button("Duplicate");
		duplicate.addActionListener(this);
		add(duplicate);
		add3 = new Button("Add 3");
		add3.addActionListener(this);
		add(add3);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.toString());
		Button b = (Button) e.getSource();
		System.out.println(b.getLabel());
		switch (b.getLabel()) {
		case "Clear":
			setValue(0);
			break;
		case "Duplicate":
			setValue(getValue()*2);
			break;
		case "Add 3":
			setValue(getValue()+3);
			break;
		}
	}

}

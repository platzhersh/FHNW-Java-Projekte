/*
 * Created on Mar 3, 2009
 */
import java.awt.Button;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ButtonApp extends Frame {
	private TextField value;
	private Button clear;
	private Button duplicate;
	private Button add3;

	public static void main(String[] args) {
		Frame f = new ButtonApp();
		f.setSize(200, 100);
		f.setVisible(true);
	}

	public ButtonApp() {
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
		clear = new ClearButton("Clear", this);
		add(clear);
		duplicate = new DuplicateButton("Duplicate", this);
		add(duplicate);
		add3 = new Add3Button("Add 3", this);
		add(add3);
	}
	
	private class ClearButton extends Button {
		ButtonApp bat;
		
		public boolean action(Event evt, Object what) {
			System.out.println(evt.toString());
			bat.setValue(0);
			return true;
		}
		public ClearButton(String name, ButtonApp b) {
			super(name);
			this.bat = b;
		}
	}
	private class DuplicateButton extends Button {
		ButtonApp bat;
		
		public boolean action(Event evt, Object what) {
			System.out.println(evt.toString());
			bat.setValue(bat.getValue()*2);
			return true;
		}
		public DuplicateButton(String name, ButtonApp b) {
			super(name);
			this.bat = b;
		}
	}
	private class Add3Button extends Button {
		ButtonApp bat;
		
		public boolean action(Event evt, Object what) {
			System.out.println(evt.toString());
			bat.setValue(bat.getValue()+3);
			return true;
		}
		public Add3Button(String name, ButtonApp b) {
			super(name);
			this.bat = b;
		}
	}
}

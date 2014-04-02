/*
 * Created on Mar 3, 2009
 */
package ch.fhnw.swent.osgi.buttons;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import ch.fhnw.swent.osgi.logger.api.Logger;

@SuppressWarnings("serial")
public class ButtonApp2 extends Frame {
	private TextField value;

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

	public ButtonApp2(Bundle bundle) {
		final Bundle b = bundle;
		setTitle("Button Example");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				try {
					b.stop();
				}
				catch (BundleException ex) {
					ex.printStackTrace();
				}
			}
		});
		setLayout(new FlowLayout());
		value = new TextField(20);
		value.setText("0");
		add(value);
		init();
//		startLogger(bundle);
	}

	@SuppressWarnings("unused")
	private void startLogger(Bundle bundle) {
		Bundle[] b = bundle.getBundleContext().getBundles();
		int i = 0;
		while (i < b.length
				&& !b[i].getSymbolicName().equals("ch.fhnw.swent.osgi.logger")) {
			i++;
		}
		if (i < b.length) {
			try {
				b[i].start();
			}
			catch (BundleException e) {
				e.printStackTrace();
			}
		}
	}

	private int getValue() {
		return Integer.parseInt(value.getText());
	}

	private void setValue(int x) {
		value.setText("" + x);
	}

	private void init() {
		LogListener l = new LogListener();
		Button b = new Button("Clear");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setValue(0);
			}
		});
		b.addActionListener(l);
		add(b);
		b = new Button("Duplicate");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setValue(2 * getValue());
			}
		});
		b.addActionListener(l);
		add(b);
		b = new Button("Add 3");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setValue(3 + getValue());
			}
		});
		b.addActionListener(l);
		add(b);
	}
}

class LogListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		Logger.writeMsg(((Button)e.getSource()).getLabel() + " pressed");
	}
}

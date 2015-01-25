package patterns.factory.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FactoryAWT implements ComponentFactory {
	
	public Button createButton(String label, ActionListener listener) {
		ButtonAWT b = new ButtonAWT(label);
		b.addActionListener(listener);
		return b;
	}

	public Label createLabel(String label) {
		return new LabelAWT(label);
	}

	public Field createField(int width, boolean enabled) {
		return new FieldAWT(width, enabled);
	}

	public Frame createFrame(String title) {
		FrameAWT f = new FrameAWT(title);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		return f;
	}

	static class ButtonAWT extends java.awt.Button implements ComponentFactory.Button {

		ButtonAWT(String label) {
			super(label);
		}
		
		public void addActionListener(final ActionListener listener) {
			this.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					listener.actionPerformed(ButtonAWT.this);
				}
			});
		}

	}

	static class FieldAWT extends java.awt.TextField implements
			ComponentFactory.Field {

		FieldAWT(int width, boolean enabled) {
			super(width);
			setEnabled(enabled);
		}

	}

	static class LabelAWT extends java.awt.Label implements
			ComponentFactory.Label {

		LabelAWT(String text) {
			super(text);
		}

	}

	static class FrameAWT extends java.awt.Frame implements
			ComponentFactory.Frame {
		FrameAWT(String label) {
			super(label);
		}

		public void add(Component c) {
			add((java.awt.Component) c);
		}

		@Override
		public void setVisible(boolean visible) {
			pack();
			super.setVisible(visible);
		}

		public void setGrid(int w, int h) {
			setLayout(new GridLayout(4, 2));
		}
	}

	static {
		CurrentFactory.setFactory(new FactoryAWT());
	}
}

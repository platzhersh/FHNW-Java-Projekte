package patterns.factory.gui;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;

public class FactorySwing implements ComponentFactory {
	public Button createButton(final String label, ActionListener listener) {
		ButtonSwing b = new ButtonSwing(label);
		b.addActionListener(listener);
		return b;
	}

	public Label createLabel(final String label) {
		return new LabelSwing(label);
	}

	public Field createField(int width, boolean enabled) {
		return new FieldSwing(width, enabled);
	}

	public Frame createFrame(String title) {
		return new FrameSwing(title);
	}

	static class ButtonSwing extends javax.swing.JButton implements
			ComponentFactory.Button {

		ButtonSwing(String label) {
			super(label);
		}
		
		public void addActionListener(final ActionListener listener) {
			this.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					listener.actionPerformed(ButtonSwing.this);
				}
			});
		}

	}

	class LabelSwing extends javax.swing.JLabel implements
			ComponentFactory.Label {

		LabelSwing(String label) {
			super(label);
		}
	}

	static class FieldSwing extends javax.swing.JTextField implements
			ComponentFactory.Field {

		public FieldSwing(int width, boolean enabled) {
			super(width);
			setEnabled(enabled);
		}
	}

	static class FrameSwing extends javax.swing.JFrame implements
			ComponentFactory.Frame {

		FrameSwing(String title) {
			super(title);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

		public void add(Component c) {
			add((java.awt.Component) c);
		}

		public void setGrid(int w, int h) {
			LayoutManager m = new GridLayout(w, h);
			if (isRootPaneCheckingEnabled()) {
				getContentPane().setLayout(m);
			} else {
				super.setLayout(m);
			}
		}

		public @Override
		void setVisible(boolean visible) {
			super.pack();
			super.setVisible(visible);
		}
	}

	static {
		CurrentFactory.setFactory(new FactorySwing());
	}

}

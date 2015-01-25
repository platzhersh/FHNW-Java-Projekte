package patterns.factory.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FactorySWT implements ComponentFactory {

	static interface ComponentSWT {
		void createPeer(Shell shell);
	}

	static class ButtonSWT implements Button, ComponentSWT {
		String label;
		ActionListener listener;

		ButtonSWT(String label, final ActionListener listener) {
			this.label = label;
			this.listener = listener;
		}

		public void createPeer(Shell shell) {
			org.eclipse.swt.widgets.Button b = new org.eclipse.swt.widgets.Button(
					shell, SWT.PUSH);
			b.setText(label);
			b.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					listener.actionPerformed(ButtonSWT.this);
				}

				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}
	}

	public Button createButton(String label, ActionListener listener) {
		return new ButtonSWT(label, listener);
	}

	static class LabelSWT implements Label, ComponentSWT {
		private String label;

		LabelSWT(String label) {
			this.label = label;
		}

		public void createPeer(Shell shell) {
			org.eclipse.swt.widgets.Label l = new org.eclipse.swt.widgets.Label(
					shell, SWT.NULL);
			l.setText(label);
		}
	}

	public Label createLabel(String label) {
		return new LabelSWT(label);
	}

	static class FieldSWT implements Field, ComponentSWT {
		private boolean enabled;
		private Text t;

		FieldSWT(boolean enabled) {
			this.enabled = enabled;
		}

		public void createPeer(Shell shell) {
			t = new Text(shell, SWT.SINGLE);
			t.setEnabled(enabled);
		}

		public String getText() {
			return t.getText();
		}

		public void setText(String text) {
			t.setText(text);
		}
	}

	public Field createField(int width, boolean enabled) {
		return new FieldSWT(enabled);
	}

	public Frame createFrame(String title) {
		final Display display = new Display();
		final Shell shell = new Shell(display);

		class ShellSWT implements Frame {
			Shell s;

			ShellSWT(Shell s) {
				this.s = s;
			}

			public void setVisible(boolean visible) {
				s.setSize(180, 135);
				s.open();
				while (!s.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
			}

			public void add(Component c) {
				if (c instanceof ComponentSWT) {
					((ComponentSWT) c).createPeer(shell);
				}
			}

			public void setGrid(int w, int h) {
				GridLayout g = new GridLayout();
				g.numColumns = h;
				s.setLayout(g);
			}
		}

		Frame f = new ShellSWT(shell);
		shell.setText(title);
		return f;
	}

	static {
		CurrentFactory.setFactory(new FactorySWT());
	}

}

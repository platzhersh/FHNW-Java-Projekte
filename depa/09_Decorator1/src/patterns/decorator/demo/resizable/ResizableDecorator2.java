package patterns.decorator.demo.resizable;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JComponent;

public class ResizableDecorator2 extends JComponent {

	private final JComponent child;

	private Button button = new Button(); // heavy-weight AWT button
	private boolean minimum = false;
	private Rectangle r;

	public ResizableDecorator2(JComponent component) {
		this.child = component;
		super.setLayout(new BorderLayout());
		super.add(child);
		if(child.getLayout() != null){
			System.out.println("replace layout manager");
			child.setLayout(new ResizableLayoutManager(child.getLayout(), button));
		}
		else {
			child.setLayout(null); // warum das nötig ist ist nicht klar.
		}
		
		button.setBounds(0, 0, 8, 8);
		child.add(button, 0); // add at beginning (painted last)
		button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_actionPerformed(e);
			}
		});
	}

    @Override
	public void setLayout(LayoutManager mgr) {
    	child.setLayout(new ResizableLayoutManager(mgr, button));
    }
    
	@Override
	public Component add(Component comp) {
		return child.add(comp);
	}

	@Override
	public Component add(Component comp, int index) {
		return child.add(comp, index);
	}

	@Override
	public void add(Component comp, Object constraints, int index) {
		child.add(comp, constraints, index);
	}

	@Override
	public void add(Component comp, Object constraints) {
		child.add(comp, constraints);
	}

	@Override
	public Component add(String name, Component comp) {
		return child.add(name, comp);
	}
	

	void button_actionPerformed(ActionEvent e) {
		if (minimum) {
			this.setBounds(r);
		} 
		else {
			r = this.getBounds();
			this.setBounds(r.x, r.y, 8, 8);
		}
		minimum = !minimum;
	}
	
	static class ResizableLayoutManager implements LayoutManager {
		private Component button;
		private LayoutManager inner;
		ResizableLayoutManager(LayoutManager inner, Component button){
			this.inner = inner;
			this.button = button;
		}
		
		@Override
		public void addLayoutComponent(String name, Component comp) {
			if(comp != button)
				inner.addLayoutComponent(name, comp);
		}
		
		@Override
		public void removeLayoutComponent(Component comp) {
			if(comp != button)
				inner.removeLayoutComponent(comp);
		}
		
		@Override
		public void layoutContainer(Container parent) {
			System.out.println("layoutContainer");
			parent.remove(button);
			inner.layoutContainer(parent);
			parent.add(button, 0);
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			System.out.println("minimumLayoutSize");
			parent.remove(button);
			Dimension d =  inner.minimumLayoutSize(parent);
			parent.add(button, 0);
			return d;
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			System.out.println("preferredLayoutSize");
			parent.remove(button);
			Dimension d = inner.preferredLayoutSize(parent);
			parent.add(button, 0);
			return d;
		}
	}
}
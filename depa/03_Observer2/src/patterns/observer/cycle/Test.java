package patterns.observer.cycle;

import java.awt.Color;

public class Test {
	public static void main(String[] args) throws Exception {
		final ColorModel model = new ColorModel(Color.black);
		final RedScrollbar sb = new RedScrollbar(model);
		
		sb.addObserver(new Observer(){
			@Override
			public void update(Observable source, Object arg) {
				System.out.println("someone changed the scrollbar value => adjust the color model");
				Color current = model.getColor();
				model.setColor(new Color((int)arg, current.getGreen(), current.getBlue()));
			}}
		);
		

		model.setColor(Color.gray);
		System.out.println("model red value = " + model.getColor().getRed());
		System.out.println("scrollbar value = " + sb.getValue());
		System.out.println();
		
		sb.setValue(44);
		System.out.println("model red value = " + model.getColor().getRed());
		System.out.println("scrollbar value = " + sb.getValue());
	}
}

package patterns.observer.multimodel;

import java.awt.Color;
import java.util.EnumSet;

import patterns.observer.multimodel.ColorModel.ColorChannel;

public class ColorTest {

	public static void main(String[] args) {
		final ColorModel model = new ColorModel(Color.BLACK);

		EnumSet<ColorChannel> all = EnumSet.allOf(ColorChannel.class);

		model.addColorListener(new ColorListener() {
			// this listener changes red to gray
			@Override
			public void colorValueChanged(Color c) {
				if (Color.RED.equals(c)) {
					model.setRed(128);
					model.setGreen(128);
					model.setBlue(128);
				}
			}
		}, all);

		model.addColorListener(new ColorListener() {
			@Override
			public void colorValueChanged(Color c) {
				System.out.println("Current color: " + c);
			}
		}, all);

		model.setColor(Color.WHITE);
	}

}

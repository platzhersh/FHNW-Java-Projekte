package imageprocessing;

import javax.swing.JOptionPane;

import main.PicsiSWT;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import utils.FilterLib;

public class Filter implements IImageProcessor {

	@Override
	public boolean isEnabled(int imageType) {
		return imageType != PicsiSWT.IMAGE_TYPE_INDEXED;
	}

	@Override
	public Image run(Image input, int imageType) {

		String[] options = { "Gauss", "Laplace", "Box", "Neutral Element" };
		int response = JOptionPane.showOptionDialog(null, "Choose your filter", "Filter",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[0]);

		FilterLib fl = new FilterLib();
		ImageData outData = input.getImageData();
		if (response == 0) {
			outData = fl.convolveGaussian(input.getImageData(), 0);
		} else if (response == 1) {
			outData = fl.convolveLaplace(input.getImageData(), 0);
			//outData = fl.convolveLaplace2(input.getImageData(), 0);
		} else if (response == 2) {
			outData = fl.convolveBox(input.getImageData(), 0);
		} else if (response == 3) {
			outData = fl.convolveNeutral(input.getImageData(), 0);
		} else {
			// Do nothing
		}

		return new Image(input.getDevice(), outData);
	}

}

package imageprocessing;

import javax.swing.JOptionPane;

import main.PicsiSWT;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import utils.DitheringLib;
import utils.FilterLib;

public class Dithering implements IImageProcessor {

	@Override
	public boolean isEnabled(int imageType) {
		return imageType != PicsiSWT.IMAGE_TYPE_INDEXED;
	}

	@Override
	public Image run(Image input, int imageType) {

		ImageData inData = input.getImageData();
		
		String[] options = { "floydSteinberg", "jarvisJudiceNinke", "stucki", "atkinson", "burkes", "sierra", "schlag","normal" };
		int response = JOptionPane.showOptionDialog(null, "Choose your filter", "Filter",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[0]);

		DitheringLib dl = new DitheringLib();
		ImageData outData = input.getImageData();
		if (response == 0) {
			outData = dl.floydSteinberg(input.getImageData(), 0);
		} else if (response == 1) {
			outData = dl.jarvisJudiceNinke(input.getImageData(), 0);
			//outData = fl.convolveLaplace2(input.getImageData(), 0);
		} else if (response == 2) {
			outData = dl.stucki(input.getImageData(), 0);
		} else if (response == 3) {
			outData = dl.atkinson(input.getImageData(), 0);
		} else if (response == 4) {
			outData = dl.burkes(input.getImageData(), 0);
		} else if (response == 5) {
			outData = dl.sierra(input.getImageData(), 0);
		} else if (response == 6) {
			outData = dl.schlag(input.getImageData(), 0);
		} else if (response == 7) {
			outData = dl.normal(input.getImageData(), 0);
		} else {
			// Do nothing
		}
		
		return new Image(input.getDevice(), outData);
	}

}

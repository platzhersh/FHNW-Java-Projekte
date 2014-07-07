package utils;

import imageprocessing.IImageProcessor;

import javax.swing.JOptionPane;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import main.PicsiSWT;

public abstract class AMatrixProcessing implements IImageProcessor {

	protected ImageData inData;
	protected ImageData outData;
	protected Matrix model = Matrix.identity(3);

	protected abstract void getTransformationMatrix();
	protected abstract int colorInterpolation(double x, double y);
	
	@Override
	public boolean isEnabled(int imageType) {
		return imageType != PicsiSWT.IMAGE_TYPE_INDEXED;
	}
	
	@Override
	public Image run(Image input, int imageType) {

		model = Matrix.identity(3);	//auskommentieren um Änderungen zu behalten.
		inData = input.getImageData();
		outData = (ImageData) input.getImageData().clone();
		
		getTransformationMatrix();
		
		for (int v = 0; v < inData.height; v++) {
			for (int u = 0; u < inData.width; u++) {
				
				int pixel = 0;	
				
				Vector t = new Vector(u, v, 1); // target				
				Vector s = model.times(t); 		// source						
				pixel = colorInterpolation(s.x(1), s.x(2)); //Farbe aus der Source holen
				
				outData.setPixel(u, v, pixel);
			}
		}
		return new Image(input.getDevice(), outData);
	}
	

}

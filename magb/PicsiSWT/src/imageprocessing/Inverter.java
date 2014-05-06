package imageprocessing;

import main.PicsiSWT;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class Inverter implements IImageProcessor {

	@Override
	public boolean isEnabled(int imageType) {
		
		return imageType != PicsiSWT.IMAGE_TYPE_INDEXED;
	}

	@Override
	public Image run(Image input, int imageType) {
		// TODO Auto-generated method stub
		ImageData inData = input.getImageData();
		
		// Instagram Style
		/*for (int v = 0; v < inData.height; v++) {
			for (int u = 0; u < inData.width; u++) {
				int pixel = inData.getPixel(u, v);
				RGB rgb = inData.palette.getRGB(pixel);
				rgb.blue = rgb.green;
				inData.setPixel(u, v, inData.palette.getPixel(rgb));
			}
			
		}*/
		
		// effizienteste Lösung 
		byte[] data = inData.data;
		
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) ~data[i];					// Bitmuster invertieren
		}
		return new Image(input.getDevice(), inData);
	}

}

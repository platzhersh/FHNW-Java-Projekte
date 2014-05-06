package imageprocessing;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class Offset implements IImageProcessor {

	@Override
	public boolean isEnabled(int imageType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Image run(Image input, int imageType) {
		// 1. Move Image up left
		ImageData inData = input.getImageData();
		ImageData id = (ImageData) input.getImageData().clone();
		for (int i = 0; i < inData.width; i++) {
			for (int j = 0; j < inData.height; j++) {
				
				int offsetX = inData.width/2;
				int offsetY = inData.height/2;
				
				int sourceX = (inData.width + i-offsetX) % inData.width;
				int sourceY = (inData.height + j-offsetY) % inData.height;
				
				id.setPixel(i, j, inData.getPixel(sourceX, sourceY));
				}
			}
		return new Image(input.getDevice(), id);
	}

}

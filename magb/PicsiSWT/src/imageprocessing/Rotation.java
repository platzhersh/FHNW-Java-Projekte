package imageprocessing;

import javax.swing.JOptionPane;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class Rotation implements IImageProcessor {

	@Override
	public boolean isEnabled(int imageType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Image run(Image input, int imageType) {
		// TODO Auto-generated method stub
		ImageData id1 = (ImageData) input.getImageData().clone();
		ImageData id2 = (ImageData) input.getImageData().clone();
		String deg = JOptionPane.showInputDialog("text");
		ImageData inData = input.getImageData();
		int height = inData.height;
		
		double cosa = Math.cos(Math.toRadians(Double.parseDouble(deg)));
		double sina = Math.sin(Math.toRadians(Double.parseDouble(deg)));
		
		
		int x = 0;
		int y = 0;
		
		
		
		// Rotate
		
		// find center to determine offset of mapped image
		int lcX = (int) (cosa*(inData.width/2)+sina*(inData.height/2));
		int lcY = (int) ((-sina)*(inData.width/2)+cosa*(inData.height/2));
		int offsetX = inData.width/2-lcX;
		int offsetY = inData.height/2-lcY;
		
		for (int i = 0; i < inData.width; i++) {
			for (int j = 0; j < inData.height; j++) {
				id2.setPixel(i, j, 0);
				x = (int) (cosa*i+sina*j) + offsetX;
				y = (int) ((-sina)*i+cosa*j) + offsetY;
				
				//System.out.println("hm. x="+x+", y="+y+", i="+i+", j="+j);

				if (x < inData.width && x >= 0 && y < inData.height && y >= 0) {
						//id2.setPixel(x, y, inData.getPixel(i, j));
						id2.setPixel(i, j, inData.getPixel(x, y));
				} else {
					/*if (x > inData.width) {
						System.out.println("Problem at ("+x+","+y+"): x too big");
					} else if ( y > inData.height) {
						System.out.println("Problem at ("+x+","+y+"): y too big");
					} else {
						System.out.println("Problem at ("+x+","+y+"): ???");
					}*/
					}
			}
		}
		
		return new Image(input.getDevice(), id2);
	}
	
	
	
}

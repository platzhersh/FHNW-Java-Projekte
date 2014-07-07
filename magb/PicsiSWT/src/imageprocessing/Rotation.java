package imageprocessing;

import javax.swing.JOptionPane;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import utils.Matrix;
import utils.Vector;

public class Rotation implements IImageProcessor {

	@Override
	public boolean isEnabled(int imageType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Image run(Image input, int imageType) {		
		
		ImageData id = (ImageData) input.getImageData().clone();
		String deg = JOptionPane.showInputDialog("Rotationswinkel in Grad");
		ImageData inData = input.getImageData();
		
		//Matrix trans1 = Matrix.translation(-inData.width/2, -inData.height/2);
		//Matrix trans2 = Matrix.translation(inData.width/2, inData.height/2);
		Matrix rot = Matrix.rotation2d(Double.parseDouble(deg));
		Matrix scale = Matrix.scale(2);
		/*
		for (int i = 0; i < inData.width; i++) {
			for (int j = 0; j < inData.height; j++) {
				id.setPixel(i, j, 0);
				
				Vector vin = new Vector(i, j, 1);
								
				//Vector vout = scale.times(trans2.times(rot.times((trans1.times(vin)))));
				//Vector vout = trans1.times(vin);
				Matrix trans1 = Matrix.translation(-i, -j);
				Matrix trans2 = Matrix.translation(i, j);
				
				Vector vout = new Vector(0,0,0);
				try {
					vout = trans2.times((Vector) rot.times(trans1.times(vin)));
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				
				
				int x = (int) vout.x(0);
				int y = (int) vout.x(1);

				//System.out.println("hm. x="+x+", y="+y+", i="+i+", j="+j);

				if (x < inData.width && x >= 0 && y < inData.height && y >= 0) {
						//id.setPixel(x, y, inData.getPixel(i, j));
						id.setPixel(i, j, inData.getPixel(x, y));
				} 
			}
			
		}*/
		Matrix trans1 = Matrix.translation(100, 100);
		trans1.print();
		
		Vector vin = new Vector(10, 10, 1);
		vin.print();
		
		Vector vout = trans1.times(vin);
		vout.print();
		
		
		
		
		// Rotate variante 2 (working)
		
		double cosa = Math.cos(Math.toRadians(Double.parseDouble(deg)));
		double sina = Math.sin(Math.toRadians(Double.parseDouble(deg)));
		
		
		int x = 0;
		int y = 0;
		
		// find center to determine offset of mapped image
		int lcX = (int) (cosa*(inData.width/2)+sina*(inData.height/2));
		int lcY = (int) ((-sina)*(inData.width/2)+cosa*(inData.height/2));
		int offsetX = inData.width/2-lcX;
		int offsetY = inData.height/2-lcY;
		
		for (int i = 0; i < inData.width; i++) {
			for (int j = 0; j < inData.height; j++) {
				id.setPixel(i, j, 0);
				x = (int) (cosa*i+sina*j) + offsetX;
				y = (int) ((-sina)*i+cosa*j) + offsetY;

				//System.out.println("hm. x="+x+", y="+y+", i="+i+", j="+j);

				if (x < inData.width && x >= 0 && y < inData.height && y >= 0) {
						//id2.setPixel(x, y, inData.getPixel(i, j));
						id.setPixel(i, j, inData.getPixel(x, y));
				} 
					}
			}
		
		
		
		return new Image(input.getDevice(), id);
	}
	
	
	
}

package imageprocessing;

import main.PicsiSWT;

import org.eclipse.swt.graphics.*;

import utils.Matrix;
import utils.Vector;
import java.awt.Color;

/**
 * Farbbild in ein Graustufenbild umwandeln
 *
 */
public class GrayScale implements IImageProcessor {

	@Override
	public boolean isEnabled(int imageType) {
		return imageType != PicsiSWT.IMAGE_TYPE_INDEXED;
	}

	@Override
	public Image run(Image input, int imageType) {
		
		ImageData inData = input.getImageData(); //Die echten Bilddaten sind hier drin
		
		//RGB zu YUV Umwandlungsmatrix
//		double[] linear_conversion = { 
//			0.2126, 0.7152, 0.0722,
//			-0.09991, -0.33609, 0.436,
//			0.615, -0.55861, -0.05639
//		};
		double[] linear_conversion = { 
				0.299, 0.587, 0.114,
				-0.147, -0.289, 0.436,
				0.615, -0.515, -0.1
			};
		Matrix m = new Matrix(3,3,linear_conversion);
		
		//YUV zu RGB Umwandlungmatrix (das Inverse)
//		double[] inv_linear_conversion = { 
//		    1.0000,    0.0000,    1.28033,
//		    1.0000,   -0.21482,   -0.38059,
//		    1.0000,    2.12798,   -0.0005
//		};	
		double[] inv_linear_conversion = { 
			1.0000,    0.0000,    1.1398,
	    	1.0000,   -0.3946,   -0.5805,
	    	1.0000,    2.0320,   -0.0005
		};
		Matrix minv = new Matrix(3,3,inv_linear_conversion);
			
		
		for(int v=0;v<inData.height; v++) {
			for(int u=0;u<inData.width;u++) {
				int pixel = inData.getPixel(u, v); 
				
				RGB rgb = inData.palette.getRGB(pixel);
				
				//Variante mit Farbwerte U und V von YUV auf 0 setzen
				Vector r = new Vector(rgb.red, rgb.green, rgb.blue);
				double[] y = m.times(r).getV();
				y[2] = 0;
				y[1] = 0;
				r = minv.times(new Vector(y));
				rgb.red = (int) r.x(1);
				rgb.green = (int) r.x(2);
				rgb.blue = (int) r.x(3);
				
			
				if(rgb.red < 0) rgb.red = 0;
				else if (rgb.red > 255) rgb.red = 255;
				
				if(rgb.green < 0) rgb.green = 0;
				else if (rgb.green > 255) rgb.green = 255;
				
				if(rgb.blue < 0) rgb.blue = 0;
				else if (rgb.blue > 255) rgb.blue = 255;
				
				
				//Variante mit der Sättigung auf 0 gesetzt (schlechter)
//				float[] hsb = inData.palette.getRGB(pixel).getHSB();
//				
//				hsb[1] = 0; //Sättigung auf 0 setzen
//				
//				Color c = new Color(Color.HSBtoRGB(hsb[0],hsb[1],hsb[2]));
//				RGB rgb = new RGB(0,0,0);
//				rgb.red = c.getRed();
//				rgb.green = c.getGreen();
//				rgb.blue = c.getBlue();
				
				inData.setPixel(u, v, inData.palette.getPixel(rgb));				
			}
		}		
		return new Image(input.getDevice(), inData);
	}

}

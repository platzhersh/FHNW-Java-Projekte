package utils;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class InterpolationLib {

	public static int nearestNeighbour(ImageData inData, double u, double v) {
		int pixel = 0;
		int u1 = (int) (u + 0.5d);
		int v1 = (int) (v + 0.5d);
		
		if(u1>0 && u1 < inData.width && v1 > 0 && v1 < inData.height)
			pixel = inData.getPixel(u1, v1);
		
		return pixel;
	}

	public static int bilinear(ImageData inData, double u, double v) {
		int[] c = new int[8];
		int pixel = 0;
		//A
		c[0] = (int) (u);
		c[1] = (int) (v);
		//B
		c[2] = ((int) (u))+1;
		c[3] = (int) (v);
		//C
		c[4] = (int) (u);
		c[5] = ((int) (v))+1;
		//D
		c[6] = ((int) (u))+1;
		c[7] = ((int) (v))+1;
		
//		System.out.println("A="+c[0]+","+c[1]+" B="+c[2]+","+c[3]);
//		System.out.println("C="+c[4]+","+c[5]+" D="+c[6]+","+c[7]);
		
		/*
		//Pixel ausserhalb des Bildes spiegeln
		for(int i=0;i<8;i++)  { 
			if(c[i] < 0) c[i] *= -1;
			// v Werte 
			else if(c[i] % 2 == 1 && c[i] >= inData.height) c[i]=inData.height-1; 
			//u Werte
			else if(c[i] % 2 == 0 && c[i] >= inData.width) c[i]=inData.width-1;
		}
		*/
		double a = u-c[4]; 
		double b = v-c[1];
	
		double[] colors = new double[12];
		RGB rgb = new RGB(0, 0, 0);
		for(int i=0;i<8;i+=2) { //0 2 4 6
			if(c[i]>0 && c[i] < inData.width && c[i+1] > 0 && c[i+1] < inData.height)
				pixel = inData.getPixel(c[i], c[i+1]);
			else {
				pixel = 0;
			}
			
			rgb = inData.palette.getRGB(pixel);
			colors[i/2] = rgb.red;
			colors[i/2 +4] = rgb.blue;
			colors[i/2 +8] = rgb.green;
		}
		
		int[] t = new int[3];
		//Alle 3 Farbkanäle interpolieren
		for(int i=0, k=0;i<12;i+=4, k++) {
//			System.out.println("Colors:" +colors[i]  + ", " + colors[i+1] + ", " + colors[i+2] + ", " + colors[i+3] );
			int g2 = (int) (
						(a-1)	*(b-1)	*colors[i] + 
						a		*(1-b)	*colors[i+1] +
						(1-a)	*b		*colors[i+2] + 
						a		*b		*colors[i+3]);
			double e = colors[i] + a*(colors[i+1]-colors[i]);
			double f = colors[i+2] + a*(colors[i+3]-colors[i+2]);			
			double g = e + b*(f-e);
			t[k] = (int)g;
//			System.out.println("e: " + e);
//			System.out.println("f: " + f);
//			System.out.println("g: " + g );
//			System.out.println("g2:" + g2);
//			System.out.println("Interpol: " + t[k] + "   //a: " + a + " b: " + b + "u:"+u + " v:"+v);
		}
		rgb.red = t[0];
		rgb.blue = t[1];
		rgb.green = t[2];
		return inData.palette.getPixel(rgb);
	}
	
}

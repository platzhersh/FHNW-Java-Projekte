package utils;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class FilterLib {

	public ImageData convolve(ImageData inData, int imageType, 
			int[][] filter, int norm, int offset) {
				
		ImageData outData = (ImageData) inData.clone();
				
		//iterate through image
		for(int v=0;v<inData.height;v++) {
			for(int u=0;u<inData.width;u++) {
								
				//iterate through filter
				float filterSumRed = 0;
				float filterSumGreen = 0;
				float filterSumBlue = 0;
				
				for(int i=0;i<filter.length;i++) {
					for(int j=0;j<filter[0].length;j++){
					
						int vi = v + (i-(filter.length/2));
						int uj = u + (j-(filter[0].length/2));
						
						if(vi < 0 || vi >= inData.height) 
							vi = v - (i-(filter.length/2));
						
						if(uj < 0 || uj >= inData.width) 
							uj = u - (j-(filter[0].length/2));
						
						int pixel = inData.getPixel(uj, vi);
						RGB rgb = inData.palette.getRGB(pixel);
					
						filterSumRed += rgb.red * filter[i][j];
						filterSumGreen += rgb.green * filter[i][j];
						filterSumBlue += rgb.blue * filter[i][j];
						
					}
				}
					filterSumRed /= norm;
					filterSumGreen /= norm;
					filterSumBlue /= norm;
					
					RGB rgb = new RGB(0,0,0);
					rgb.red = (int) filterSumRed + offset;
					rgb.green = (int) filterSumGreen + offset;
					rgb.blue = (int) filterSumBlue + offset;
					
					//clmap()
					if(rgb.red < 0) rgb.red = 0;
					else if (rgb.red > 255) rgb.red = 255;
					
					if(rgb.green < 0) rgb.green = 0;
					else if (rgb.green > 255) rgb.green = 255;
					
					if(rgb.blue < 0) rgb.blue = 0;
					else if (rgb.blue > 255) rgb.blue = 255;
					
					outData.setPixel(u, v, outData.palette.getPixel(rgb));
				
			}
		}
		return outData;
	}
	
	
	public ImageData convolveNeutral(ImageData inData, int imageType) {
		int norm = 1;
		int offset = 0;
		int[][] filter = {
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,1,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0}
		};		
		return convolve(inData, imageType, filter,norm,offset);		
	}
	
	public ImageData convolveBox(ImageData inData, int imageType) {
		int norm = 9;
		int offset = 0;
		int[][] filter = {
				{0,0,0,0,0},
				{0,1,1,1,0},
				{0,1,1,1,0},
				{0,1,1,1,0},
				{0,0,0,0,0}
		};		
		return convolve(inData, imageType, filter,norm,offset);		
	}
	
	public ImageData convolveGaussian(ImageData inData, int imageType) {
		int norm = 57;
		int offset = 1;
		int[][] filter = {
				{0,1,2,1,0},
				{1,3,5,3,1},
				{2,5,9,5,2},
				{1,3,5,3,1},
				{0,1,2,1,0}
		};		
		return convolve(inData, imageType, filter,norm,offset);		
	}
	
	public ImageData convolveLaplace(ImageData inData, int imageType) {
		int norm = 1;
		int offset = 0;
		int[][] filter = {
				{0	,0	,-1	,0	,0},
				{0	,-1	,-2	,-1	,0},
				{-1	,-2	,16	,-2	,-1},
				{0	,-1	,-2	,-1	,0},
				{0	,0	,-1	,0	,0}
		};		
		return convolve(inData, imageType, filter,norm,offset);		
	}
	public ImageData convolveLaplace2(ImageData inData, int imageType) {
		int norm = 10;
		int offset = 0;
		int[][] filter = {
				{0	,0	,-1	,0	,0},
				{0	,-1	,2	,-1	,0},
				{-1	,2	,10	,2	,-1},
				{0	,-1	,2	,-1	,0},
				{0	,0	,-1	,0	,0}
		};		
		return convolve(inData, imageType, filter,norm,offset);		
	}
	
	
}

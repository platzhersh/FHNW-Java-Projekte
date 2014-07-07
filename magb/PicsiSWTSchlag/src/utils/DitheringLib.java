package utils;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class DitheringLib {

	public ImageData dithering(ImageData inData, int imageType, 
			int[][] filter, int divisor) {
		
		for(int v=0;v<inData.height; v++) {
			for(int u=0;u<inData.width;u++) {
				
				int pixel = inData.getPixel(u, v);
				RGB rgb = inData.palette.getRGB(pixel);
				float t = rgb.red;
				float error = 0;
				if(t < 128) {
					error = t;
					t = 0;
				} else {
					error = t-255;
					t = 255;
				}
				inData.setPixel(u, v, inData.palette.getPixel(new RGB((int)t,(int)t,(int)t)));
				error /= divisor;
								
				for(int i=0;i<filter.length;i++) {
					for(int j=0;j<filter[0].length;j++){
					
						int vi = v + (i-(filter.length/2));
						int uj = u + (j-(filter[0].length/2));
						
						if(vi < 0 || vi >= inData.height) 
							vi = v - (i-(filter.length/2));
						
						if(uj < 0 || uj >= inData.width) 
							uj = u - (j-(filter[0].length/2));
												
						if(filter[i][j] != 0) {
							int g = inData.palette.getRGB(inData.getPixel(uj, vi)).red;							
							g += ((float) filter[i][j]) * error;
							//else if(t==255) g -= ((float) filter[i][j]) * error;
							g = clamp(g);
							inData.setPixel(uj, vi, inData.palette.getPixel(new RGB((int)g,(int)g,(int)g)));
						}						
					}
				}
			}
		}
		return inData;
	}
	
	private int clamp(int g) {
		if(g < 0) return 0; 
		else if (g>255) return 255;
		return g;
	}

	public ImageData floydSteinberg(ImageData inData, int imageType)
	{
		int[][] filter = {
				{0,0,0},
				{0,0,7},
				{3,5,1}
		};
		return dithering(inData,imageType,filter,16);		
	}
	
	public ImageData jarvisJudiceNinke(ImageData inData, int imageType)
	{
		int[][] filter = {
			{0,0,0,0,0},
			{0,0,0,0,0},
			{0,0,0,7,5},
			{3,5,7,5,3},
			{1,3,5,3,1}
		};
		return dithering(inData,imageType,filter,48);		
	}
	public ImageData stucki(ImageData inData, int imageType)
	{
		int[][] filter = {
			{0,0,0,0,0},
			{0,0,0,0,0},
			{0,0,0,8,4},
			{2,4,8,4,2},
			{1,2,4,2,1}
		};
		return dithering(inData,imageType,filter,42);		
	}
	public ImageData atkinson(ImageData inData, int imageType)
	{
		int[][] filter = {
			{0,0,0,0,0},
			{0,0,0,0,0},
			{0,0,0,1,1},
			{0,1,1,1,0},
			{0,0,1,0,0}
		};
		return dithering(inData,imageType,filter,8);		
	}
	public ImageData burkes(ImageData inData, int imageType)
	{
		int[][] filter = {
			{0,0,0,0,0},
			{0,0,0,0,0},
			{0,0,0,8,4},
			{2,4,8,4,2},
			{0,0,0,0,0}
		};
		return dithering(inData,imageType,filter,32);		
	}
	public ImageData sierra(ImageData inData, int imageType)
	{
		int[][] filter = {
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,5,3},
				{2,4,5,4,2},
				{0,2,3,2,0}
		};
		return dithering(inData,imageType,filter,32);		
	}
	public ImageData schlag(ImageData inData, int imageType)
	{
		int[][] filter = {
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,2,0},
				{0,0,2,1,0},
				{0,0,0,0,0}
		};
		return dithering(inData,imageType,filter,8);		
	}
	public ImageData normal(ImageData inData, int imageType)
	{
		int[][] filter = {
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0}
		};
		return dithering(inData,imageType,filter,1);		
	}
}

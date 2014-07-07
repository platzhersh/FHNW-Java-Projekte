package imageprocessing;

import main.PicsiSWT;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class PatternMatching implements IImageProcessor {

	@Override
	public boolean isEnabled(int imageType) {
		return imageType == PicsiSWT.IMAGE_TYPE_GRAY;
	}

	@Override
	public Image run(Image input, int imageType) {
		
		final int x = 200;
		final int y = 310;
		final int w = 70;
		final int h = 50;
		
		ImageData inData = input.getImageData();
		ImageData pattern = ImageProcessing.crop(inData, x, y, w, h);
		
		final int size = pattern.height * pattern.width;
		final double sqrtSize = Math.sqrt(size);
		
		//preprocessing: myR, myR2, sigmaR
		double myR = 0, myR2 = 0, sigmaR = 0;
		for(int j=0;j < pattern.height; j++) {
			for(int i=0;i < pattern.width; i++) {
				int p = pattern.getPixel(i,j);
				myR += p;
				myR2 += p*p;
			}
		}
		myR /= size;
		myR2 /= size;
		sigmaR = Math.sqrt(myR2 - (myR*myR)); //Standardabweichung
		
		//pattern matching
		final int stepSize = 2;
		double cMax = 0;
		int bestPosX = 0, bestPosY=0;
		
		for(int s=0;s < inData.height - pattern.height; s += stepSize) {
			for(int r=0;r < inData.width - pattern.width; r += stepSize) {
				//compute correlation coefficient
				double myI = 0;
				int sumIR = 0, sumI2 = 0;
				
				for(int j=0;j < pattern.height; j++) {
					for(int i=0;i < pattern.width; i++) {
						int val = inData.getPixel(r+i, s+j);
						int p = pattern.getPixel(i, j);
						myI += val;
						sumIR += val*p;
						sumI2 += val*val;
					}
				}
				myI /= size;
				double c = (sumIR - size * myI * myR)/
						(Math.sqrt(sumI2 - size * myI * myI) * sigmaR * sqrtSize);
				
				if(c > cMax) {
					cMax = c;
					bestPosX = r;
					bestPosY = s;
				}
				
			}
		}
		
		return new Image(input.getDevice(), ImageProcessing.crop(inData, bestPosX, bestPosY, w, h));
	}

}




















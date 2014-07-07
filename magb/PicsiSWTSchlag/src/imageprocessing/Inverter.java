package imageprocessing;

import main.PicsiSWT;

import org.eclipse.swt.graphics.*;

public class Inverter implements IImageProcessor {

	/**
	 * Es geht darum wann diese Funktion ausgef�hrt werden k�nnen. F�r welche Bildtypen. Bsp nur mit RGB Bildern. Oder nur mit Graustufen oder so.
	 */
	@Override
	public boolean isEnabled(int imageType) {
		return imageType != PicsiSWT.IMAGE_TYPE_INDEXED;
	}

	/**
	 * Hier passiert was wir mit dem Bild machen wollen.
	 */
	@Override
	public Image run(Image input, int imageType) {
		
		ImageData inData = input.getImageData(); //Die echten Bilddaten sind hier drin
		//Sobald ich getImageData mache kriege ich eine Kopie und das Input Bild wird sich nicht dadurch ver�ndern.
		
		
		//Zweite Variante: Auf die Bildelemente direkt zugreifen. getPixel und setPixel sind jedoch nicht super effizient.
		for(int v=0;v<inData.height; v++) {
			for(int u=0;u<inData.width;u++) {
				int pixel = inData.getPixel(u, v); // In diesem Int nur 1 bit relevant beim bin�rbild relevant, oder beim graustufen nur 1 byte oder bei rgb sind es 3 byte.
				//Unter der Annahme wir h�tte ein RGB Farbbild:
				RGB rgb = inData.palette.getRGB(pixel);
				rgb.red = (byte) ~rgb.red;
								
				inData.setPixel(u, v, inData.palette.getPixel(rgb));				
			}
		}
		
		
		/*
		 //Effizienteste Variante, ohne Interpretation
		byte[] data = inData.data;
		
		for(int i=0;i<data.length; i++) {
			data[i] = (byte) ~data[i]; //Invertieren der Intensit�ten. Tilde macht daraus einen Integer
		}
		
		*/
		return new Image(input.getDevice(), inData);
	}

}

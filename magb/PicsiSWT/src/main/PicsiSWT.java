package main;
import gui.MainWindow;

import java.text.MessageFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PicsiSWT {
	public static final String[] OPEN_FILTER_EXTENSIONS = new String[] {
		"*.bmp;*.gif;*.ico;*.jfif;*.jpeg;*.jpg;*.pbm;*.pgm;*.png;*.ppm;*.tif;*.tiff",
		"*.bmp", "*.gif", "*.ico", "*.jpg;*.jpeg;*.jfif", "*.png", "*.pbm;*.pgm;*.ppm", "*.tif;*.tiff" };
	public static final String[] OPEN_FILTER_NAMES = new String[] {
		"All images",
		"BMP (*.bmp)", "GIF (*.gif)", "ICO (*.ico)", "JPEG (*.jpg, *.jpeg, *.jfif)",
		"PNG (*.png)", "PnM (*.pbm, *.pgm, *.ppm)", "TIFF (*.tif, *.tiff)" };
	public static final String[] SAVE_FILTER_EXTENSIONS = new String[] {
		"*.bmp", "*.gif", "*.ico", "*.jpg", "*.pbm", "*.pgm", "*.png", "*.ppm", "*.tif" };
	public static final String[] SAVE_FILTER_NAMES = new String[] {
		"BMP (*.bmp)", "GIF (*.gif)",
		"ICO (*.ico)", "JPEG (*.jpg)", "PBM (*.pbm)", "PGM (*.pgm)", "PNG (*.png)", "PPM (*.ppm)",
		"TIFF (*.tif)" };
	public static final int IMAGE_PBM = 100; // extension of SWT.IMAGE_XXX
	public static final int IMAGE_PGM = 101;
	public static final int IMAGE_PPM = 102;

	public static final int IMAGE_TYPE_BINARY = 1;
	public static final int IMAGE_TYPE_GRAY = 2;
	public static final int IMAGE_TYPE_RGB = 3;
	public static final int IMAGE_TYPE_INDEXED = 4;

	public static final String APP_NAME = "FHNW Picsi SWT";
	public static final String APP_VERSION = "1.0.2014.11";
	
	public static Shell s_shell;
	
	public static void main(String[] args) {
		Display display = new Display();
		MainWindow picsi = new MainWindow();
		s_shell = picsi.open(display);
		
		while (!s_shell.isDisposed())
			if (!display.readAndDispatch()) display.sleep();
		display.dispose();
	}

	public static int determineFileType(String filename) {
		String name = filename.toLowerCase();
	
		if (name.endsWith("bmp"))
			return SWT.IMAGE_BMP;
		if (name.endsWith("gif"))
			return SWT.IMAGE_GIF;
		if (name.endsWith("ico"))
			return SWT.IMAGE_ICO;
		if (name.endsWith("jpg") || name.endsWith("jpeg") || name.endsWith("jfif"))
			return SWT.IMAGE_JPEG;
		if (name.endsWith("png"))
			return SWT.IMAGE_PNG;
		if (name.endsWith("tif") || name.endsWith("tiff"))
			return SWT.IMAGE_TIFF;
		if (name.endsWith("pbm")) 
			return IMAGE_PBM;
		if (name.endsWith("pgm")) 
			return IMAGE_PGM;
		if (name.endsWith("ppm")) 
			return IMAGE_PPM;
		return SWT.IMAGE_UNDEFINED;
	}
	
	public static int determineFilterIndex(String filename) {
		String name = filename.toLowerCase();
	
		if (name.endsWith("bmp"))
			return 0;
		if (name.endsWith("gif"))
			return 1;
		if (name.endsWith("ico"))
			return 2;
		if (name.endsWith("jpg") || name.endsWith("jpeg") || name.endsWith("jfif"))
			return 3;
		if (name.endsWith("pbm")) 
			return 4;
		if (name.endsWith("pgm")) 
			return 5;
		if (name.endsWith("png"))
			return 6;
		if (name.endsWith("ppm")) 
			return 7;
		if (name.endsWith("tif") || name.endsWith("tiff"))
			return 8;
		return 0;
	}
	
	public static String fileTypeString(int filetype) {
		if (filetype == SWT.IMAGE_BMP)
			return "BMP";
		if (filetype == SWT.IMAGE_GIF)
			return "GIF";
		if (filetype == SWT.IMAGE_ICO)
			return "ICO";
		if (filetype == SWT.IMAGE_JPEG)
			return "JPEG";
		if (filetype == SWT.IMAGE_PNG)
			return "PNG";
		if (filetype == SWT.IMAGE_TIFF)
			return "TIFF";
		if (filetype == IMAGE_PBM) 
			return "PBM";
		if (filetype == IMAGE_PGM) 
			return "PGM";
		if (filetype == IMAGE_PPM) 
			return "PPM";
		return "Unknown type";
	}
	
	/*
	 * Open an error dialog displaying the specified information.
	 */
	public static String createMsg(String msg, Object[] args) {
		MessageFormat formatter = new MessageFormat(msg);
		return formatter.format(args);
	}
	
	public static String createMsg(String msg, Object arg) {
		MessageFormat formatter = new MessageFormat(msg);
		return formatter.format(new Object[]{arg});
	}

	public static int determineimageType(ImageData imageData) {
		if (imageData.depth == 1) {
			return PicsiSWT.IMAGE_TYPE_BINARY;
		} else {
			if (imageData.palette.isDirect) {
				return PicsiSWT.IMAGE_TYPE_RGB;
			} else {
				// indexed "color" image

				// check the palette
				if (imageData.depth == 8) {
					RGB[] rgbs = imageData.getRGBs();
					
					// check for grayscale
					int i = 0;
					while(i < rgbs.length && rgbs[i].blue == rgbs[i].green && rgbs[i].green == rgbs[i].red) i++;
					if (i >= rgbs.length) {
						return PicsiSWT.IMAGE_TYPE_GRAY;
					}
				}
				return PicsiSWT.IMAGE_TYPE_INDEXED;
			}
		}
	}
}

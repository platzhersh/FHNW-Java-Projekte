package imageprocessing;

import gui.TwinView;

import java.util.ArrayList;

import main.PicsiSWT;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class ImageProcessing {
	private static class ImageMenuItem {
		private String m_text;
		private int m_accelerator;
		private IImageProcessor m_process;
		
		public ImageMenuItem(String text, int accelerator, IImageProcessor proc) {
			m_text = text;
			m_accelerator = accelerator;
			m_process = proc;
		}
	}
	
	private TwinView m_views;
	private ArrayList<ImageMenuItem> m_menuItems = new ArrayList<ImageMenuItem>();
	
	public ImageProcessing(TwinView views) {
		assert views != null : "views are null";
		m_views = views;
		
		m_menuItems.add(new ImageMenuItem("&Invert\tF1", SWT.F1, new Inverter()));	// dank "&" auch per Alt-I aufrufbar
		// TODO add here further image processing objects (they are inserted into the Image menu)
		m_menuItems.add(new ImageMenuItem("&Rotate\tF2", SWT.F2, new Rotation()));
		m_menuItems.add(new ImageMenuItem("&Offset\tF2", SWT.F3, new Offset()));
	}
	
	public void createMenuItems(Menu menu) {
		for(final ImageMenuItem item : m_menuItems) {
			MenuItem mi = new MenuItem(menu, SWT.PUSH);
			mi.setText(item.m_text);
			mi.setAccelerator(item.m_accelerator);
			mi.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					Image output = null;
					try {
						output = item.m_process.run(m_views.getFirstImage(), m_views.getFirstimageType());
					} catch(Throwable e) {
						String location = item.m_text.substring(0, item.m_text.indexOf('\t')).replace("&", "");
						m_views.m_mainWnd.showErrorDialog("ImageProcessing", location, e);
					}						
					if (output != null) {
						m_views.showImageInSecondView(output);
					}
				}
			});
		}
	}
	
	public boolean isEnabled(int i) {
		return m_menuItems.get(i).m_process.isEnabled(m_views.getFirstimageType());
	}

	// TODO add general image processing class methods here
	
	public static ImageData scale(ImageData inData, int imageType, int tw, int th) {
		double sx = (double)tw/inData.width;
		double sy = (double)th/inData.height;
		ImageData outData = new ImageData(tw, th, inData.depth, inData.palette);
		
		// scale image data
		for (int y=0; y < outData.height; y++) {
			int v0 = (int)Math.floor(y/sy);
			int v1 = v0 + 1;
			double b1 = y/sy - v0, b0 = (1 - b1);
	
			for (int x=0; x < outData.width; x++) {
				int u0 = (int)Math.floor(x/sx);
				int u1 = u0 + 1;
				double a1 = x/sx - u0, a0 = (1 - a1);
				
				int A, B, C, D;
				
				A = inData.getPixel(u0, v0);
				C = (v1 < inData.height) ? inData.getPixel(u0, v1) : A;
				if (u1 < inData.width) {
					B = inData.getPixel(u1, v0);
					D = (v1 < inData.height) ? inData.getPixel(u1, v1) : B;
				} else {
					B = A;
					D = C;
				}
				
				if (imageType == PicsiSWT.IMAGE_TYPE_GRAY) {
					// Gray: bilinear interpolation				
					outData.setPixel(x, y, ImageProcessing.clamp8(a0*b0*A + a1*b0*B + a0*b1*C + a1*b1*D));
				} else {
					// RGB: bilinear interpolation
					RGB cA = inData.palette.getRGB(A), 
						cB = inData.palette.getRGB(B), 
						cC = inData.palette.getRGB(C), 
						cD = inData.palette.getRGB(D);
					
					RGB cG = new RGB(
						ImageProcessing.clamp8(a0*b0*cA.red   + a1*b0*cB.red   + a0*b1*cC.red   + a1*b1*cD.red),
						ImageProcessing.clamp8(a0*b0*cA.green + a1*b0*cB.green + a0*b1*cC.green + a1*b1*cD.green),
						ImageProcessing.clamp8(a0*b0*cA.blue  + a1*b0*cB.blue  + a0*b1*cC.blue  + a1*b1*cD.blue)
					);
					
					outData.setPixel(x, y, outData.palette.getPixel(cG));
				}
			}
		}
		return outData;
	}

	public static ImageData convolve(ImageData inData, int imageType, int[][] filter, int den, int offset) {
		assert den > 0 : "wrong denominator";
		
		ImageData outData = (ImageData)inData.clone();
		int fSizeD2 = filter.length/2;
		
		if (imageType == PicsiSWT.IMAGE_TYPE_GRAY) {
			for (int v=0; v < outData.height; v++) {
				for (int u=0; u < outData.width; u++) {
					int sum = 0;
					
					for (int j=0; j < filter.length; j++) {
						int v0 = v + j - fSizeD2;
						if (v0 < 0) v0 = -v0;
						if (v0 >= inData.height) v0 = 2*inData.height - v0 - 1;
						for (int i=0; i < filter.length; i++) {
							int u0 = u + i - fSizeD2;
							if (u0 < 0) u0 = -u0;
							if (u0 >= inData.width) u0 = 2*inData.width - u0 - 1;
							
							sum += inData.getPixel(u0, v0)*filter[j][i];
						}					
					}
					outData.setPixel(u, v, clamp8(offset + sum/den));
				}
			}
		} else if (imageType == PicsiSWT.IMAGE_TYPE_RGB) {
			for (int v=0; v < outData.height; v++) {
				for (int u=0; u < outData.width; u++) {
					RGB sum = new RGB(0, 0, 0);
					
					for (int j=0; j < filter.length; j++) {
						int v0 = v + j - fSizeD2;
						if (v0 < 0) v0 = -v0;
						if (v0 >= inData.height) v0 = 2*inData.height - v0 - 1;
						for (int i=0; i < filter.length; i++) {
							int u0 = u + i - fSizeD2;
							if (u0 < 0) u0 = -u0;
							if (u0 >= inData.width) u0 = 2*inData.width - u0 - 1;
							
							RGB col = inData.palette.getRGB(inData.getPixel(u0, v0));
							sum.red += col.red*filter[j][i];
							sum.green += col.green*filter[j][i];
							sum.blue += col.blue*filter[j][i];
						}					
					}
					sum.red = clamp8(offset + sum.red/den);
					sum.green = clamp8(offset + sum.green/den);
					sum.blue = clamp8(offset + sum.blue/den);
					outData.setPixel(u, v, outData.palette.getPixel(sum));
				}
			}
		}
		
		return outData;
	}

	public static int[] cumulativeHistogram(int[] histo) {
		assert histo.length > 0 : "invalid histogram";
		int[] cum = new int[histo.length];
		
		cum[0] = histo[0];
		for (int i=1; i < histo.length; i++) cum[i] = cum[i - 1] + histo[i];
		
		return cum;
	}

	public static int[] histogram(ImageData inData, int nClasses) {
		int[] histo = new int[nClasses];
		byte[] data = inData.data;
		
		for (int i=0; i < data.length; i++) {
			histo[0xFF & data[i]]++;
		}
		
		return histo;
	}

	public static void applyLUT(ImageData inData, byte[] lut) {
		byte[] data = inData.data;
	
		for (int i=0; i < data.length; i++) data[i] = lut[0xFF & data[i]];
	}

	public static boolean isValidAllRGB(ImageData outData) {
		boolean[][][] used = new boolean[256][256][256];
	
		for (int v=0; v < outData.height; v++) {
			for (int u=0; u < outData.width; u++) {
				RGB rgb = outData.palette.getRGB(outData.getPixel(u, v));
				if (used[rgb.red][rgb.green][rgb.blue]) {
					return false;
				}
				used[rgb.red][rgb.green][rgb.blue] = true;
			}
		}
		return true;
	}

	public static int clamp8(int v) {
		// only needs one test in the usual case
		if ((v & 0xFFFFFF00) != 0) 
			return (v < 0) ? 0 : 255; 
		else 
			return v;
		//return (v >= 0) ? (v < 256 ? v : 255) : 0;
	}
	
	public static int clamp8(double d) {
		if (d < 0) {
			return 0;
		} else if (d > 255) {
			return 255;
		} else {
			return (int)Math.round(d);
		}
	}

	public static ImageData crop(ImageData inData, int x, int y, int w, int h) {
		ImageData outData = new ImageData(w, h, inData.depth, inData.palette);
		
		for (int v=0; v < h; v++) {
			for (int u=0; u < w; u++) {
				outData.setPixel(u, v, inData.getPixel(u + x, v + y));
			}
		}
		return outData;
	}
}

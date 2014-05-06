package files;
import main.PicsiSWT;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JTextArea;

public class PNM implements IImageFile {
	private int m_width, m_height;
	private int m_maxValue;
	private int m_fileType;
	private boolean m_ascii; // image matrix in ASCII or binary format

	/**
	 * Read PNM file and returns the resulting image.
	 * The file format might be ASCII or binary.
	 */
	public Image read(String fileName, Display display) throws Exception {
		RandomAccessFile raf = new RandomAccessFile(fileName, "r");
		
		// read header
		readHeader(raf);
		
		// create image and read in image data
		if (m_ascii) {
			BufferedReader in = new BufferedReader(new FileReader(raf.getFD()));
			
			switch(m_fileType) {
			case PicsiSWT.IMAGE_PBM: return readPBM(in, display);
			case PicsiSWT.IMAGE_PGM: return readPGM(in, display);
			case PicsiSWT.IMAGE_PPM: return readPPM(in, display);
			default:
				in.close();
				throw new Exception("Read PNM: Wrong image type");
			}
		} else {
			switch(m_fileType) {
			case PicsiSWT.IMAGE_PBM: return readBinPBM(raf, display);
			case PicsiSWT.IMAGE_PGM: return readBinPGM(raf, display);
			case PicsiSWT.IMAGE_PPM: return readBinPPM(raf, display);
			default:
				raf.close();
				throw new Exception("Read PNM: Wrong image type");
			}
		}
	}

	/**
	 * Save PNM file in binary format.
	 */
	public void save(String fileName, int fileType, Image image) throws Exception {
		RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
		ImageData imageData = image.getImageData();
		
		m_fileType = fileType;
		m_width = imageData.width;
		m_height = imageData.height;
		if (m_maxValue == 0) m_maxValue = 255;
		
		m_ascii = false;
		
		// write header
		switch(m_fileType) {
		case PicsiSWT.IMAGE_PBM:
			raf.writeBytes("P4");
			break;
		case PicsiSWT.IMAGE_PGM:
			raf.writeBytes("P5");
			break;
		case PicsiSWT.IMAGE_PPM:
			raf.writeBytes("P6");
			break;
		}
		raf.writeBytes("\n" + m_width + " " + m_height);
		if (m_fileType != PicsiSWT.IMAGE_PBM) {
			raf.writeBytes("\n" + m_maxValue);
		}
		raf.writeBytes("\n");		
		
		// save image in binary format
		switch(m_fileType) {
		case PicsiSWT.IMAGE_PBM: writeBinPBM(raf, imageData); break;
		case PicsiSWT.IMAGE_PGM: writeBinPGM(raf, imageData); break;
		case PicsiSWT.IMAGE_PPM: writeBinPPM(raf, imageData); break;
		default:
			raf.close();
			throw new Exception("Write PNM: Wrong image type");
		}
	}
	
	/**
	 * Check PNM header structure
	 * @param fileName
	 * @return true if the format is ASCII
	 */
	public boolean checkHeader(String fileName) {		
		RandomAccessFile raf;
		
		try {
			raf = new RandomAccessFile(fileName, "r");
	
			readHeader(raf);
			raf.close();
			
			return m_ascii;
		} catch(Exception ex) {
			return false;
		}
	}
	
	/**
	 * Display image content in PNM ASCII format
	 */
	public void displayTextOfBinaryImage(Image image, JTextArea text) {
		// write header
		switch(m_fileType) {
		case PicsiSWT.IMAGE_PBM:
			text.append("P1");
			break;
		case PicsiSWT.IMAGE_PGM:
			text.append("P2");
			break;
		case PicsiSWT.IMAGE_PPM:
			text.append("P3");
			break;
		}
		text.append("\n" + m_width + " " + m_height);
		if (m_fileType != PicsiSWT.IMAGE_PBM) {
			text.append("\n" + m_maxValue);
		}
		text.append("\n");
		
		// write image
		switch(m_fileType) {
		case PicsiSWT.IMAGE_PBM:
			writePBM(image, text);
			break;
		case PicsiSWT.IMAGE_PGM:
			writePGM(image, text, m_maxValue);
			break;
		case PicsiSWT.IMAGE_PPM:
			writePPM(image, text, m_maxValue);
			break;
		}
	}

	public boolean isBinaryFormat() {
		return !m_ascii;
	}

	public static void writePBM(Image image, JTextArea text) {
		ImageData imageData = image.getImageData();
		assert imageData.depth == 1 : "wrong channel depth";
		byte[] data = imageData.data;
		int pos = 0;
		StringBuilder sb = new StringBuilder(imageData.width*2);
		int stride = (imageData.width + 7)/8;
		
		for (int y = 0; y < imageData.height; y++) {
			int w = 0;
			for (int x = 0; x < stride; x++) {
				int val = data[pos++] << 24;
				for(int i = 0; i < 8 && w < imageData.width; i++, w++) {
					sb.append(val < 0 ? 0 : 1).append(' ');
					val <<= 1;
				}
			}
			text.append(sb.toString());
			text.append("\n");
			sb.delete(0, sb.length());
		}	
	}

	public static void writePGM(Image image, JTextArea text, int maxValue) {
		ImageData imageData = image.getImageData();
		assert imageData.depth == 8 : "wrong channel depth";
		final int padding = imageData.bytesPerLine - imageData.width;
		byte[] data = imageData.data;
		int pos = 0;
		StringBuilder sb = new StringBuilder(imageData.width*3);
		
		for (int y = 0; y < imageData.height; y++) {
			for (int x = 0; x < imageData.width; x++) {
				sb.append(data[pos++]*maxValue/255).append(' ');
			}
			pos += padding;
			text.append(sb.toString());
			text.append("\n");
			sb.delete(0, sb.length());
		}	
	}

	public static void writePPM(Image image, JTextArea text, int maxValue) {
		ImageData imageData = image.getImageData();
		assert imageData.palette.isDirect : "indexed color is not supported";
		StringBuilder sb = new StringBuilder(imageData.width*9);
		
		for (int y = 0; y < imageData.height; y++) {
			for (int x = 0; x < imageData.width; x++) {
				RGB rgb = imageData.palette.getRGB(imageData.getPixel(x, y));
				sb.append(rgb.red*maxValue/255).append(' ');
				sb.append(rgb.green*maxValue/255).append(' ');
				sb.append(rgb.blue*maxValue/255).append(' ');
			}
			text.append(sb.toString());
			text.append("\n");
			sb.delete(0, sb.length());
		}	
	}

	private void readHeader(RandomAccessFile in) throws Exception {
		String s;
		boolean useMaxValue = true;
		
		// read magic number
		do {
			s = in.readLine(); 
			if (s == null) {
				in.close();
				throw new Exception("Wrong header"); 
			}
			s = s.trim();
		} while(s.isEmpty() || s.charAt(0) == '#');
		
		String[] ss = s.split("\\s+");	// regular expression: \s = whitespace, x+ = at least one
		if (ss[0].equals("P1")) {
			m_ascii = true;
			m_fileType = PicsiSWT.IMAGE_PBM;
			useMaxValue = false;
		} else if (ss[0].equals("P2")) {
			m_ascii = true;
			m_fileType = PicsiSWT.IMAGE_PGM;
		} else if (ss[0].equals("P3")) {
			m_ascii = true;
			m_fileType = PicsiSWT.IMAGE_PPM;
		} else if (ss[0].equals("P4")) {
			m_ascii = false;
			m_fileType = PicsiSWT.IMAGE_PBM;
			useMaxValue = false;
		} else if (ss[0].equals("P5")) {
			m_ascii = false;
			m_fileType = PicsiSWT.IMAGE_PGM;
		} else if (ss[0].equals("P6")) {
			m_ascii = false;
			m_fileType = PicsiSWT.IMAGE_PPM;
		} else {
			in.close();
			throw new Exception("Wrong PNM type");
		}
		
		// read width and height
		do {
			s = in.readLine(); 
			s = s.trim();
		} while(s.isEmpty() || s.charAt(0) == '#');

		ss = s.split("\\s+");	// regular expression: \s = whitespace, x+ = at least one
		
		if (ss.length == 2) {
			m_width = Integer.parseInt(ss[0]);
			m_height = Integer.parseInt(ss[1]);
			
		} else {
			in.close();
			throw new Exception("Wrong header: image size expected");
		}
		//System.out.println(m_width);
		//System.out.println(m_height);
		
		if (useMaxValue) {
			// read max value
			do {
				s = in.readLine(); 
				s = s.trim();
			} while(s.isEmpty() || s.charAt(0) == '#');

			m_maxValue = Integer.parseInt(s);
			//System.out.println(m_maxValue);				
		}
	}
	
	private Image readPBM(BufferedReader in, Display display) throws IOException {
		try {
			int stride = (m_width + 7)/8;
			byte[] data = new byte[stride*m_height];
			
			// read data
			int pos = 0, val = 0, cnt = 0, x = 0;
			String s;
			
			// read next line
			do {
				s = in.readLine(); 
			} while(s != null && s.isEmpty());
			
			while(s != null && pos < data.length) {
				String[] ss = s.trim().split("\\s+");
				for(int i=0; i < ss.length; i++) {
					val = (val << 1) + (1 - Integer.parseInt(ss[i]));
					x++;
					cnt++;
					if (cnt == 8 || x == m_width) {
						data[pos++] = (byte)(val << (8 - cnt));
						val = 0;
						cnt = 0;
						if (x == m_width) x = 0;
					}
				}
				// read next line
				do {
					s = in.readLine(); 
				} while(s != null && s.isEmpty());
			}
			
			// create image
			return new Image(display, new ImageData(m_width, m_height, 1, new PaletteData(new RGB[]{ new RGB(255, 255, 255), new RGB(0, 0, 0) }), 1, data));

		} finally {
			in.close();			
		}
	}
	
	private Image readBinPBM(RandomAccessFile in, Display display) throws IOException {
		try {
			// read data
			int strideIn = ((m_width + 31)/32)*4;
			int stride = (m_width + 7)/8;
			byte[] data = new byte[stride*m_height];
			int pos = 0;
			byte[] line = new byte[strideIn];
	
			for (int y = 0; y < m_height; y++) {
				in.read(line);
				for (int x = 0; x < stride; x++) {
					data[pos++] = (byte)(~line[x]);
				}
			}			
	
			// create image
			return new Image(display, new ImageData(m_width, m_height, 1, new PaletteData(new RGB[]{ new RGB(255, 255, 255), new RGB(0, 0, 0) }), 1, data));
		
		} finally {
			in.close();
		}

	}
	
	private void writeBinPBM(RandomAccessFile out, ImageData imageData) throws IOException {
		try {
			// write data
			int strideOut = ((m_width + 31)/32)*4;
			int stride = (m_width + 7)/8;
			int pos = 0;
			byte[] line = new byte[strideOut];
	
			for (int y = 0; y < m_height; y++) {
				for (int x = 0; x < stride; x++) {
					line[x] = (byte)(~imageData.data[pos++]);
				}
				out.write(line);
			}	
			
		} finally {
			out.close();
		}
	}
	
	private Image readPGM(BufferedReader in, Display display) throws IOException {
		try {
			byte[] data = new byte[m_width*m_height];
			
			// read data
			int pos = 0;
			String s;
			
			// read next line
			do {
				s = in.readLine(); 
			} while(s != null && s.isEmpty());
			
			while(s != null && pos < data.length) {
				String[] ss = s.trim().split("\\s+");
				for(int i=0; i < ss.length; i++) {
					int val = Integer.parseInt(ss[i])*255/m_maxValue;
					data[pos++] = (byte)(val);
				}
				// read next line
				do {
					s = in.readLine(); 
				} while(s != null && s.isEmpty());
			}
		
			// create palette
			RGB[] palette = new RGB[256];
			for (int i=0; i < palette.length; i++) {
				palette[i] = new RGB(i, i ,i);
			}
			
			// create image
			return new Image(display, new ImageData(m_width, m_height, 8, new PaletteData(palette), 1, data));
			
		} finally {
			in.close();
		}
	}

	private Image readBinPGM(RandomAccessFile in, Display display) throws IOException {
		try {
			// read data
			int pos = 0;
	
			byte[] line = new byte[m_width];
			byte[] data = new byte[m_width*m_height];
	
			for (int y = 0; y < m_height; y++) {
				in.read(line);
				for (int x = 0; x < m_width; x++) {
					data[pos++] = (byte)(line[x]*255/m_maxValue);
				}
			}	
			
			// create palette
			RGB[] palette = new RGB[256];
			for (int i=0; i < palette.length; i++) {
				palette[i] = new RGB(i, i ,i);
			}
			
			// create image
			return new Image(display, new ImageData(m_width, m_height, 8, new PaletteData(palette), 1, data));

		} finally {
			in.close();
		}
	}
	
	private void writeBinPGM(RandomAccessFile out, ImageData imageData) throws IOException {
		try {
			// write data
			final int padding = imageData.bytesPerLine - m_width;
			int pos = 0;
			byte[] line = new byte[m_width];
	
			for (int y = 0; y < m_height; y++) {
				for (int x = 0; x < m_width; x++) {
					line[x] = (byte)(imageData.data[pos++]*m_maxValue/255);
				}
				pos += padding;
				out.write(line);
			}	
			
		} finally {
			out.close();
		}
	}
	
	private Image readPPM(BufferedReader in, Display display) throws IOException {
		try {
			final int bypp = 3;
			byte[] data = new byte[m_width*m_height*bypp];
			
			// read data
			int pos = 0;
			String s;
			
			// read next line
			do {
				s = in.readLine(); 
			} while(s != null && s.isEmpty());
			
			while(s != null && pos < data.length) {
				String[] ss = s.trim().split("\\s+");
				for(int i=0; i < ss.length; i++) {
					data[pos++] = (byte)(Integer.parseInt(ss[i])*255/m_maxValue);
				}
				// read next line
				do {
					s = in.readLine(); 
				} while(s != null && s.isEmpty());
			}
					
			// create image
			PaletteData pd = new PaletteData(0xFF0000, 0xFF00, 0xFF);
			pd.blueShift = 0;
			pd.greenShift = -8;
			pd.redShift = -16;
			return new Image(display, new ImageData(m_width, m_height, bypp*8, pd, 1, data));
			
		} finally {
			in.close();
		}
	}

	private Image readBinPPM(RandomAccessFile in, Display display) throws IOException {
		try {
			final int bypp = 3;
			byte[] data = new byte[m_width*m_height*bypp];
	
			// read data
			int pos = 0;
			final int nBytes = bypp*m_width;
			final int stride = ((nBytes + 3)/4)*4;
			byte[] line = new byte[stride];
	
			for (int y = 0; y < m_height; y++) {
				int p = 0;
				in.read(line);
				for (int x = 0; x < nBytes; x++) {
					data[pos++] = (byte)(line[p++]*255/m_maxValue);
				}
			}			
			
			// create image
			PaletteData pd = new PaletteData(0xFF0000, 0xFF00, 0xFF); // R G B
			pd.redShift = -16;
			pd.greenShift = -8;
			pd.blueShift = 0;
			return new Image(display, new ImageData(m_width, m_height, bypp*8, pd, 1, data));
		
		} finally {
			in.close();			
		}
	}
	
	private void writeBinPPM(RandomAccessFile out, ImageData imageData) throws IOException {
		try {
			final int bypp = 3;
			
			// write data
			final int nBytes = bypp*m_width;
			final int stride = ((nBytes + 3)/4)*4;
			byte[] line = new byte[stride];
	
			for (int y = 0; y < m_height; y++) {
				int pos = 0;
				for (int x = 0; x < m_width; x++) {
					RGB rgb = imageData.palette.getRGB(imageData.getPixel(x, y));
					line[pos++] = (byte)(rgb.red*m_maxValue/255);
					line[pos++] = (byte)(rgb.green*m_maxValue/255);
					line[pos++] = (byte)(rgb.blue*m_maxValue/255);
				}
				out.write(line);
			}	
			
		} finally {
			out.close();
		}
	}
	
}

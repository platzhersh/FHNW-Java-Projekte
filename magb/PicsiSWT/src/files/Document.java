package files;
import javax.swing.JTextArea;

import main.PicsiSWT;

import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

public class Document {
	private String m_fileName;
	private IImageFile m_file;
	private int m_fileType;
	
	public Image load(String filename, int filetype, Display display) throws Exception {
		m_fileType = filetype;
		if (m_fileType >= PicsiSWT.IMAGE_PBM && m_fileType <= PicsiSWT.IMAGE_PPM) {
			m_file = new PNM();
		} else {
			m_file = new BMP();
		}
		if (m_file != null) {
			m_fileName = filename;
			return m_file.read(filename, display);
		}
		return null;
	}
	
	public void save(Image image, String filename, int filetype) throws Exception {
		if (filename == null) {
			if (m_file != null && m_fileName != null && m_fileType >= 0) {
				// save with existing file name and file type
				filename = m_fileName;
				filetype = m_fileType;
			} else {
				assert filename != null : "filename is null";
				assert filetype >= 0 : "wrong filetype";
				
				if (filetype >= PicsiSWT.IMAGE_PBM && filetype <= PicsiSWT.IMAGE_PPM) {
					m_file = new PNM();
				} else {
					m_file = new BMP();
				}				
			}
		} else {
			// save with new file name or new file type
			assert filetype >= 0 : "wrong filetype";
			if (m_file == null || filetype != m_fileType) {
				if (filetype >= PicsiSWT.IMAGE_PBM && filetype <= PicsiSWT.IMAGE_PPM) {
					m_file = new PNM();
				} else {
					m_file = new BMP();
				}				
			}
		}
		if (m_file != null) {
			m_fileName = filename;
			m_file.save(filename, filetype, image);
		}
	}
	
	public boolean isBinaryFormat() {
		assert m_file != null : "no image file available";
		return m_file.isBinaryFormat();		
	}
	
	public String getFileName() {
		return m_fileName;
	}
	
	public void displayTextOfBinaryImage(Image image, JTextArea text) {
		assert m_file != null : "no image file available";
		
		text.removeAll();
		m_file.displayTextOfBinaryImage(image, text);			
	}

}

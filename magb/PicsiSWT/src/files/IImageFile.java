package files;

import javax.swing.JTextArea;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public interface IImageFile {
	public Image read(String fileName, Display display) throws Exception;
	public void save(String fileName, int fileType, Image image) throws Exception;
	public void displayTextOfBinaryImage(Image image, JTextArea text);
	public boolean isBinaryFormat();
}

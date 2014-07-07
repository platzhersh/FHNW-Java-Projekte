package imageprocessing;

import org.eclipse.swt.graphics.Image;

public interface IImageProcessor {
	/**
	 * Enables/disables the menu entry
	 * @param imageType one of the image types define in Globl.IMAGE_TYPE_XXX
	 * @return true if the menu entry should be enabled for the given image type
	 */
	public boolean isEnabled(int imageType);
	
	/**
	 * Runs the image processing routine
	 * @param input image
	 * @param imageType one of the image types define in Globl.IMAGE_TYPE_XXX
	 * @return output image or null if the image processing doesn't produce a useful output
	 */
	public Image run(final Image input, int imageType);
}

package imageprocessing;

import main.PicsiSWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

/**
 * Created with IntelliJ IDEA.
 * User: pixel
 * Date: 08.06.14
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class Dithering implements IImageProcessor {
    @Override
    public boolean isEnabled(int imageType) {
        return imageType == PicsiSWT.IMAGE_TYPE_GRAY;
    }

    @Override
    public Image run(Image input, int imageType) {
        ImageData inData = input.getImageData();
        byte[] data = inData.data;

        byte[] resdata = new byte[inData.width*inData.height];

        RGB[] palette = new RGB[2];
        palette[1] = new RGB(0, 0 ,0);
        palette[0] = new RGB(255, 255 ,255);
        /*byte[] resdata = new byte[inData.width*inData.height];

        RGB[] palette = new RGB[256];
        for (int i=0; i < palette.length; i++) {
            palette[i] = new RGB(i, i ,i);
        }    */


        for(int i=0;i<data.length;i++){
            int val = 0xFF & data[i];
            resdata[i] = (0xFF & data[i])<128?(byte)0xFF:0;

        }
        ImageData res = new ImageData(inData.width, inData.height, 8, new PaletteData(palette), 1,resdata);


        res.setPixel(0,0,0);
        res.setPixel(0,0,1);

        System.out.println(res.data[0]);
        System.out.println(res.data[1]);




        return new Image(input.getDevice(), res);
    }
}

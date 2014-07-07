package imageprocessing;

import utils.Matrix;
import main.PicsiSWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

/**
 * Created with IntelliJ IDEA.
 * User: pixel
 * Date: 08.06.14
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class RGB2Grey implements IImageProcessor {
    @Override
    public boolean isEnabled(int imageType) {
        return imageType == PicsiSWT.IMAGE_TYPE_RGB;
    }

    @Override
    public Image run(Image input, int imageType) {
        ImageData inData = input.getImageData();

        double[][] a = new double[3][3];
        a[0][0] = 0.299;
        a[0][1] = 0.587;
        a[0][2] = 0.114;
        a[1][0] = -0.147;
        a[1][1] = -0.289;
        a[1][2] = 0.436;
        a[2][0] = 0.615;
        a[2][1] = -0.515;
        a[2][2] = -0.1;
        Matrix c = new Matrix(a);

        byte[] data = new byte[inData.width*inData.height];

        RGB[] palette = new RGB[256];
        for (int i=0; i < palette.length; i++) {
            palette[i] = new RGB(i, i ,i);
        }

        ImageData res = new ImageData(inData.width, inData.height, 8, new PaletteData(palette), 1,data);



        for(int v=0;v<inData.height;v++){
            for(int u=0;u<inData.width;u++){
                int pixel = inData.getPixel(u,v);

                RGB rgb = inData.palette.getRGB(pixel);

                double[][] p = new double[3][1];
                p[0][0] = rgb.red;
                p[1][0] = rgb.green;
                p[2][0] = rgb.blue;
                Matrix Pm = new Matrix(p);
                Pm = c.times(Pm);
                rgb.red = (int)Pm.el(0,0);
                rgb.green = (int)Pm.el(0,0);
                rgb.blue = (int)Pm.el(0,0);
                res.setPixel(u, v, res.palette.getPixel(rgb));
            }
        }

        return new Image(input.getDevice(), res);

    }
}

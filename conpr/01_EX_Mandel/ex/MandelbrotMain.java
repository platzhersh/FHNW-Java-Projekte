package ex;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Computes images of the Mandelbrot Set.
 */
public class MandelbrotMain {

    public static final int IMG_WIDTH = 2048 * 2;
    public static final int IMG_HEIGHT = IMG_WIDTH;

    public static final double RE_PER_PIXEL = (Mandelbrot.RE_MAX - Mandelbrot.RE_MIN) / IMG_WIDTH;
    public static final double IM_PER_PIXEL = (Mandelbrot.IM_MAX - Mandelbrot.IM_MIN) / IMG_HEIGHT;

    public static void main(String[] args) throws Exception {
        System.out.println("#CPU: " + Runtime.getRuntime().availableProcessors());

        // Sequential computation
        System.out.println("Start: Sequential");
        long start = System.currentTimeMillis();
        BufferedImage image = computeSeqMandel();
        long end = System.currentTimeMillis();
        System.out.format("End: Sequential %s ms\n", end - start);
        ImageIO.write(image, "png", new File("FractalSeq.png"));

        
        // Parallel computation
        System.out.println("Start: Parallel");
        start = System.currentTimeMillis();
        image = computeParMandel();
        end = System.currentTimeMillis();
        System.out.format("End: Parallel %s ms\n", end - start);
        ImageIO.write(image, "png", new File("FractalPar.png"));
    }

    /** Computes an image of the Mandelbrot set in a sequential fashion. */
    private static BufferedImage computeSeqMandel() {
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = image.getRaster();
        ColorModel model = image.getColorModel();
        for (int x = 0; x < IMG_WIDTH; x++) { // x-axis
            for (int y = 0; y < IMG_HEIGHT; y++) { // y-axis
                double re = Mandelbrot.RE_MIN + x * RE_PER_PIXEL; // map pixel to complex plane
                double im = Mandelbrot.IM_MIN + y * IM_PER_PIXEL; // map pixel to complex plane
                int iterations = Mandelbrot.mandel(re, im);
                raster.setDataElements(x, y, Mandelbrot.getColor(iterations, model));
            }
        }
        return image;
    }

    /**
     * Computes an image of the Mandelbrot set much faster than
     * {@link #computeSeqMandel()}, when your done.
     */
    public static BufferedImage computeParMandel() {
        final BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final WritableRaster raster = image.getRaster();
        final ColorModel model = image.getColorModel();

        // TODO Implement parallel version of the mandelbrot set computation.
        return image;
    }
}

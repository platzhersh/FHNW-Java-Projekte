package le.fj;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

import javax.imageio.ImageIO;

/**
 * Computes images of the Mandelbrot Set.
 */
public class MandelbrotFJ {

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

        // Parallel Pool computation
        System.out.println("Start: Parallel Pool");
        start = System.currentTimeMillis();
        image = computeParPoolMandel();
        end = System.currentTimeMillis();
        System.out.format("End: Parallel Pool %s ms\n", end - start);
        ImageIO.write(image, "png", new File("FractalPar.png"));
        
        // Parallel FJ Pool computation
        System.out.println("Start: Parallel FJ Pool");
        start = System.currentTimeMillis();
        image = computeParFJPoolMandel();
        end = System.currentTimeMillis();
        System.out.format("End: Parallel Pool FJ %s ms\n", end - start);
        ImageIO.write(image, "png", new File("FractalParFJ.png"));
        

    }

    /** Computes an image of the Mandelbrot set in a sequential fashion. */
    private static BufferedImage computeSeqMandel() {
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = image.getRaster();
        ColorModel model = image.getColorModel();
        for (int x = 0; x < IMG_WIDTH; x++) { // x-axis
            double re = Mandelbrot.RE_MIN + x * RE_PER_PIXEL; // map pixel to
                                                              // complex plane
            for (int y = 0; y < IMG_HEIGHT; y++) { // y-axis
                double im = Mandelbrot.IM_MIN + y * IM_PER_PIXEL; // map pixel
                                                                  // to complex
                                                                  // plane
                int iterations = Mandelbrot.mandel(re, im);
                raster.setDataElements(x, y,
                        Mandelbrot.getColor(iterations, model));
            }
        }
        return image;
    }

    /**
     * Computes an image of the Mandelbrot set much faster than
     * {@link #computeSeqMandel()}, when your done.
     */
    public static BufferedImage computeParMandel() {
        final BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        final WritableRaster raster = image.getRaster();
        final ColorModel model = image.getColorModel();

        final int N = Runtime.getRuntime().availableProcessors();
        final int heightPerThread = IMG_HEIGHT / N;
        final List<Thread> threads = new ArrayList<Thread>(N);

        for (int i = 0; i < N; i++) {
            final int startY = i * heightPerThread;
            final int endY = (i < N - 1) ? startY + heightPerThread
                    : IMG_HEIGHT;
            Thread t = new Thread(new MandelSlice(startY, endY, raster, model));
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    private static final class MandelSlice implements Runnable {
        private final int startY, endY;
        private final WritableRaster raster;
        private final ColorModel model;

        private MandelSlice(int startY, int endY, WritableRaster raster, ColorModel model) {
            this.endY = endY;
            this.startY = startY;
            this.raster = raster;
            this.model = model;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int x = 0; x < IMG_WIDTH; x++) { // x-axis
                double re = Mandelbrot.RE_MIN + x * RE_PER_PIXEL;
                for (int y = startY; y < endY; y++) { // y-axis
                    double im = Mandelbrot.IM_MIN + y * IM_PER_PIXEL;
                    int iterations = Mandelbrot.mandel(re, im);
                    raster.setDataElements(x, y, Mandelbrot.getColor(iterations, model));
                }
            }
            // System.out.println("PAR: " + (System.currentTimeMillis() - start)
            // + "(" + startX + "," + endX + ")");
        }
    }

    /**
     * Computes an image of the Mandelbrot set much faster than
     * {@link #computeSeqMandel()}, when your done.
     */
    public static BufferedImage computeParPoolMandel() {
        final BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        final WritableRaster raster = image.getRaster();
        final ColorModel model = image.getColorModel();

        final int N_THREADS = Runtime.getRuntime().availableProcessors();
        final int N_SLICES = 256;

        final int widthPerThread = IMG_WIDTH / N_SLICES;
        final List<MandelSlice> tasks = new LinkedList<MandelSlice>();

        for (int i = 0; i < N_SLICES; i++) {
            final int startX = i * widthPerThread;
            final int endX = (i < N_SLICES - 1) ? startX + widthPerThread
                    : IMG_WIDTH;
            tasks.add(new MandelSlice(startX, endX, raster, model));
        }

        final List<Thread> threads = new ArrayList<Thread>(N_THREADS);
        for (int i = 0; i < N_THREADS; i++) {
            Thread t = new Thread() {
                public void run() {
                    boolean running = true;
                    while (running) {
                        MandelSlice slice = null;
                        synchronized (tasks) {
                            if (!tasks.isEmpty()) {
                                slice = tasks.remove(0);
                            } else {
                                running = false;
                            }
                        }
                        if (slice != null) {
                            slice.run();
                        }
                    }
                }
            };

            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static BufferedImage computeParFJPoolMandel() {
        final BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final WritableRaster raster = image.getRaster();
        final ColorModel model = image.getColorModel();

        ForkJoinPool fj = new ForkJoinPool();
        fj.invoke(new FJMandel(0, IMG_WIDTH, raster, model));
        return image;
    }

    @SuppressWarnings("serial")
   static class FJMandel extends RecursiveAction {

        private final int startY, endY;
        private final WritableRaster raster;
        private final ColorModel model;

        private FJMandel(int startY, int endY, WritableRaster raster, ColorModel model) {
            this.endY = endY;
            this.startY = startY;
            this.raster = raster;
            this.model = model;
        }

        @Override
        public void compute() {
            if (endY - startY <= 32) { // Solve sequentially
                for (int x = 0; x < IMG_WIDTH; x++) { // x-axis
                    double re = Mandelbrot.RE_MIN + x * RE_PER_PIXEL;
                    for (int y = startY; y < endY; y++) { // y-axis
                        double im = Mandelbrot.IM_MIN + y * IM_PER_PIXEL;
                        int iterations = Mandelbrot.mandel(re, im);
                        raster.setDataElements(x, y, Mandelbrot.getColor(iterations, model));
                    }
                }
            } else { // Split problem 
                int mid = (startY + endY) / 2;
                FJMandel left = new FJMandel(startY, mid, raster, model);
                FJMandel right = new FJMandel(mid, endY, raster, model);
                invokeAll(left, right);
            }
        }
    }
}

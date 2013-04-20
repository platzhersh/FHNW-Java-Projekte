package le.fj;

import java.awt.Color;
import java.awt.image.ColorModel;

/**
 * Computes the Mandelbrot set.
 * http://en.wikipedia.org/wiki/Mandelbrot_set
 */
public class Mandelbrot {
   public static final int MAX_ITERATIONS = 255;
   
   public static final double RE_MIN = -2;
   public static final double RE_MAX = 2;
   public static final double IM_MIN = -2;
   public static final double IM_MAX = 2; 
   
   /**
    * z_n+1 = z_n^2 + c starting with z_0 = 0
    * 
    * Checks whether c = re + i*im is a member of the Mandelbrot set.
    * @param re real part
    * @param im imaginary part
    * @return the number of iterations
    */
   public static int mandel(double cre, double cim) {
      double re = 0.0;
      double im = 0.0;
      int iterations = 0;
      while (re*re + im*im <= 4 && iterations < MAX_ITERATIONS) {
         double re1 = re * re - im * im + cre;
         double im1 = 2 * re * im + cim;
         re = re1;
         im = im1;
         iterations++;
      }
      return iterations;
   }
   
   /** Your color computation goes here! */
   public static Color getCustomColor(int iterations) {
      Color c;
      if (iterations == MAX_ITERATIONS) {
         c = Color.BLACK;
      } else {
         int grey = MAX_ITERATIONS - iterations;
         c = new Color((4 * grey) % 256, grey, (4 * grey) % 256);
      }
      return c;
   }
   
   /** Fiddles with awt color model etc. */
   public static Object getColor(int iterations, ColorModel model) {
      return model.getDataElements(getCustomColor(iterations).getRGB(), null);
   }
}

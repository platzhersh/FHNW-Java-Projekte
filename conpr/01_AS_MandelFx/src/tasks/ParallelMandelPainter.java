package tasks;

import as.CancelSupport;
import as.Mandelbrot;
import as.PixelPainter;
import as.Plane;

public class ParallelMandelPainter implements Runnable {

	
	int i, nofCores, part;
	double half, reMin, imMax, step;
	Plane plane;
	CancelSupport cancel;
	PixelPainter painter;
	
	public ParallelMandelPainter(int i, int cores, Plane p, CancelSupport c, PixelPainter painter) {
		this.i = i;
		this.nofCores = cores;
		this.plane = p;
		this.cancel = c;
		this.painter = painter;
		
		half = plane.length / 2;
		reMin = plane.center.r - half;
		imMax = plane.center.i + half;
		step = plane.length / Mandelbrot.IMAGE_LENGTH;
		
		part = Mandelbrot.IMAGE_LENGTH / cores;
	}
	
	@Override
	public void run() {
		for (int x = 0; x < Mandelbrot.IMAGE_LENGTH && !cancel.isCancelled(); x++) { // x-axis
		//for (int x = part*i; x < part*i+part && !cancel.isCancelled(); x++) { // x-axis
			double re = reMin + x * step; // map pixel to complex plane
			for (int y = part*i; y < part*i+part; y++) { // y-axis
				double im = imMax - y * step; // map pixel to complex plane

				int iterations = Mandelbrot.mandel(re, im);
				painter.paint(x, y, Mandelbrot.getColor(iterations));
			}
		}
	}	

}

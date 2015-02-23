package as;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javafx.application.Platform;
import javafx.beans.value.WritableDoubleValue;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class PixelWriterPixelPainter implements PixelPainter {
	private final BlockingQueue<Pixel> ps;// = new LinkedBlockingQueue<>(); is much slower here!
	private final Thread painter;
	private final int CHUNK_SIZE = 2048 * 16;

	public PixelWriterPixelPainter(PixelWriter pp, int totalPixels,
			WritableDoubleValue progress) {
		ps = new ArrayBlockingQueue<PixelWriterPixelPainter.Pixel>(totalPixels);
		painter = new Thread(
				() -> {
					try {

						int takenPixels = 0;
						while (takenPixels < totalPixels) {
							List<Pixel> buffer = new ArrayList<>(CHUNK_SIZE);

							for (int i = 0; i < CHUNK_SIZE && takenPixels < totalPixels; i++) {
								buffer.add(ps.take());
								takenPixels++;
							}

							double takenPixelsSnapShot = takenPixels;
							Platform.runLater(() -> {
								buffer.forEach(p -> pp.setColor(p.x, p.y, p.color));
								progress.setValue(takenPixelsSnapShot / totalPixels);
							});
						}
					} catch (Exception e) { e.printStackTrace(); }
				}, "PixelPainter");
		painter.setDaemon(true);
		painter.start();
	}

	final class Pixel {
		final int x, y;
		final Color color;

		public Pixel(int x, int y, Color color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
	}

	@Override
	public void paint(int x, int y, Color color) {
		ps.add(new Pixel(x, y, color));
	}
}
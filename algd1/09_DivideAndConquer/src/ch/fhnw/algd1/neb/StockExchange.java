package ch.fhnw.algd1.neb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class StockExchange {
	private float[] stock;

	public StockExchange(float[] stock) {
		this.stock = stock;
	}

	public StockExchange() {
		this(new float[0]);
	}

	public void generateStock(int length, float maxDifference) {
		float[] result = new float[length];
		int sign = 1;
		for (int i = 0; i < result.length; ++i) {
			float value = (float) (Math.random() * maxDifference);
			value *= sign;
			result[i] = value;
			if (Math.random() < 0.25) {
				sign *= -1;
			}
		}
		stock = result;
	}

	public void saveStock() {
		final JFileChooser chooser = new JFileChooser();
		int status = chooser.showSaveDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = chooser.getSelectedFile();
			try {
				final ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						new FileOutputStream(selectedFile));
				objectOutputStream.writeObject(stock);
				objectOutputStream.close();
			} catch (Exception ex) {
				System.out
						.println("Error while saving file " + ex.getMessage());
			}
		}
	}

	public void loadStock() {
		final JFileChooser chooser = new JFileChooser();
		int status = chooser.showOpenDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = chooser.getSelectedFile();
			try {
				final ObjectInputStream objectInputStream = new ObjectInputStream(
						new FileInputStream(selectedFile));
				stock = (float[]) objectInputStream.readObject();
				objectInputStream.close();
			} catch (Exception ex) {
				System.out.println("Error while loading file "+ ex.getMessage());
			}
		}
	}

	/**
	 * Linear complexity.
	 * 
	 * @param stockChanges
	 * @return
	 */
	public StockDifference getMaxStockDifference() {
		int left = 0;
		int start = 0;
		int end = 0;
		float maxDiff = 0;
		float curDiff = 0;
		System.out.println(Arrays.toString(stock));
		for (int stockIndex = 0; stockIndex < stock.length; ++stockIndex) {
			final float stockDelta = stock[stockIndex];
			curDiff += stockDelta;
			curDiff = Math.max(0, curDiff);
			if (curDiff == 0) {
				left = stockIndex + 1;
			}
			if (curDiff > maxDiff) {
				maxDiff = curDiff;
				end = stockIndex + 1;
				start = left;
			}
		}
		return new StockDifference(start, end, maxDiff);
	}

	public static class StockDifference {
		public int start;
		public int end;
		public float diff;

		public StockDifference(int start, int end, float diff) {
			
			this.start = start;
			this.end = end;
			this.diff = diff;
		}

		@Override
		public String toString() {
			return "start: " + start + " end: " + end + " diff: " + diff;
		}
	}

	public static void main(String[] args) {
		setLookAndFeel();
		StockExchange stockExchange = new StockExchange();
		stockExchange.generateStock(100000, 250);
		//stockExchange.loadStock();
		StockDifference maxStockDifference = stockExchange.getMaxStockDifference();
		System.out.println(maxStockDifference.toString());
	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
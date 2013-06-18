package ch.fhnw.algd1.StockExchange;

import java.util.Arrays;


public class StockExchange {
	float[] stock;
	StockExchangeGUI view;
	StockExchangeControl control;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		StockExchange se = new StockExchange();
		se.generateStock(100000, 250);

		se.startGUI();
		se.control = new StockExchangeControl(se.view, se);
		
		System.out.println(se.toString());
		long startTime = System.nanoTime();
		System.out.println(se.getBestPeriod().toString());
		long stopTime = System.nanoTime();
		long duration = stopTime-startTime;
		System.out.println("Done after "+duration/1000000000.0+"s");
		
		
	}
	
	public void startGUI() {
		view = new StockExchangeGUI();
	}
	
	public void generateStock(int size, float maxDiff) {
		stock = new float[size];
		float sign = 1;
		for (int i = 0; i < stock.length; i++) {
			sign *= Math.random() < 0.4 ? -1 : 1; 
			stock[i] = (float) (Math.random() * maxDiff * sign);
		}
	}
	
	public StockPeriod getBestPeriod() {
		 StockPeriod cur = new StockPeriod();
		 StockPeriod max = new StockPeriod();
		 
		 while (cur.end < stock.length) {
			cur.diff += stock[cur.end];
			if (cur.diff <= 0) {
				cur.diff = 0; 	
				cur.end++; cur.start = cur.end; 
			}
			else {
				if (cur.diff > max.diff) {
					max.diff = cur.diff;
					max.start = cur.start;
					max.end = cur.end;
				}
				cur.end++;	
			}
				
		 }

		 return max;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(stock);
	}
	
	public static class StockPeriod {
		int start;
		int end;
		float diff;	
		
		public StockPeriod() {
			start = 0; end = 0; diff = 0;
		}
		
		@Override
		public String toString() {
			return ("Start: "+start+", End: "+end+", Win: "+diff+".");
		}
	}

}

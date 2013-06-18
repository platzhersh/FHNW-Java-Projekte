package ch.fhnw.algd1;

import java.util.Arrays;

public class StockExchange {
	float[] stock;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StockExchange se = new StockExchange();
		se.generateStock(5, 1);

		System.out.println(se.toString());
		System.out.println(se.getBestPeriod().toString());
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
	
	private static class StockPeriod {
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

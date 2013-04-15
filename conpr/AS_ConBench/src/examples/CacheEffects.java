package examples;

import conbench.Benchmark;
import conbench.Contention;
//http://ark.intel.com/products/70846
public class CacheEffects {

	static final int N_TIMES = 10000;
	static final int INCREMENTS = 1000;
	
	@Benchmark(N_TIMES)
	public static class FalseSharing {
		public volatile long a;
		public volatile long b;
		
		@Contention({1})
		public void accessA(int nTimes, int nThreads) {
			for(int i=0; i < nTimes; i++) {
				for(int j=0; j < INCREMENTS; j++) {
					a = a + 1;
				}
			}
		}
		
		@Contention({1})
		public void accessB(int nTimes, int nThreads) {
			for(int i=0; i < nTimes; i++) {
				for(int j=0; j < INCREMENTS; j++) {
					b = b + 1;
				}
			}
		}
	}
	
	@Benchmark(N_TIMES)
	public static class PaddedVariables {
		public volatile long a;
		public volatile long p1, p2, p3, p4, p5, p6, p7 = 0;
		public volatile long b;
		
		@Contention({1})
		public void accessA(int nTimes, int nThreads) {
			for(int i=0; i < nTimes; i++) {
				for(int j=0; j < INCREMENTS; j++) {
					a = a + 1;
				}
			}
		}
		
		@Contention({1})
		public void accessB(int nTimes, int nThreads) {
			for(int i=0; i < nTimes; i++) {
				for(int j=0; j < INCREMENTS; j++) {
					b = b + 1;
				}
			}
		}
	}
}

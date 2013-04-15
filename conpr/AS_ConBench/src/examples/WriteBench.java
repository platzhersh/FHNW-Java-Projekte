package examples;

import java.util.concurrent.atomic.AtomicInteger;

import conbench.Benchmark;
import conbench.Contention;

public class WriteBench {
    static final int N_TIMES = 100000000;
    
    @Benchmark(N_TIMES)
    public static class Simple {
        public int cnt;
        @Contention({1,2,4,8})
        public void write(int nTimes, int nThreads) {
            for(int i = 0; i < nTimes; i ++) {
                cnt = i;
            }
        }
    }
    
    @Benchmark(N_TIMES)
    public static class Volatile {
        public volatile int cnt;
        @Contention({1,2,4,8})
        public void write(int nTimes, int nThreads) {
            for(int i = 0; i < nTimes; i ++) {
                cnt = i;
            }
        }
    }
    
    @Benchmark(N_TIMES)
    public static class Atomic {
        public final AtomicInteger cnt = new AtomicInteger();
        @Contention({1,2,4,8})
        public void write(int nTimes, int nThreads) {
            for(int i = 0; i < nTimes; i ++) {
                cnt.set(i);
            }
        }
    }
    
    @Benchmark(N_TIMES)
    public static class Sync {
        public int cnt;
        @Contention({1,2,4,8})
        public void write(int nTimes, int nThreads) {
            for(int i = 0; i < nTimes; i ++) {
                synchronized (this) {
                    cnt = i;
                }
            }
        }
    }
}

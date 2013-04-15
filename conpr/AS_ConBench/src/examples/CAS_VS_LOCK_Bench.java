package examples;

import static conbench.ConBench.burnCycles;

import java.util.concurrent.atomic.AtomicLong;

import conbench.Benchmark;
import conbench.Contention;

public class CAS_VS_LOCK_Bench {
    static final int N_TIMES = 10000;
    static final int LOW = 0;
    static final int HIGH = 10;

    /////////////////// Low contention //////////////////
    
    @Benchmark(N_TIMES)
    public static class LOCK_LOW {
        private final LockTarget t = new LockTarget();

        @Contention({ 1, 2, 4, 8, 16, 32 })
        public void pessimistic(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                t.pessismistic(LOW);
            }
        }
    }

    @Benchmark(N_TIMES)
    public static class CAS_LOW {
        private final AtomicTarget t = new AtomicTarget();

        @Contention({ 1, 2, 4, 8, 16, 32 })
        public void optimistic(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                t.optimistic(LOW);
            }
        }
    }
    
    /////////////////// High contention //////////////////
    
    @Benchmark(N_TIMES)
    public static class LOCK_HIGH {
        private final LockTarget t = new LockTarget();

        @Contention({ 1, 2, 4, 8, 16, 32 })
        public void pessimistic(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                t.pessismistic(HIGH);
            }
        }
    }

    @Benchmark(N_TIMES)
    public static class CAS_HIGH {
        private final AtomicTarget t = new AtomicTarget();

        @Contention({ 1, 2, 4, 8, 16, 32 })
        public void optimistic(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                t.optimistic(HIGH);
            }
        }
    }
}

class AtomicTarget {
    private final AtomicLong value = new AtomicLong();
    
    public long optimistic(int work) {
        while(true) {
            long current = value.get();
            burnCycles(work);
            long newValue = current + 1;
            if(value.compareAndSet(current, newValue)) return newValue;
        }
    }
}

class LockTarget {
    private long value;
    private final Object lock = new Object();
    
    public long pessismistic(int work) {
        synchronized (lock) {
            burnCycles(work);
            value += 1;
            return value;
        }
    }
}

/*
 
 Here are the numbers I get on a quad-Core (8HT) computer:

 == Low Contention == 
 
CAS_LOW: Warming up ...
 Starting benchmark ...
 - Run[0] optimistic(1), Duration: 42ns
 - Run[1] optimistic(2), Duration: 124ns
 - Run[2] optimistic(4), Duration: 57ns
 - Run[3] optimistic(8), Duration: 221ns
 - Run[4] optimistic(16), Duration: 532ns
 - Run[5] optimistic(32), Duration: 455ns


LOCK_LOW: Warming up ...
 Starting benchmark ...
 - Run[0] pessimistic(1), Duration: 42ns
 - Run[1] pessimistic(2), Duration: 95ns
 - Run[2] pessimistic(4), Duration: 195ns
 - Run[3] pessimistic(8), Duration: 881ns
 - Run[4] pessimistic(16), Duration: 1828ns
 - Run[5] pessimistic(32), Duration: 4196ns


 
 == High Contention == 

CAS_HIGH: Warming up ...
 Starting benchmark ...
 - Run[0] optimistic(1), Duration: 75.792µs
 - Run[1] optimistic(2), Duration: 145.465µs
 - Run[2] optimistic(4), Duration: 343.911µs
 - Run[3] optimistic(8), Duration: 983.779µs
 - Run[4] optimistic(16), Duration: 1930.636µs
 - Run[5] optimistic(32), Duration: 4091.714µs


LOCK_HIGH: Warming up ...
 Starting benchmark ...
 - Run[0] pessimistic(1), Duration: 78.563µs
 - Run[1] pessimistic(2), Duration: 179.738µs
 - Run[2] pessimistic(4), Duration: 321.367µs
 - Run[3] pessimistic(8), Duration: 604.078µs
 - Run[4] pessimistic(16), Duration: 1177.615µs
 - Run[5] pessimistic(32), Duration: 2302.323µs

 */




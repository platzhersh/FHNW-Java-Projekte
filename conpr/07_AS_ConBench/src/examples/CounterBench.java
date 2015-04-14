package examples;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

import conbench.Benchmark;
import conbench.Threads;

public class CounterBench {
    static final int N_TIMES = 1000000;

    @Benchmark(N_TIMES)
    public static class SyncCounterBench {
        final SyncCounter c = new SyncCounter();

        @Threads({ 1, 2, 4, 8, 16, 32 })
        public void increment(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                c.increment();
            }
        }
    }
    
    @Benchmark(N_TIMES)
    public static class AtomicFieldUpdaterCounterBench {
        final AtomicFieldUpdaterCounter c = new AtomicFieldUpdaterCounter();

        @Threads({ 1, 2, 4, 8, 16, 32 })
        public void increment(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                c.increment();
            }
        }
    }
    
    @Benchmark(N_TIMES)
    public static class AtomicCounterCASBench {
        final AtomicCounterCAS c = new AtomicCounterCAS();

        @Threads({ 1, 2, 4, 8, 16, 32 })
        public void increment(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                c.increment();
            }
        }
    }
    
    @Benchmark(N_TIMES)
    public static class AtomicCounterBench {
        final AtomicCounter c = new AtomicCounter();

        @Threads({ 1, 2, 4, 8, 16, 32 })
        public void increment(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                c.increment();
            }
        }
    }
}

final class SyncCounter {
    private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        return ++value;
    }
}


final class AtomicFieldUpdaterCounter {
	private static final AtomicLongFieldUpdater<AtomicFieldUpdaterCounter> updater 
	    = AtomicLongFieldUpdater.newUpdater(AtomicFieldUpdaterCounter.class, "value");

	private volatile long value;
	
	public long getValue() {
        return value;
    }

    public long increment() {
        return updater.incrementAndGet(this);
    }
}

final class AtomicCounterCAS {
	private final AtomicLong value = new AtomicLong();
	
	public long getValue() {
        return value.get();
    }

    public long increment() {
        while (true) {
            long current = value.get();
            long next = current + 1;
            if (value.compareAndSet(current, next))
                return next;
        }
    }
}

final class AtomicCounter {
	private final AtomicLong value = new AtomicLong();
	
	public long getValue() {
        return value.get();
    }

    public long increment() {
        return value.incrementAndGet();
    }
}



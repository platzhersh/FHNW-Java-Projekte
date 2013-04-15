package examples;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

import sun.misc.Unsafe;
import conbench.Benchmark;
import conbench.Contention;

/*
 * Required VM Arguments: -Xbootclasspath/p:${project_loc:/06_AS_ConBench}/bin/.
 */
public class CounterBench {
    static final int N_TIMES = 1000000;

    @Benchmark(N_TIMES)
    public static class SyncCounterBench {
        final SyncCounter c = new SyncCounter();

        @Contention({ 1, 2, 4, 8, 16, 32 })
        public void increment(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                c.increment();
            }
        }
    }

    @Benchmark(N_TIMES)
    public static class UnsafeCounterBench {
        final UnsafeCounter c = new UnsafeCounter();

        @Contention({ 1, 2, 4, 8, 16, 32 })
        public void increment(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                c.increment();
            }
        }
    }
    
    @Benchmark(N_TIMES)
    public static class AtomicFieldUpdaterCounterBench {
        final AtomicFieldUpdaterCounter c = new AtomicFieldUpdaterCounter();

        @Contention({ 1, 2, 4, 8, 16, 32 })
        public void increment(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                c.increment();
            }
        }
    }
    
    @Benchmark(N_TIMES)
    public static class AtomicCounterCASBench {
        final AtomicCounterCAS c = new AtomicCounterCAS();

        @Contention({ 1, 2, 4, 8, 16, 32 })
        public void increment(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                c.increment();
            }
        }
    }
    
    @Benchmark(N_TIMES)
    public static class AtomicCounterBench {
        final AtomicCounter c = new AtomicCounter();

        @Contention({ 1, 2, 4, 8, 16, 32 })
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

final class UnsafeCounter {
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;
    private volatile long value = 0;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset(UnsafeCounter.class.getDeclaredField("value"));
        } catch (Exception ex) { 
            throw new Error(ex);
        }
    }

    public long getValue() {
        return value;
    }

    public long increment() {
        while (true) {
            long current = getValue();
            long next = current + 1;
            if (compareAndSwap(current, next))
                return next;
        }
    }

    private boolean compareAndSwap(long expected, long newVal) {
        return unsafe.compareAndSwapLong(this, valueOffset, expected, newVal);
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



package examples;

import static conbench.ConBench.burnCycles;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import conbench.Benchmark;
import conbench.Contention;

public class ReaderWriterBench {
    static final int N_TIMES = 100000;
    
    @Benchmark(N_TIMES)
    public static final class RWLock {
        final ReadWriteLock lock = new ReentrantReadWriteLock();
        final Lock writeLock = lock.writeLock();
        final Lock readLock = lock.readLock();

        @Contention({ 1, 1, 1, 1 })
        public void write(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                writeLock.lock();
                try {
                    burnCycles(1);
                } finally {
                    writeLock.unlock();
                }
            }
        }

        @Contention({ 1, 2, 4, 8 })
        public void read(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                readLock.lock();
                try {
                    burnCycles(1);
                } finally {
                    readLock.unlock();
                }
            }
        }
    }
    
    @Benchmark(N_TIMES)
    public static final class SimpleLock {
        final ReentrantLock lock = new ReentrantLock();

        @Contention({ 1, 1, 1, 1 })
        public void write(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
                lock.lock();
                try {
                    burnCycles(1);
                } finally {
                    lock.unlock();
                }
            }
        }

        @Contention({ 1, 2, 4, 8 })
        public void read(int nTimes, int nThreads) {
            for (int i = 0; i < nTimes; i++) {
            	lock.lock();
                try {
                    burnCycles(1);
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}

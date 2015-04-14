package conbench;

import java.util.List;

/**
 * Describes a benchmark class.
 */
public final class BenchmarkDescriptor {
    /** The benchmark class. */
    public final Class<?> testClass;
    /** Run each benchmark method with this parameter. */
    public final int nTimes;
    /** Descriptors for all @Threads annotated methods. */
    public final List<BenchmarkMethodDescriptor> testMethods;

    public BenchmarkDescriptor(Class<?> testClass, int nTimes, List<BenchmarkMethodDescriptor> testMethods) {
        this.testClass = testClass;
        this.nTimes = nTimes;
        this.testMethods = testMethods;
    }
}

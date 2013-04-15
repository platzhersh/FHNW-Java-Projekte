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
    /** Descriptors for all @Contention annotated methods. */
    public final List<ContentionDescriptor> testMethods;

    public BenchmarkDescriptor(Class<?> testClass, int multiplicator,
            List<ContentionDescriptor> testMethods) {
        this.testClass = testClass;
        this.nTimes = multiplicator;
        this.testMethods = testMethods;
    }
}

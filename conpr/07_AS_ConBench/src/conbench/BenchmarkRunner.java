package conbench;

/**
 * Interface to abstract from benchmark runner implementations.
 */
public interface BenchmarkRunner {
    /**
     * Runs a benchmark and prints results.
     * @param desc the descriptor of the benchmark to run
     */
    void runBenchmark(BenchmarkDescriptor desc);
}

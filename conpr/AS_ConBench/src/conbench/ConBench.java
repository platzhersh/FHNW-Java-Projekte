package conbench;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import examples.CAS_VS_LOCK_Bench;

/**
 * This class runs multithreaded benchmarks. A benchmark is a java class which
 * is annotated with @{@link Benchmark} and which contains at least one public
 * method which takes a single {@code int} parameter and which is annotated with @
 * {@link Contention}.
 */
public class ConBench {

    /**
     * Main entry point.
     * 
     * @param args
     *            are interpreted as class names.
     * @throws Exception
     *             in case of a problem
     */
    public static void main(String[] args) throws Exception {
        // Put your benchmark classes here for easy development
        Class<?>[] benchClasses = { CAS_VS_LOCK_Bench.class };// , WriteBench.class, ReaderWriterBench.class, CounterBench.class, MapBench.class };

        if (args.length > 0) {
            Class<?>[] classes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                classes[i] = Class.forName(args[i]);
            }
            benchClasses = classes;
        }
        runBenchmarks(benchClasses);
    }

    /**
     * Return your {@link BenchmarkRunner} instance here.
     * 
     * @return your {@link BenchmarkRunner}
     */
    public static BenchmarkRunner createRunner() {
        return new ConprRunner();
    }

    /**
     * Runs the given benchmark classes.
     * 
     * @param benchmarkClasses
     *            the benchmark classes
     */
    public static void runBenchmarks(Class<?>... benchmarkClasses) {
        BenchmarkRunner runner = createRunner();
        for (Class<?> benchmarkClass : benchmarkClasses) {
            for (BenchmarkDescriptor desc : analyzeClass(benchmarkClass)) {
                runner.runBenchmark(desc);
            }
        }
    }

    /**
     * Returns a list of {@link BenchmarkDescriptor} for the given class.
     * 
     * @param benchClass
     *            the benchmark class
     * @return a list of {@link BenchmarkDescriptor} for the given class
     */
    public static List<BenchmarkDescriptor> analyzeClass(Class<?> benchClass) {
        if (benchClass.isAnnotationPresent(Benchmark.class)) {
            Benchmark conBench = benchClass.getAnnotation(Benchmark.class);

            List<ContentionDescriptor> descs = new ArrayList<>();

            int nThreadsLength = -1;
            for (Method m : benchClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Contention.class)) {
                    Class<?>[] params = m.getParameterTypes();
                    if (params.length != 2 || !Integer.TYPE.equals(params[0]) || !Integer.TYPE.equals(params[1])) {
                        fail("Method " + benchClass.getSimpleName() + "." + m.getName() + " must have two int parameter!");
                    }

                    Contention tc = m.getAnnotation(Contention.class);
                    if (nThreadsLength != -1 && nThreadsLength != tc.value().length) {
                        fail("All @Contention methods must have equal length thread counts!");
                    }
                    nThreadsLength = tc.value().length;

                    descs.add(new ContentionDescriptor(m, tc.value()));
                }
            }
            if (descs.isEmpty()) {
                fail("No @Contention methods found in " + benchClass.getSimpleName());
            }
            List<BenchmarkDescriptor> result = new LinkedList<>();
            result.add(new BenchmarkDescriptor(benchClass, conBench.value(), descs));
            return result;
        } else {
            List<BenchmarkDescriptor> result = new LinkedList<>();
            for (Class<?> innerClass : benchClass.getDeclaredClasses()) {
                result.addAll(analyzeClass(innerClass));
            }
            return result;
        }
    }

    private static void fail(String msg) {
        throw new IllegalArgumentException(msg);
    }

    /**
     * This is required to avoid that the loop in {@code burnCycles} is
     * optimized away.
     */
     public static int burn = 0;

    /**
     * Burns some cycles times n.
     * 
     * @param n
     *            the multiplier
     */
    public static void burnCycles(int n) {
    	/*try {
			Thread.sleep(0,n);
		} catch (InterruptedException e) {}*/
        for (int i = 0; i < 100000 * n; i++) {
            burn++;
        }
    }
}



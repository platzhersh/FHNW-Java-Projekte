package conbench;

import java.lang.reflect.Method;

/**
 * Describes a benchmark method.
 */
public final class ContentionDescriptor {
    /** The method to execute. */
    public final Method method;
    /** The number of threads for the different runs. */
    public final int[] nThreads;

    public ContentionDescriptor(Method method, int[] nThreads) {
        this.method = method;
        this.nThreads = nThreads;
    }
}

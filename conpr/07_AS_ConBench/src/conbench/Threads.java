package conbench;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as ConBench enabled method. The annotated method is
 * required to accept two int parameters:
 * <ol> 
 *   <li>nTimes - isolates the scenario of interest from the benchmark infrastructure. </li>
 *   <li>nThreads - the number of threads executing this method concurrently. </li>
 * </ol>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Threads {
    /**
     * Returns the array of thread counts under which the annotated method should be executed.
     * @return the array of thread counts
     */
    int[] value();
}

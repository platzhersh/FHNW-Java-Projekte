package conbench;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method as benchmark enabled. The annotated method is
 * required to accept an int nTimes parameter in order to isolate the scenario
 * of interest from the benchmark infrastructure.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Contention {
    /**
     * Returns the array of thread counts under which the annotated method should be executed.
     * @return the array of thread counts
     */
    int[] value();
}

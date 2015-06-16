package jcstress;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult2;

@JCStressTest
@Description("Tests racy assignments.")
@Outcome(id = "[1, 0]", expect = Expect.ACCEPTABLE, desc = "t1 first")
@Outcome(id = "[0, 1]", expect = Expect.ACCEPTABLE, desc = "t2 first")
@Outcome(id = "[1, 1]", expect = Expect.ACCEPTABLE, desc = "Interleaving.")
@Outcome(id = "[0, 0]", expect = Expect.ACCEPTABLE_SPEC, desc = "Reordering or visibility issue.")
@State
public class JMMTest { 
    int x = 0, y = 0;
    int a = 0, b = 0;

    @Actor
    public void thread1() {
      x = 1; 
      b = y;
    } 

    @Actor
    public void thread2() { 
      y = 1;
      a = x;
    }

    @Arbiter
    public void observe(IntResult2 res) {
        res.r1 = a;
        res.r2 = b;
    }
}
package jcstress;

import java.util.concurrent.atomic.AtomicInteger;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult2;

@JCStressTest
@Description("Tests some stuff.")
@Outcome(id = "[0, 0]", expect = Expect.ACCEPTABLE, desc = "t1 first")
@Outcome(id = "[0, 1]", expect = Expect.ACCEPTABLE, desc = "t2 first")
@Outcome(id = "[5, 0]", expect = Expect.ACCEPTABLE, desc = "Interleaving.")
@Outcome(id = "[5, 1]", expect = Expect.ACCEPTABLE_SPEC, desc = "Reordering or visibility issue.")
@State
public class JMMJCStress {
   private AtomicInteger ai = new AtomicInteger(5);
   private int i = 1;

   @Actor
   public void thread1() {
	   i++;  
       ai.set(i);
   }
   
   @Actor
   public void thread2() {
	   int _i = i;         // (1)
       int _ai = ai.get(); // (2)
       System.out.println(_i + " " + _ai);
   }
  
   @Arbiter
   public void observe(IntResult2 res) {
       res.r1 = ai.get();
       res.r2 = i;
   }
   
}

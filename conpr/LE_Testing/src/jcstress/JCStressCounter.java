package jcstress;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult1;

@JCStressTest
@Description("Tests concurrent increments.")
@Outcome(id = {"[10]","[11]","[12]","[13]","[14]","[15]","[16]","[17]","[18]","[19]","[20]"}, 
         expect = Expect.ACCEPTABLE_INTERESTING, desc = "Legal interleavings")
@State
public class JCStressCounter { 
    private volatile int cnt = 0; 

    @Actor
    public void thread1() {
        for(int i = 0; i < 10; i++) {
          cnt++;
        }
    } 

    @Actor
    public void thread2() {
      for(int i = 0; i < 10; i++) {
        cnt++;
      }
    }

    @Arbiter
    public void observe(IntResult1 res) {
        res.r1 = cnt;
    }
}
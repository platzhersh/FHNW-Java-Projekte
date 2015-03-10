package ch.fhnw.conpr.jmm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JMMTest {
    int x = 0, y = 0;
    int a = 0, b = 0;

    static void burnCycles() {
        int n = 10000000;
        while (n > 0) n--;
    }

    class T1 extends Thread {
        @Override
        public void run() {
            x = 1;
            burnCycles();
            b = y;
        }
    }

    class T2 extends Thread {
        @Override
        public void run() {
            y = 1;
            burnCycles();
            a = x;
        }
    }

    public static void main(String[] args) throws Exception {
        Set<String> set = new HashSet<String>();
        final JMMTest t = new JMMTest();

        while (set.size() < 4) {
            t.a = 0; t.b = 0;
            t.x = 0; t.y = 0;
            Thread t1 = t.new T1();
            Thread t2 = t.new T2();
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            String s = "(" + t.a + "," + t.b + ")";
            if (!set.contains(s)) {
                set.add(s);
                System.out.println(new Date() + ":  " + s);
            }
        }
    }
}

/*
 * T1 vor T2: (1,0) 
 * T2 vor T1: (0,1) 
 * T1 und T2 interleaved: (1,1)
 * Visibilityproblem: (0,0)
 * 
 */

//Thu Mar 05 21:48:39 CET 2015:  (1,1)
//Thu Mar 05 21:48:39 CET 2015:  (1,0)
//Thu Mar 05 21:48:39 CET 2015:  (0,1)
//Thu Mar 05 21:51:31 CET 2015:  (0,0)


package le;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

class MyThreadPoolExecutor implements Executor {
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    public void execute(Runnable r) {
        queue.offer(r);
    }

    public MyThreadPoolExecutor(int size) {
        for (int i = 0; i < size; i++) activate();
    }

    private void activate() {
        new Thread(() -> {
             try {
                 while (true) {
                     queue.take().run();
                 }
             } catch (InterruptedException e) {
                 // die
             }
         }).start();
    }
}
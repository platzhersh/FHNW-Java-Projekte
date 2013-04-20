package le;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

class ThreadPoolExecutor implements Executor {
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    public void execute(Runnable r) {
        queue.offer(r);
    }

    public ThreadPoolExecutor(int size) {
        for (int i = 0; i < 10; i++)
            activate();
    }

    private void activate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        queue.take().run();
                    }
                } catch (InterruptedException e) {
                    // die
                }
            }
        }).start();
    }
}
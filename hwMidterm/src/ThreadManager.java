public class ThreadManager implements Runnable {

    private ThreadPool pool;
    private boolean running = true;

    public ThreadManager(ThreadPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        startPool();
    }

    public void startPool() {
        while(running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    private void increaseThreadPool() {

    }

    private void decreaseThreadPool() {

    }

    public void stopPool() {
        running = false;
    }

    public void ThreadFinished(Thread thread) {
        pool.
    }
}

import java.util.LinkedList;

public class ThreadManager implements Runnable {

    private ThreadPool pool;
    LinkedList<ClientInfo> queue;
    private boolean running = true;

    public ThreadManager(int maxCapacity, LinkedList<ClientInfo> queue) {
        this.pool = new ThreadPool(maxCapacity, this);
        this.queue = queue;
    }

    @Override
    public void run() {
        while(running) {
            try {
                if (!queue.isEmpty()) {
                    Capitalizer newCap = pool.getAvailableCapitalizer();
                    if (newCap != null) {
                        ClientInfo info = queue.removeLast();
                        newCap.setClient(info.getSocket(), info.getClientNumber());
                    }
                }
                balancePool();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                stopPool();
            }
        }
        pool.stopAll();
    }

    private void balancePool() {
        if (pool.getTotalCapitalizerCount() + queue.size() <= 10 && pool.getTotalCapitalizerCount() != 5) {
            pool.setTotalCapitalizerCount(5);
            System.out.println("Total in queue: " + queue.size());
        } else if (pool.getTotalCapitalizerCount() + queue.size() <= 20 && pool.getTotalCapitalizerCount() + queue.size() > 10 && pool.getTotalCapitalizerCount() != 10) {
            pool.setTotalCapitalizerCount(10);
            System.out.println("Total in queue: " + queue.size());
        } else if (pool.getTotalCapitalizerCount() + queue.size() < 50 && pool.getTotalCapitalizerCount() + queue.size() > 20 && pool.getTotalCapitalizerCount() != 20) {
            pool.setTotalCapitalizerCount(20);
            System.out.println("Total in queue: " + queue.size());
        } else if(pool.getTotalCapitalizerCount() + queue.size() >= 50 && pool.getTotalCapitalizerCount() != 40) {
            pool.setTotalCapitalizerCount(40);
            System.out.println("Total in queue: " + queue.size());
        }
    }

    public void stopPool() {
        running = false;
    }

    public void capitalizerFinished(Capitalizer capitalizer) {
        pool.capitalizerFinished(capitalizer);
    }
}

import java.util.HashSet;
import java.util.LinkedList;

public class ThreadPool {
    private int maxCapacity;
    private ThreadManager manager;
    private HashSet<Capitalizer> runningPool;
    private HashSet<Capitalizer> availablePool;

    public ThreadPool(int maxCapacity, ThreadManager manager) {
        this.maxCapacity = maxCapacity;
        this.manager = manager;
        runningPool = new HashSet<>();
        availablePool = new HashSet<>();
    }

    public void stopAll() {
        for (Capitalizer t : runningPool) {
            t.interrupt();
            t.closeSocket();
        }
        for (Capitalizer t : availablePool) {
            t.interrupt();
        }
        for (Capitalizer t : runningPool) {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }
        for (Capitalizer t : availablePool) {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }
        System.out.println("running pool pre: " + runningPool);
        System.out.println("available pool pre: " + availablePool);
        runningPool.clear();
        availablePool.clear();
        System.out.println("running pool post: " + runningPool);
        System.out.println("available pool post: " + availablePool);
    }

    public Capitalizer getAvailableCapitalizer() {
        if (availablePool.isEmpty()) return null;
        Capitalizer capitalizer = availablePool.iterator().next();
        availablePool.remove(capitalizer);
        runningPool.add(capitalizer);
        synchronized (capitalizer) {
            capitalizer.notify();
        }
        return capitalizer;
    }

    public void capitalizerFinished(Capitalizer capitalizer) {
        synchronized (capitalizer) {
            if (runningPool.remove(capitalizer)) {
                try {
                    capitalizer.wait();
                } catch (InterruptedException e) {

                } finally {
                    availablePool.add(capitalizer);
                }
            }
        }
    }

    public int getTotalCapitalizerCount() {
        return availablePool.size() + runningPool.size();
    }

    public void doubleThreadCount() {
        setTotalCapitalizerCount(2 * getTotalCapitalizerCount());
    }

    public void halfThreadCount() {
        setTotalCapitalizerCount(getTotalCapitalizerCount() / 2);
    }

    public void setTotalCapitalizerCount(int count) {
        System.out.println("Previous pool size: " + getTotalCapitalizerCount());
        int currentCount = getTotalCapitalizerCount();
        while (currentCount != count && maxCapacity >= currentCount) {
            if (count > currentCount) {
                Capitalizer newCap = new Capitalizer(manager);
                newCap.start();
                availablePool.add(newCap);
            } else {
                if (availablePool.isEmpty()) {
                    Capitalizer capitalizer = runningPool.iterator().next();
                    capitalizer.interrupt();
                    capitalizer.closeSocket();
                    runningPool.remove(capitalizer);
                } else {
                    Capitalizer capitalizer = availablePool.iterator().next();
                    capitalizer.interrupt();
                    availablePool.remove(capitalizer);
                }
            }
            currentCount = getTotalCapitalizerCount();
        }
        System.out.println("New pool size:  " + getTotalCapitalizerCount());
        System.out.println("Total used:  " + runningPool.size());
        System.out.println("Total available:  " + availablePool.size());
    }
}

import java.util.HashSet;
import java.util.LinkedList;

public class ThreadPool {
    private int maxCapacity;
    private HashSet<Capitalizer> runningPool;
    private HashSet<Capitalizer> availablePool;

    public ThreadPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void stopAll() {
        for (Capitalizer t : runningPool) {
            t.interrupt();
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

    }

    public Capitalizer getAvailableCapitalizer() {
        Capitalizer capitalizer = availablePool.iterator().next();
        availablePool.remove(capitalizer);
        capitalizer.start();
        runningPool.add(capitalizer);
        return capitalizer;
    }

    public void capitalizerFinished(Capitalizer capitalizer) {
        if (runningPool.remove(capitalizer)) {
            capitalizer.interrupt();
            availablePool.add(capitalizer);
        }
    }

    public int getTotalCapitalizerCount() {
        return availablePool.size() + runningPool.size();
    }

    public void setTotalCapitalizerCount(int count) {
        int currentCount = getTotalCapitalizerCount();
        while (currentCount != count && maxCapacity > getTotalCapitalizerCount()) {
            if (count < currentCount) {
                availablePool.add(new Capitalizer());
            } else {
                if(availablePool.isEmpty()) {
                    Capitalizer capitalizer = runningPool.iterator().next();
                    capitalizer.interrupt();
                    runningPool.remove(capitalizer);
                } else {
                    Capitalizer capitalizer = availablePool.iterator().next();
                    capitalizer.interrupt();
                    availablePool.remove(capitalizer);
                }
            }
        }
    }
}

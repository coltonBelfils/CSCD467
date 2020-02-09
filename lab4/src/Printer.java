public class Printer implements Runnable {
    private int id;
    private TurnMonitor monitor;
    private int maxCount;

    public Printer(int id, TurnMonitor monitor, int maxCount) {
        this.monitor = monitor;
        this.id = id;
        this.maxCount = maxCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < maxCount; i++) {
            monitor.waitTurn(id);
            System.out.println("Printer #" + id + " --> " + i);
            monitor.incrementTurn();
        }
    }
}

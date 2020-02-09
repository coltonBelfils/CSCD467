public class TurnMonitor {
    private int isTurn;
    private int threadCount;

    public TurnMonitor(int threadCount) {
        this.isTurn = 0;
        this.threadCount = threadCount;
    }

    public synchronized void incrementTurn() {
        isTurn = (isTurn + 1) % threadCount;
        notifyAll();
    }

    public synchronized void waitTurn(int id) {
        while (id != isTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

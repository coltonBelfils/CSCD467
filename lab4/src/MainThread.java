public class MainThread {
    public static void main(String[] args) {
        int threadCount = Integer.parseInt(args[0]);
        int maxCount = Integer.parseInt(args[1]);

        TurnMonitor turn = new TurnMonitor(threadCount);

        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new Printer(i, turn, maxCount));
        }

        for (Thread t : threads) {
            t.start();
        }
    }
}

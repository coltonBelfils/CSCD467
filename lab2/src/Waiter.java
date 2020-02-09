public class Waiter implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Printer has already got his work half done!");
        }
        System.out.println("Waiter has done all its work, terminating.");
    }
}

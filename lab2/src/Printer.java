public class Printer implements Runnable {
    Thread waiter;

    public Printer(Thread waiter) {
        this.waiter = waiter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println("Printer number: " + i);
            if (i == 25) {
                waiter.interrupt();
            }
        }
    }
}

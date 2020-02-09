public class mainThread {
    Waiter waiter;
    Printer printer;
    Thread waiterThread;
    Thread printerThread;

    public static void main(String[] args) {
        Thread waiter;
        Thread printer;

        waiter = new Thread(new Waiter());
        printer = new Thread(new Printer(waiter));

        waiter.start();
        printer.start();

        try {
            waiter.join();
            printer.join();
        } catch (InterruptedException e) {
            System.out.println("main thread interrupted before finished");
        }

        System.out.println("Waiter and Printer are done");
    }
}

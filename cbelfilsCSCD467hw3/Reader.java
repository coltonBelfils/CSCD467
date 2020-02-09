import java.util.Scanner;


public class Reader implements Runnable {
    private Scanner scan;
    private SharedQueue queue;

    public Reader(Scanner scan, SharedQueue queue) {
        this.scan = scan;
        this.queue = queue;
    }

    @Override
    public void run() {
        String read;
        while (scan.hasNextLine()) {
            read = scan.nextLine();
            queue.enqueue(read);
        }
        queue.setAsComplete();
        scan.close();
    }
}

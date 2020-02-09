import javax.swing.*;

public class DisplayPrinter implements Runnable {
    private JTextArea out;

    DisplayPrinter(JTextArea out) {
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {
            try {
                out.append("Thread: " + Thread.currentThread().getName() + "\n");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        out.append("Thread: " + Thread.currentThread().getName() + " STOPPED\n");
    }
}

import javax.swing.*;

public class AsyncPrinter implements Runnable {
    private JTextArea out;
    private String message;

    AsyncPrinter(JTextArea out, String message) {
        this.out = out;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            while(true) {
                out.append(message + "\n");
                Thread.sleep(200);
            }
        } catch (InterruptedException ignored) {}

        /*
        while (true) {
            try {
                out.append(message);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                break;
            }
        }

         */
    }
}

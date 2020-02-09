import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;


public class Reader implements Runnable {
    private BufferedReader bufferedReader;
    private SharedQueue queue;

    public Reader(BufferedReader bufferedReader, SharedQueue queue) {
        this.bufferedReader = bufferedReader;
        this.queue = queue;
    }

    @Override
    public void run() {
        String read;
        try {
            while ((read = bufferedReader.readLine()) != null) {
                queue.enqueue(read);
            }
            queue.setAsComplete();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

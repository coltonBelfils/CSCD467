import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Searcher implements Runnable {
    private String pattern;
    private SharedQueue queue;

    public Searcher(String pattern, SharedQueue queue) {
        this.pattern = pattern;
        this.queue = queue;
    }

    @Override
    public void run() {
        String read;
        while (!queue.isComplete()) {
            read = queue.deQueue();
            queue.addCount(searchLine(read));
        }
    }

    private int searchLine(String text) {
        Pattern pattern = Pattern.compile(this.pattern);
        Matcher matcher = pattern.matcher(text);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}

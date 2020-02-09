public class Counter {
	private int c = 0;

    public synchronized void increment( int n) {
        // write me here
        c += n;
    }

    public synchronized int total() {
        return c;
    }
}

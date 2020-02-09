import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ParallelSearchCoarse {
	public static void main(String[] args) throws InterruptedException {
		if( args.length < 2) {
			System.out.println("Usage: Java ParallelSearchCoarse FileName Pattern");
			System.exit(0);
		}
		
		String fname = args[0];         // fileName = files/wikipedia2text-extracted.txt
		String pattern = args[1];       // pattern = "(John) (.+?) ";
		long start = System.currentTimeMillis();
		
		File fin = new File(fname);
		if (!fin.exists()) {
			System.out.println("File, " + fname + ", does not exist");
			return;
		}
		Scanner scanner;
		try {
			scanner = new Scanner(fin);
		} catch (FileNotFoundException e) {
			System.out.println("File, " + fname + ", does not exist");
			return;
		}

		SharedQueue queue = new SharedQueue();

		Thread reader = new Thread(new Reader(scanner, queue));
		Thread searcher = new Thread(new Searcher(pattern, queue));
		reader.start();
		searcher.start();
		
		reader.join();
		searcher.join();

		System.out.println(queue.getCount() + " pattern matches found");

		long end = System.currentTimeMillis();
		System.out.println("Time cost for concurrent solution is " + (end - start));
		
	}

}

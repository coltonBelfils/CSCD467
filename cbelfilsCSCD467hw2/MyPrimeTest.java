public class MyPrimeTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		if (args.length < 3) {
			System.out.println("Usage: MyPrimeTest numThread low high \n");
			return;
		}
		int nthreads = Integer.parseInt(args[0]);
		int low = Integer.parseInt(args[1]);
		int high = Integer.parseInt(args[2]);
		Counter c = new Counter();
		
		//test cost of serial code
		long start = System.currentTimeMillis();
		int numPrimeSerial = SerialPrime.numSerailPrimes(low, high);
		long end = System.currentTimeMillis();
		long timeCostSer = end - start;
		System.out.println("Time cost of serial code: " + timeCostSer + " ms.");
		
		//test of concurrent code
		// **************************************
        // Write me here
		start = System.currentTimeMillis();
		int totalNums = high - low;
		int totalNumsPer = totalNums / nthreads;
		int currentBottomNum = low;
		int currentTopNum = low + totalNumsPer;

		Thread[] threads = new Thread[nthreads];
		for (int i = 0; i < nthreads; i++) {
			if (i == nthreads - 1) {
				currentTopNum = high;
			}
			threads[i] = new Thread(new ThreadPrime(currentBottomNum, currentTopNum, c));
			threads[i].start();
			currentBottomNum = currentTopNum + 1;
			currentTopNum = currentBottomNum + totalNumsPer;
		}

		for (int i = 0; i < nthreads; i++) {
			threads[i].join();
		}
		end = System.currentTimeMillis();
		long timeCostCon = end - start;
		// **************************************
		System.out.println("Time cost of parallel code: " + timeCostCon + " ms.");
		System.out.format("The speedup ration is by using concurrent programming: %5.2f. %n", (double)timeCostSer / timeCostCon);
		
		System.out.println("Number prime found by serial code is: " + numPrimeSerial);
		System.out.println("Number prime found by parallel code is " + c.total());
	}
		

}

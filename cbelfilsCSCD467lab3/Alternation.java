public class Alternation {

    boolean isT1turn;
	ThreadTurn t1Turn = new ThreadTurn();
    Thread t1;
    Thread t2;

    public Alternation() {

        isT1turn = true;
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 50; i += 2) {
                    while (!t1Turn.isT1Turn()) ;  //guarded block
                    System.out.println("T1=" + i);
                    t1Turn.setT1Turn(false);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }//end of for
            }
        });
        t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 2; i <= 50; i += 2) {
                    while (t1Turn.isT1Turn()) ;   //guarded block
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("T2=" + i);
                    t1Turn.setT1Turn(true);
                }
            }
        });
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Alternation();
    }
}



public class ThreadTurn {
    private boolean isT1Turn = true;

    public synchronized void setT1Turn(boolean isT1Turn) {
        this.isT1Turn = isT1Turn;
    }

    public synchronized boolean isT1Turn() {
        return isT1Turn;
    }
}

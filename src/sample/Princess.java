package sample;

public class Princess extends Thread {
    private WindowController controller;
    private final int kingdomId;
    private King king;
    private int loveTime;

    public Princess(King king, int loveTime, int kingdomId) {
        this.king = king;
        this.loveTime = loveTime;
        this.kingdomId = kingdomId;
    }

    @Override
    public void run() {
        while(true) {
            controller = Main.loader.getController();
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
            } finally {
                controller.addEvent("Princess received jewelery. She's making our king happy!",kingdomId);
                synchronized (king) {
                    king.notify();
                }
            }
        }
    }

}

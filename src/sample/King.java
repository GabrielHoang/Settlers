package sample;

public class King extends Thread {
    WindowController controller;
    private final int kingdomId;
    private int leadership = 0;

    public King(int kingdomId) {
        this.kingdomId = kingdomId;
    }

    @Override
    public void run() {
        controller = Main.loader.getController();
        while (true) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                } finally {
                    synchronized (this) {
                        leadership+=5;
                        controller.addEvent("King leadership rises. +5 attack for troops", kingdomId);
                    }
                }
            }
        }
    }

    public int getLeadership() {
        return leadership;
    }
}

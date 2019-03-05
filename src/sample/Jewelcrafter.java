package sample;

public class Jewelcrafter extends Thread implements Hunger{
    private final int kingdomId;
    private final Princess princess;
    private int workingTime;
    private WindowController controller;
    private int currentHunger=0;
    private final int hungerGainedByWorking = 25;

    public Jewelcrafter(Princess princess, int workingTime, int kingdomId) {
        this.princess=princess;
        this.workingTime=workingTime;
        this.kingdomId=kingdomId;
    }

    @Override
    public void run() {
        controller = Main.loader.getController();
        while(true) {
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
            }
            if(currentHunger < hungerGainedByWorking) {
                try {
                    controller.addEvent("Jewelcrafter received gold and starts crafting...", kingdomId);
                    currentThread().sleep(Main.rand.nextInt(workingTime)+workingTime);
                    currentHunger+=hungerGainedByWorking;
                } catch (InterruptedException e) {
                } finally {
                    synchronized (princess) {
                        //push jewelery to princess
                        princess.notify();
                    }
                }
            }
            else {
                controller.addEvent("Jewelcrafter is starving!",kingdomId);
            }

        }
    }

    @Override
    public void decreaseCurrentHunger(int hunger) {
        currentHunger-=hunger;
    }

}

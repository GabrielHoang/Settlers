package sample;

public class Miner extends Thread implements Hunger{
    private WindowController controller;
    private final int kingdomId;
    private int workingTime;
    private int currentHunger = 0;
    private final int hungerGainedByWorking = 35;
    IronMaster ironMaster;

    public Miner(IronMaster ironMaster, int workingTime, int kingdomId) {
        this.ironMaster = ironMaster;
        this.workingTime = workingTime;
        this.kingdomId = kingdomId;
    }

    @Override
    public void run() {
        this.controller = (WindowController)Main.loader.getController();
        while(true) {
            if(currentHunger < 40) {
                try { //digging working time + random of it
                    Thread.sleep(Main.rand.nextInt(workingTime) + workingTime);
                    currentHunger+=hungerGainedByWorking;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    int diggedStone = Main.rand.nextInt(3);
                    if (diggedStone == 0) {
                        //coal
                        controller.addEvent("Miner digged out coal.", kingdomId);
                        ironMaster.receiveCoal();
                    } else if (diggedStone == 1) {
                        //ore
                        controller.addEvent("Miner digged out ore.", kingdomId);
                        ironMaster.receiveOre();
                    } else {
                        //gold
                        controller.addEvent("Miner digged out gold.", kingdomId);
                        ironMaster.receiveGold();
                    }
                }
                synchronized (ironMaster) {
                    ironMaster.notify();
                }
            } else {
                controller.addEvent("Miner is starving - can't work!", kingdomId);
            }
            //rest
            try {
                currentThread().sleep(3*workingTime);
            } catch (InterruptedException e) {
            }

        }
    }

    @Override
    public void decreaseCurrentHunger(int hunger) {
        this.currentHunger-=hunger;
    }

}

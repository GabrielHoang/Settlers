package sample;

public class Farmer extends Thread {
    Miner miner;
    IronMaster ironMaster;
    Jewelcrafter jewelcrafter;
    QuarterMaster quarterMaster;

    private int workingTime;
    private final int kingdomId;
    private WindowController controller;

    public Farmer(Miner miner, IronMaster ironMaster, Jewelcrafter jewelcrafter, QuarterMaster quarterMaster, int workingTime, int kingdomId) {
        this.miner = miner;
        this.ironMaster = ironMaster;
        this.jewelcrafter = jewelcrafter;
        this.quarterMaster = quarterMaster;
        this.workingTime = workingTime;
        this.kingdomId = kingdomId;
    }

    @Override
    public void run() {
    controller = Main.loader.getController();
        while(true) {
            if(Main.rand.nextInt(2)==0) {
                //wheat
                try {
                    controller.addEvent("Farmer planted wheat",kingdomId);
                    currentThread().sleep(Main.rand.nextInt(workingTime)+2*workingTime);
                    controller.addEvent("Farmer mows down wheat",kingdomId);
                    currentThread().sleep(workingTime);
                    controller.addEvent("Wheat is ready for kingdom!",kingdomId);
                } catch (InterruptedException e) {
                }
                miner.decreaseCurrentHunger(100);
                ironMaster.decreaseCurrentHunger(80);
                jewelcrafter.decreaseCurrentHunger(60);
                if(kingdomId == 0) {
                    for (Warrior w: Main.warriors) {
                        w.decreaseCurrentHunger(130);
                    }
                } else {
                    for (Warrior w : Main.warriors2) {
                        w.decreaseCurrentHunger(130);
                    }
                }
            } else {
                //meat
                try {
                    controller.addEvent("Farmer breeds new sheeps",kingdomId);
                    currentThread().sleep(Main.rand.nextInt(workingTime)+4*workingTime);
                    controller.addEvent("Farmer eviscerates sheeps",kingdomId);
                    currentThread().sleep(workingTime);
                    controller.addEvent("Meat is ready for kingdom!",kingdomId);
                } catch (InterruptedException e) {
                }

                synchronized (miner) {
                    miner.decreaseCurrentHunger(150);
                }
                synchronized (ironMaster) {
                    ironMaster.decreaseCurrentHunger(105);
                }
                synchronized (jewelcrafter) {
                    jewelcrafter.decreaseCurrentHunger(90);
                }
                if(kingdomId == 0) {
                    synchronized (Main.warriors) {
                        for (Warrior w: Main.warriors) {
                            w.decreaseCurrentHunger(195);
                        }
                    }
                } else {
                    synchronized (Main.warriors2) {
                        for (Warrior w : Main.warriors2) {
                            w.decreaseCurrentHunger(195);
                        }
                    }
                }
            }
        }
    }
}

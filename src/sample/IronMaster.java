package sample;


public class IronMaster extends Thread implements Hunger{

    private WindowController controller;
    private final int kingdomId;
    private final Jewelcrafter jewelcrafter;
    private final QuarterMaster quarterMaster;

    private int currentHunger=0;
    private final int hungerGainedByWorking = 35;
    private int workingTime;
    boolean isCoal=false;
    boolean isOre=false;
    boolean isGold=false;

    public int getCoalAmount() {
        return coalAmount;
    }

    public int getOreAmount() {
        return oreAmount;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    int coalAmount = 0;
    int oreAmount = 0;
    int goldAmount = 0;

    public IronMaster (Jewelcrafter jewelcrafter, QuarterMaster quarterMaster, int workingTime, int kingdomId) {
        this.jewelcrafter = jewelcrafter;
        this.quarterMaster = quarterMaster;
        this.workingTime = workingTime;
        this.kingdomId = kingdomId;
    }

    public synchronized void receiveCoal() {
        isCoal = true;
        coalAmount++;
        controller.addEvent("Transferring coal to IronMaster", kingdomId);
    }

    public synchronized void receiveOre() {
        isOre = true;
        oreAmount++;
        controller.addEvent("Transferring ore to IronMaster", kingdomId);
    }

    public synchronized void receiveGold() {
        isGold = true;
        goldAmount++;
        controller.addEvent("Transferring gold to IronMaster", kingdomId);
    }



    @Override
    public void run() {
        this.controller = Main.loader.getController();

        while(true) {
            synchronized (this) {
                try{
                    if((isCoal && isOre) || (isCoal && isGold)) {
                        continue;
                    } else {
                        this.wait();
                    }

                } catch (InterruptedException e) {
                }
            }
            if(currentHunger<hungerGainedByWorking) {
                if (isCoal && isGold) {
                    //create gold
                    try {
                        currentThread().sleep(Main.rand.nextInt(workingTime) + workingTime);
                        currentHunger += hungerGainedByWorking;
                        coalAmount--;
                        goldAmount--;

                        controller.addEvent("Ironmaster created gold", kingdomId);
                    } catch (InterruptedException e) {
                    }
                    synchronized (jewelcrafter) {
                        //push gold to jewelcrafter
                        jewelcrafter.notify();
                    }
                } else if(isCoal && isOre) {
                    //create steel
                    try {
                        currentThread().sleep(Main.rand.nextInt(workingTime)+workingTime);
                        currentHunger+=hungerGainedByWorking;
                        coalAmount--;
                        oreAmount--;
                        controller.addEvent("Ironmaster created steel", kingdomId);
                    } catch (InterruptedException e) {
                    }
                    synchronized (quarterMaster) {
                        quarterMaster.notify();
                    }
                }
                else {
                    //not enough resources
                    continue;
                }
            } else {
                controller.addEvent("IronMaster is starving!", kingdomId);
            }

        }



    }

    @Override
    public void decreaseCurrentHunger(int hunger) {
        currentHunger-=hunger;
    }

}

package sample;

public class QuarterMaster extends Thread {
    private final int kingdomID;
    WindowController controller;
    private int workingTime;

    public QuarterMaster(int workingTime, int kingdomID) {
        this.workingTime = workingTime;
        this.kingdomID = kingdomID;
    }

    @Override
    public void run() {
        controller = Main.loader.getController();
        while(true) {
            //wait for steel
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
            }
            //make weapons
            try {
                currentThread().sleep(Main.rand.nextInt(workingTime)+workingTime);
                if(Main.rand.nextInt(2) == 0) {
                    controller.addEvent("Quartermaster received steel and creates sharpened sword!",kingdomID);
                    currentThread().sleep(Main.rand.nextInt(workingTime)+workingTime);
                    controller.addEvent("Quartermaster gives sharpened sword to warrior", kingdomID);
                    Warrior temp;
                    if(kingdomID == 0) {
                        synchronized (temp = Main.warriors.get(Main.rand.nextInt(Main.warriors.size()))) {
                         if(!temp.isEnhancedWeapon()) temp.sharpSword();
                        }
                    } else {
                        synchronized (temp = Main.warriors2.get(Main.rand.nextInt(Main.warriors2.size()))) {
                            if(!temp.isEnhancedWeapon()) temp.sharpSword();
                        }
                    }
                } else {
                    controller.addEvent("Quartermaster received steel and creates enhanced armor!",kingdomID);
                    currentThread().sleep(Main.rand.nextInt(workingTime)+workingTime);
                    controller.addEvent("Quartermaster gives enhanced armor to warrior", kingdomID);
                    Warrior temp;
                    if(kingdomID == 0) {
                        synchronized (temp = Main.warriors.get(Main.rand.nextInt(Main.warriors.size()))) {
                            if(!temp.isEnhancedArmor()) temp.armorUp();
                        }
                    } else {
                        synchronized (temp = Main.warriors2.get(Main.rand.nextInt(Main.warriors2.size()))) {
                            if(!temp.isEnhancedArmor()) temp.armorUp();
                        }
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }
}

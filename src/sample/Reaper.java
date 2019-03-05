package sample;

import java.util.ArrayList;

public class Reaper extends Thread {

    ArrayList<Warrior> war1;
    ArrayList<Warrior> war2;

    public Reaper(ArrayList<Warrior> war1, ArrayList<Warrior> war2) {
        this.war1 = war1;
        this.war2 = war2;
    }

    @Override
    public void run() {
        while (true) {

            synchronized (war1) {
                for (Warrior w : war1) {
                    if (w.returnHp() <= 0) {
                        w.setWarriorAlive(false);
                        war1.remove(w);
                    }
                }
            }
            synchronized (war2) {
                for (Warrior w : war2) {
                    if (w.returnHp() <= 0) {
                        w.setWarriorAlive(false);
                        war2.remove(w);
                    }
                }
            }
            try {
                currentThread().sleep(5000);
            } catch (InterruptedException e) {
            }
        }
    }
}

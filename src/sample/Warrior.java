package sample;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Warrior extends Thread implements Hunger {

    private CountDownLatch myLatch;
    private final int kingdomId;
    private int restAfterFightTime;
    private King king;
    ArrayList<Warrior> enemy;
    private int currentHunger=0;
    private int hungerGainedByFight = 70;

    public void setWarriorAlive(boolean alive) {
        isAlive = alive;
    }

    private boolean isAlive = true;
    private int attack;
    private int defense;
    private boolean enhancedArmor;
    private boolean enhancedWeapon;
    private int hp;

    public boolean isEnhancedArmor() {
        return enhancedArmor;
    }

    public boolean isEnhancedWeapon() {
        return enhancedWeapon;
    }



    public Warrior(int kingdomId, King king, ArrayList<Warrior> enemy, int attack, int defense, int hp, int restAfterFightTime, boolean enhancedArmor, boolean enhancedWeapon, CountDownLatch latch) {
        this.kingdomId = kingdomId;
        this.king = king;
        this.enemy = enemy;
        this.attack = attack;
        this.defense = defense;
        this.enhancedArmor = enhancedArmor;
        this.enhancedWeapon = enhancedWeapon;
        this.hp = hp;
        this.restAfterFightTime = restAfterFightTime;
        this.myLatch = latch;
    }

    public synchronized void receiveHit(int hit) {
        synchronized (this) {
            if(isAlive) {
                //if has upgrades
                if(enhancedArmor) {
                    hp-=(2*defense)-hit;
                }
                else {
                //standard armor
                hp-=hit;
                }
                //remove from army if is dead now
                if(hp<=0) {
                    this.isAlive = false;
                    if(kingdomId == 0) {
                        Main.warriors.remove(this);
                    } else {
                        Main.warriors2.remove(this);
                    }
                }
            }
        }
    }

    public synchronized void attackEnemy (Warrior enemy) {
        synchronized (enemy) {
            if(enemy.isAlive) {
                int overallDmg = attack + king.getLeadership();
                if(enhancedWeapon) overallDmg += 2* attack;
                enemy.receiveHit(overallDmg);
            }
        }
    }

    public synchronized void armorUp() {
        enhancedArmor = true;
    }

    public synchronized void sharpSword() {
        enhancedWeapon = true;
    }

    @Override
    public void run() {
        //wait with war before every warrior is initiated
        try {
            myLatch.await();
        } catch (InterruptedException e) {
        }
        while(isAlive && enemy.size() > 0) {
            if(currentHunger < hungerGainedByFight) {
                currentHunger-=hungerGainedByFight;
                Warrior myTarget;
                synchronized (myTarget=enemy.get(Main.rand.nextInt(enemy.size()))) {
                    attackEnemy(myTarget);
                }
                try {
                    currentThread().sleep(2*restAfterFightTime);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    //TEST
    public int returnHp() {
        return hp;
    }




    @Override
    public void decreaseCurrentHunger(int hunger) {
        currentHunger-=hunger;
    }

}

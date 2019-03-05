package sample;

public class Kingdom{
    private int warriorCount;
    private final int kingdomId;
    private Kingdom enemyKingdom = null;
    private IronMaster ironMaster;
    private King king;

    public Kingdom(int warriorCount, int kingdomId, King king, IronMaster ironMaster) {
        this.warriorCount = warriorCount;
        this.kingdomId = kingdomId;
        this.king = king;
        this.ironMaster = ironMaster;
        //initialize warriors with no enhancements
        for(int i = 0; i < warriorCount; i++) {
            if(kingdomId == 0) {
                Main.warriors.add(new Warrior(0,king,Main.warriors2, 10,4,50,4000,false, false, Main.warriorsStart));
            } else {
                Main.warriors2.add(new Warrior(1,king,Main.warriors, 10,4,50,4000,false, false, Main.warriorsStart));
            }

        }
    }
    public void setEnemyKingdom(Kingdom enemyKingdom) {
        this.enemyKingdom = enemyKingdom;
    }

    public String showState() {
        if(kingdomId == 0) {
            warriorCount = Main.warriors.size();
        } else {
            warriorCount = Main.warriors2.size();
        }
        return "Warriors left: " + warriorCount +
                "\nKings` leadership: " + king.getLeadership() +
                "\nOre: " + ironMaster.getOreAmount() +
                "\nGold: " + ironMaster.getGoldAmount() +
                "\nCoal: " + ironMaster.getCoalAmount();
    }

}

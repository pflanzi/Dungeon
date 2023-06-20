package ecs.entities.monster;

public class BossMonsterP2 extends BossMonster {

    private static final int healthpoints = 10;
    private static final int dmg = 3;
    private static final float xSpeed = 0.05f;
    private static final float ySpeed = 0.05f;
    private static final long XPonDeath = 10;

    private static final String pathToIdleLeft = "monster/BossMonster2/idleLeft";
    private static final String pathToIdleRight = "monster/BossMonster2/idleRight";
    private static final String pathToRunLeft = "monster/BossMonster2/runLeft";
    private static final String pathToRunRight = "monster/BossMonster2/runRight";

    public BossMonsterP2(int scaling) {
        super(healthpoints, dmg, scaling, xSpeed, ySpeed, pathToIdleLeft, pathToIdleRight, pathToRunLeft, pathToRunRight, XPonDeath);
    }
}

package ecs.entities.monster;

public class BossMonsterP1 extends BossMonster{

    private static final int healthpoints = 10;
    private static final int dmg = 2;
    private static final float xSpeed = 0.025f;
    private static final float ySpeed = 0.025f;
    private static final long XPonDeath = 10;

    private static final String pathToIdleLeft = "monster/BossMonster/idleLeft";
    private static final String pathToIdleRight = "monster/BossMonster/idleRight";
    private static final String pathToRunLeft = "monster/BossMonster/runLeft";
    private static final String pathToRunRight = "monster/BossMonster/runRight";


    public BossMonsterP1(int scaling) {
        super(healthpoints, dmg, scaling, xSpeed, ySpeed, pathToIdleLeft, pathToIdleRight, pathToRunLeft, pathToRunRight, XPonDeath);
    }
}

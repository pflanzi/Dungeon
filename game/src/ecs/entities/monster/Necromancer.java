package ecs.entities.monster;

public class Necromancer extends RangedMonster {

    private static final int healthpoints = 7;
    private static final float xSpeed = 0.075f;
    private static final float ySpeed = 0.075f;
    private static final int dmg = 5;
    private static final long XPonDeath = 10;

    private static final String pathToIdleLeft = "monster/necromancer/idleLeft";
    private static final String pathToIdleRight = "monster/necromancer/idleRight";
    private static final String pathToRunLeft = "monster/necromancer/runLeft";
    private static final String pathToRunRight = "monster/necromancer/runRight";

    public Necromancer(int scaling) {
        super(
                healthpoints,
                dmg,
                scaling,
                xSpeed,
                ySpeed,
                pathToIdleLeft,
                pathToIdleRight,
                pathToRunLeft,
                pathToRunRight,
                XPonDeath);
    }
}

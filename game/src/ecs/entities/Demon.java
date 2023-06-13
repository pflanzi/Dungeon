package ecs.entities;

public class Demon extends MeleeMonster {

    private static final int healthpoints = 10;
    private static final int dmg = 4;
    private static final float xSpeed = 0.1f;
    private static final float ySpeed = 0.1f;
    private static final long XPonDeath = 10;

    private static final String pathToIdleLeft = "monster/demon/idleLeft";
    private static final String pathToIdleRight = "monster/demon/idleRight";
    private static final String pathToRunLeft = "monster/demon/runLeft";
    private static final String pathToRunRight = "monster/demon/runRight";

    public Demon(int scaling) {
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

package ecs.entities;

public class Demon extends MeleeMonster {

    private final static int healthpoints = 10;
    private final static int dmg = 4;
    private final static float xSpeed = 0.1f;
    private final static float ySpeed = 0.1f;

    private final static String pathToIdleLeft = "monster/demon/idleLeft";
    private final static String pathToIdleRight = "monster/demon/idleRight";
    private final static String pathToRunLeft = "monster/demon/runLeft";
    private final static String pathToRunRight = "monster/demon/runRight";

    public Demon() {
        super(healthpoints, dmg, xSpeed, ySpeed, pathToIdleLeft, pathToIdleRight, pathToRunLeft, pathToRunRight);

    }

}

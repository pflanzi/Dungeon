package ecs.entities;

public class Demon extends MeleeMonster {

    private final int healthpoints = 10;
    private final int dmg = 4;
    private final float xSpeed = 0.35f;
    private final float ySpeed = 0.35f;

    private final String pathToIdleLeft = "monster/demon/idleLeft";
    private final String pathToIdleRight = "monster/demon/idleRight";
    private final String pathToRunLeft = "monster/demon/runLeft";
    private final String pathToRunRight = "monster/demon/runRight";

    public Demon() {
        super(healthpoints,);
    }

}

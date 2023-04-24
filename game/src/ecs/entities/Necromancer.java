package ecs.entities;

import ecs.components.PositionComponent;

public class Necromancer extends RangedMonster {

    private final int healthpoints = 7;
    private final float xSpeed = 0.2f;
    private final float ySpeed = 0.2f;
    private final int dmg = 5;
    private final String pathToIdleLeft = "monster/demon/idleLeft";
    private final String pathToIdleRight = "monster/demon/idleRight";
    private final String pathToRunLeft = "monster/demon/runLeft";
    private final String pathToRunRight = "monster/demon/runRight";





    public Necromancer(float posX, float posY) {
        super(healthpoints,
            dmg,
            xSpeed,
            ySpeed,
            pathToIdleLeft,
            pathToIdleRight,
            pathToRunLeft,
            pathToRunRight,
            posX,
            posY);


    }


}

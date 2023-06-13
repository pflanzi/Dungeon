package ecs.entities;

import ecs.components.ai.idle.heroChaseWalk;

public class Ogre extends MeleeMonster {

    private static final int healthpoints = 12;
    private static final int dmg = 6;
    private static final float xSpeed = 0.05f;
    private static final float ySpeed = 0.05f;
    private static final long XPonDeath = 10;

    private static final String pathToIdleLeft = "monster/ogre/idleLeft";
    private static final String pathToIdleRight = "monster/ogre/idleRight";
    private static final String pathToRunLeft = "monster/ogre/runLeft";
    private static final String pathToRunRight = "monster/ogre/runRight";

    public Ogre(int scaling) {
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
                new heroChaseWalk(),
                XPonDeath);
    }
}

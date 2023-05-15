package ecs.entities;

import ecs.components.PositionComponent;

public class Necromancer extends RangedMonster {

    private final static int healthpoints = 7;
    private final static float xSpeed = 0.075f;
    private final static float ySpeed = 0.075f;
    private final static int dmg = 5;
    private final static long XPonDeath = 10;

    private final static String pathToIdleLeft = "monster/necromancer/idleLeft";
    private final static String pathToIdleRight = "monster/necromancer/idleRight";
    private final static String pathToRunLeft = "monster/necromancer/runLeft";
    private final static String pathToRunRight = "monster/necromancer/runRight";





    public Necromancer(int scaling) {
        super(healthpoints, dmg, scaling ,xSpeed, ySpeed, pathToIdleLeft, pathToIdleRight, pathToRunLeft, pathToRunRight, XPonDeath);


    }


}

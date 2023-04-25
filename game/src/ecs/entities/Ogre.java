package ecs.entities;

public class Ogre extends MeleeMonster {

    private final static int healthpoints = 12;
    private final static int dmg = 6;
    private final static float xSpeed = 0.05f;
    private final static float ySpeed = 0.05f;

    private final static String pathToIdleLeft = "monster/ogre/idleLeft";
    private final static String pathToIdleRight = "monster/ogre/idleRight";
    private final static String pathToRunLeft = "monster/ogre/runLeft";
    private final static String pathToRunRight = "monster/ogre/runRight";



    public Ogre() {
        super(healthpoints, dmg, xSpeed, ySpeed, pathToIdleLeft, pathToIdleRight, pathToRunLeft, pathToRunRight);
    }




}

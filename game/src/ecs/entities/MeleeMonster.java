package ecs.entities;

abstract public class MeleeMonster extends Monster {

    public MeleeMonster(
        int healthpoints,
        int dmg,
        float xSpeed,
        float ySpeed,
        String pathToIdleLeft,
        String pathToIdleRight,
        String pathToRunLeft,
        String pathToRunRight)
    {
        super(
            healthpoints,
            dmg,
            xSpeed,
            ySpeed,
            pathToIdleLeft,
            pathToIdleRight,
            pathToRunLeft,
            pathToRunRight);
    }
}

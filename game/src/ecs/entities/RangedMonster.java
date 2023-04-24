package ecs.entities;

abstract public class RangedMonster extends Monster {

    public RangedMonster(
        int healthpoints,
        int dmg,
        float xSpeed,
        float ySpeed,
        String pathToIdleLeft,
        String pathToIdleRight,
        String pathToRunLeft,
        String pathToRunRight,
        float posX,
        float posY
    ) {
        super(
            healthpoints,
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

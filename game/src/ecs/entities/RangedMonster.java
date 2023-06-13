package ecs.entities;

public abstract class RangedMonster extends Monster {

    public RangedMonster(
            int healthpoints,
            int dmg,
            int scaling,
            float xSpeed,
            float ySpeed,
            String pathToIdleLeft,
            String pathToIdleRight,
            String pathToRunLeft,
            String pathToRunRight,
            long XPonDeath) {
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

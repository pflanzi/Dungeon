package ecs.entities.monster;

/** class that creates Entity BossMonster */

public abstract class BossMonster extends Monster {

public BossMonster(

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

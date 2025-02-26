package ecs.entities.monster;

import ecs.components.ai.idle.IIdleAI;

public abstract class MeleeMonster extends Monster {

    public MeleeMonster(
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

    public MeleeMonster(
            int healthpoints,
            int dmg,
            int scaling,
            float xSpeed,
            float ySpeed,
            String pathToIdleLeft,
            String pathToIdleRight,
            String pathToRunLeft,
            String pathToRunRight,
            IIdleAI IdleAI,
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
                IdleAI,
                XPonDeath);
    }
}

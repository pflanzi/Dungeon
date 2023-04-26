package ecs.entities;

import ecs.components.ai.idle.IIdleAI;

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

    public MeleeMonster(
        int healthpoints,
        int dmg,
        float xSpeed,
        float ySpeed,
        String pathToIdleLeft,
        String pathToIdleRight,
        String pathToRunLeft,
        String pathToRunRight,
        IIdleAI IdleAI) {
        super(healthpoints,
            dmg,
            xSpeed,
            ySpeed,
            pathToIdleLeft,
            pathToIdleRight,
            pathToRunLeft,
            pathToRunRight,
            IdleAI);
    }
}

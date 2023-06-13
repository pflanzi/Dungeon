package ecs.components.skill;

import ecs.components.HealthComponent;
import ecs.entities.Entity;

/** Makes the user immortal for a certain amount of seconds */
public class GodmodeSkill extends TimeBasedSkill {

    public int durationInSeconds = 10;

    protected Entity user;

    @Override
    public void execute(Entity entity) {
        user = entity;
        entity.getComponent(HealthComponent.class)
                .ifPresent(
                        hc -> {
                            ((HealthComponent) hc).setImmortal(true);
                        });
        System.out.println("Godmode activated\n");
    }

    public void deactivate() {
        user.getComponent(HealthComponent.class)
                .ifPresent(
                        hc -> {
                            ((HealthComponent) hc).setImmortal(false);
                            System.out.println("Godmode deactivated\n");
                        });
    }
}

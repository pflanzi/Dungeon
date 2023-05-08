package ecs.components.skill;

import ecs.components.HealthComponent;
import ecs.entities.Entity;
import ecs.entities.Trap;
import starter.Game;

public class GodmodeSkill extends TimeBasedSkill{

    @Override
    public void execute(Entity entity) {
        user = entity;
        entity.getComponent(HealthComponent.class)
            .ifPresent(
                hc -> {
                    ((HealthComponent) hc).setImmortal(true);
                });
    }

    public void deactivate(){
        user.getComponent(HealthComponent.class)
            .ifPresent(
                hc -> {
                    ((HealthComponent) hc).setImmortal(false);
                });
    }
}

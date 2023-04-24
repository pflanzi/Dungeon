package ecs.components.collision;

import ecs.components.Component;
import ecs.components.HealthComponent;
import ecs.entities.Entity;
import ecs.entities.Trap;
import level.LevelAPI;
import level.elements.tile.Tile;
import starter.Game;


public class TrapDamageCollision implements ICollide{

    @Override
    /**
     * Trap a deals damage to the target b and removes the trap if it's not repeatable
     */
    public void onCollision(Entity a, Entity b, Tile.Direction from) {

        b.getComponent(HealthComponent.class)
            .ifPresent(
                hc -> {
                    ((HealthComponent) hc).receiveHit((((Trap) a).getDamage()));
                    if(((Trap) a).isRepeatable() == false){
                        Game.removeEntity(a);
                    }else{
                        ((Trap) a).setTrapToUsed();
                    }
                });
    }
}

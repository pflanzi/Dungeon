package ecs.components.ai.transition;

import ecs.entities.Entity;

public class FriendlyTransition implements ITransition {
    /**
     * Function that determines whether an entity should be in combat mode.
     *
     * @param entity associated entity
     * @return if the entity should fight => always returns false for friendly creatures
     */
    @Override
    public boolean isInFightMode(Entity entity) {
        return false;
    }
}

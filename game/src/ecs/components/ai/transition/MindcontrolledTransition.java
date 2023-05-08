package ecs.components.ai.transition;

import ecs.components.ai.AITools;
import ecs.entities.Entity;

public class MindcontrolledTransition implements ITransition {

    private final float range;

    /**
     * Switches to combat mode when the player is within range of the entity.
     *
     * @param range Range of the entity.
     */
    public MindcontrolledTransition(float range) {
        this.range = range;
    }

    @Override
    public boolean isInFightMode(Entity entity) {
        return AITools.entityInRange(entity, AITools.closestMonsterToEntity(entity), range);
    }
}

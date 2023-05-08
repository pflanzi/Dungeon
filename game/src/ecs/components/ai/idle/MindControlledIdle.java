package ecs.components.ai.idle;

import ecs.components.ai.AITools;
import ecs.components.ai.fight.MindcontrolledFight;
import ecs.entities.Entity;
import ecs.entities.Monster;

/**
 * follows a given target entity
 */
public class MindControlledIdle implements IIdleAI{
    private Entity target;
    public MindControlledIdle(Entity target){
        this.target = target;
    }
    @Override
    public void idle(Entity entity) {
        AITools.move(entity, AITools.calculatePath(entity, target));
    }
}

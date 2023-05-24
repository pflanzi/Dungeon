package ecs.components.ai.idle;

import ecs.components.ai.AITools;
import ecs.entities.Entity;

public class FollowHeroWalk implements IIdleAI {

    @Override
    public void idle(Entity entity) {
        AITools.move(entity, AITools.calculateFriendlyPathToHero(entity));
    }
}

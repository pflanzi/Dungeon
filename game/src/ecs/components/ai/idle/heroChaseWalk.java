package ecs.components.ai.idle;

import ecs.components.ai.AITools;
import ecs.entities.Entity;
import java.util.Random;

public class heroChaseWalk implements IIdleAI {

    @Override
    public void idle(Entity entity) {
        Random rnd = new Random();
        int n = rnd.nextInt(3) + 1;
        if (n == 1) {
            AITools.move(entity, AITools.calculatePathToHero(entity));
        }
    }
}

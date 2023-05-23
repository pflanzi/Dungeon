package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import level.elements.tile.Tile;

import java.util.Random;

public class FollowHeroWalk implements IIdleAI {

    private float distanceToHero;
    private GraphPath<Tile> path;

    public FollowHeroWalk() {
        // TODO: fill this
    }

    @Override
    public void idle(Entity entity) {
        // TODO: change this so the ghost follows the hero at a distance

        Random rnd = new Random();
        int n = rnd.nextInt(3) + 1;
        if (n == 1) {
            AITools.move(entity, AITools.calculatePathToHero(entity));
        }
    }
}

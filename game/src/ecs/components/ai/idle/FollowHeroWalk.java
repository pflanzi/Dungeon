package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.InteractionComponent;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import level.elements.tile.Tile;

import java.util.Random;

public class FollowHeroWalk implements IIdleAI {

    private static final Random random = new Random();
    private boolean moveToRandom;
    private GraphPath<Tile> currentPath;


    /**
     * Movement behaviour for a friendly NPC.
     * Sometimes moves towards the hero, sometimes moves to a random tile in the level
     *
     * @param entity associated entity
     */
    @Override
    public void idle(Entity entity) {

        moveToRandom = random.nextInt(10) % 4 == 0;

        if (currentPath == null) {

            if (moveToRandom) {
                currentPath = AITools.calculatePathToHero(entity);
            } else currentPath = AITools.calculatePathToRandomTileInRange(entity, 5.0f);

        } else {
            if (AITools.pathFinished(entity, currentPath) && !moveToRandom) {
                currentPath = AITools.calculatePathToHero(entity);

            } else if (AITools.pathFinished(entity, currentPath) && moveToRandom) {
                currentPath = AITools.calculatePathToRandomTileInRange(entity, 5.0f);

            }
        }

        if (!(AITools.pathFinished(entity, currentPath))) {
            if (AITools.playerInRange(entity, (float) InteractionComponent.DEFAULT_RADIUS / 2))
                AITools.move(entity, AITools.calculatePath(entity, entity));
            else AITools.move(entity, currentPath);
        }
    }
}

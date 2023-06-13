package ecs.components.ai.fight;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.HealthComponent;
import ecs.components.ai.AITools;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import tools.Constants;

public class MindcontrolledFight extends CollideAI {

    private final int delay = Constants.FRAME_RATE;
    private int timeSinceLastUpdate = delay;
    private GraphPath<Tile> path;
    private Entity targetEntity;

    /**
     * Attacks the target if it is within the given range. Otherwise, it will move towards the
     * target.
     *
     * @param attackRange Range in which the attack skill should be executed
     */
    public MindcontrolledFight(float attackRange, Entity targetEntity) {
        super(attackRange);
        this.targetEntity = targetEntity;
    }

    @Override
    public void fight(Entity entity) {
        if (AITools.entityInRange(entity, targetEntity, rushRange)) {
            // the faster pathing once a certain range is reached
            path = AITools.calculatePath(entity, targetEntity);
            AITools.move(entity, path);
            timeSinceLastUpdate = delay;
            entity.getComponent(HealthComponent.class)
                    .ifPresent(
                            hc -> {
                                if (((Math.random() * 10) + 1) % 3 == 1)
                                    ((HealthComponent) hc)
                                            .receiveHit(new Damage(1, DamageType.PHYSICAL, entity));
                            });
        } else {
            // check if new pathing update
            if (timeSinceLastUpdate >= delay) {
                path = AITools.calculatePath(entity, targetEntity);
                timeSinceLastUpdate = -1;
            }
            timeSinceLastUpdate++;
            AITools.move(entity, path);
        }
    }
}

package ecs.components.interactions;

import ecs.components.IInteraction;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import ecs.entities.Tombstone;
import starter.Game;

import java.util.Random;

public class TombstoneInteraction implements IInteraction {

    /**
     * Randomly decides what happens if the hero interacts with a tombstone.
     *
     * @param entity Entity that calls the onInteraction method, e.g. the Tombstone entity.
     */
    @Override
    public void onInteraction(Entity entity) {
        int choice = new Random().nextInt(2);

        if (entity instanceof Tombstone) {

            if (AITools.entityInRange(entity, ((Tombstone) entity).getGhost(), ((Tombstone) entity).getInteractionRadius())) {
                System.out.println("Ghost is in range of the tombstone.");

                switch (choice) {
                    case 0 -> ((Tombstone) entity).reward();
                    case 1 -> ((Tombstone) entity).punish();
                }

                System.out.println("Removing ghost and tombstone ...");

                Game.removeEntity(entity);
                Game.removeEntity(((Tombstone) entity).getGhost());

            } else System.out.println("Ghost not yet in range of the tombstone.");

        } else System.out.println("Interacting entity is not of type Tombstone.");

    }
}

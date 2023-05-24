package ecs.components.interactions;

import ecs.components.IInteraction;
import ecs.entities.Entity;
import ecs.entities.Tombstone;
import starter.Game;

import java.util.Random;

public class TombstoneInteraction implements IInteraction {

    /**
     * Randomly decides what happens if the hero interacts with a tombstone.
     *
     * @param entity Entity that interacts with the tombstone which typically is the hero.
     */
    @Override
    public void onInteraction(Entity entity) {
        int choice = new Random().nextInt(2);

        if (entity instanceof Tombstone) {

            switch (choice) {
                case 0 -> ((Tombstone) entity).reward();
                case 1 -> ((Tombstone) entity).punish();
            }

            System.out.println("Removing ghost and tombstone ...");

            Game.removeEntity(entity);
            Game.removeEntity(((Tombstone) entity).getGhost());
        }

        // TODO: delete entities (ghost + tombstone) afterwards
    }
}

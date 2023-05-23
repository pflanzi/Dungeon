package ecs.entities;

import ecs.components.*;

import java.util.Random;

public class Tombstone extends Entity implements IInteraction {

    private Ghost ghost;
    private String texture = "";
    private int dmgAmount = 3;
    private int healAmount = 5;
    private float radius = 1.0f;

    public Tombstone(Ghost ghost) {
        this.ghost = ghost;

        new PositionComponent(this);
        // TODO: fill this with functionality => AI and stuff
        new HitboxComponent(this, null, null);
        new InteractionComponent(this, radius, false, this);
    }

    /**
     * Randomly decides what happens if the hero interacts with a tombstone.
     *
     * @param hero Entity that interacts with the tombstone which typically is the hero.
     */
    @Override
    public void onInteraction(Entity hero) {
        int choice = new Random().nextInt(2);

        switch (choice) {
            case 0 -> reward(hero);
            case 1 -> punish(hero);
        }
        // TODO: delete entity afterwards
    }

    /**
     * Adds 5 HP to the entity's current max HP.
     * @param entity Entity that interacts with the tombstone which typically is the hero.
     */
    private void reward(Entity entity) {
        entity.getComponent(HealthComponent.class)
            .ifPresent(hc -> {
                ((HealthComponent) hc)
                    .setMaximalHealthpoints(
                        ((HealthComponent) hc).getMaximalHealthpoints() + healAmount
                    );
            });
    }

    /**
     * Removes 3 HP from the entity's current HP.
     * @param entity Entity that interacts with the tombstone which typically is the hero.
     */
    private void punish(Entity entity) {
        entity.getComponent(HealthComponent.class)
            .ifPresent(hc -> {
                ((HealthComponent) hc)
                    .setCurrentHealthpoints(
                        ((HealthComponent) hc).getCurrentHealthpoints() - dmgAmount
                    );
            });
    }

}

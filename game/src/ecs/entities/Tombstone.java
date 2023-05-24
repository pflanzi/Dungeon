package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.interactions.TombstoneInteraction;
import starter.Game;

public class Tombstone extends Entity {

    private Ghost ghost;
    IInteraction tombstoneInteraction;
    private String texture = "character/ghost/Tombstone_17px.png";
    private final int dmgAmount = 3;
    private final int healAmount = 5;
    private final int interactionRadius = InteractionComponent.DEFAULT_RADIUS;

    public Tombstone(Ghost ghost) {
        this.ghost = ghost;
        tombstoneInteraction = new TombstoneInteraction();

        new PositionComponent(this);
        new HitboxComponent(this,
            (you, other, direction) -> System.out.printf("tombstoneCollisionEnter:\t%s\n", other.getClass().getSimpleName()),
            (you, other, direction) -> System.out.printf("tombstoneCollisionExit:\t%s\n", other.getClass().getSimpleName()));
        new InteractionComponent(this, interactionRadius, false, tombstoneInteraction);
        new AnimationComponent(this, AnimationBuilder.buildAnimation(texture));
    }

    /**
     * Adds 5 HP to the entity's current max HP.
     */
    public void reward() {
        Game.getHero()
            .flatMap(h -> h.getComponent(HealthComponent.class))
            .ifPresent(hc -> {
                System.out.printf("The hero has been rewarded with %d additional HP!\n", healAmount);
                System.out.printf("Previous Max HP:\t%d\n", ((HealthComponent) hc).getMaximalHealthpoints());

                ((HealthComponent) hc)
                    .setMaximalHealthpoints(
                        ((HealthComponent) hc).getMaximalHealthpoints() + healAmount);

                System.out.printf("New Max HP:\t%d\n", ((HealthComponent) hc).getMaximalHealthpoints());;
            });
    }

    /**
     * Removes 3 HP from the entity's current HP.
     */
    public void punish() {
        Game.getHero()
            .flatMap(h -> h.getComponent(HealthComponent.class))
            .ifPresent(hc -> {
                System.out.printf("The hero has been punished with %d damage to their current health!\n", dmgAmount);
                System.out.printf("Previous Current HP:\t%d\n", ((HealthComponent) hc).getCurrentHealthpoints());

                ((HealthComponent) hc)
                    .setMaximalHealthpoints(
                        ((HealthComponent) hc).getCurrentHealthpoints() - dmgAmount);

                System.out.printf("New Current HP:\t%d\n", ((HealthComponent) hc).getCurrentHealthpoints());;
            });
    }

    public Ghost getGhost() {
        return ghost;
    }

    public int getInteractionRadius() { return interactionRadius; }
}

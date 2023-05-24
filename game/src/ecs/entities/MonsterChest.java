package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.idle.heroChaseWalk;

/**
 * class that creates Entity MonsterChest
 */
public class MonsterChest extends MeleeMonster {

    private final static int hp = 5;
    private final static int dmg = 1;
    private final static float xSpeed = 0.1f;
    private final static float ySpeed = 0.1f;

    private final static String pathToIdleLeft = "objects/treasurechest/chest_full_open_anime_f3.png";
    private final static String pathToIdleRight = "objects/treasurechest/chest_full_open_anime_f4.png";
    final static String pathToRunLeft = "objects/treasurechest/chest_full_open_anime_f3.png";
    private final static String pathToRunRight = "objects/treasurechest/chest_full_open_anime_f4.png";

    public MonsterChest(Chest chest) {

        super(hp, dmg, 1, xSpeed, ySpeed, pathToIdleLeft, pathToIdleRight, pathToRunLeft, pathToRunRight, new heroChaseWalk(), 10);

        setupPositionComponent(chest);
        setupInventoryComponent(chest);
        setupHealthComponent(chest);
    }

    private void setupPositionComponent(Chest chest) {

        PositionComponent epc =
            (PositionComponent)
                chest.getComponent(PositionComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(this, epc.getPosition());
    }

    private void setupInventoryComponent(Chest chest) {

        InventoryComponent inventoryComponent =
            chest.getComponent(InventoryComponent.class)
                .map(InventoryComponent.class::cast)
                .orElseThrow(
                    () ->
                        new MissingComponentException("InventoryComponent"));
        new InventoryComponent(this, inventoryComponent.getMaxSize());


    }

    /**
     * Sets up healthcomponent also drops items ondeath
     * @param chest
     */
    private void setupHealthComponent(Chest chest) {

        HealthComponent healthComponent =
            this.getComponent(HealthComponent.class)
                .map(HealthComponent.class::cast)
                .orElseThrow(
                    () ->
                        new MissingComponentException("HealthComponent"));
        new HealthComponent(this, hp, new IOnDeathFunction() {
            @Override
            public void onDeath(Entity entity) {
                chest.setMonster(false);
                chest.dropItems(chest);
            }
        }, AnimationBuilder.buildAnimation(pathToRunLeft), AnimationBuilder.buildAnimation(pathToIdleLeft));
    }
}



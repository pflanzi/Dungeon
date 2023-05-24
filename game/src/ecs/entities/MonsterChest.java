package ecs.entities;

import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.components.ai.idle.heroChaseWalk;
import ecs.components.IOnDeathFunction;

/**
 * class that creates Entity MonsterChest
 */
public class MonsterChest extends MeleeMonster implements IOnDeathFunction {

    private final static int hp = 5;
    private final static int dmg = 1;
    private final static float xSpeed = 0.1f;
    private final static float ySpeed = 0.1f;

    private final static String pathToIdleLeft = "objects/treasurechest/chest_full_open_anime_f3.png";
    private final static String pathToIdleRight = "objects/treasurechest/chest_full_open_anime_f4.png";
    final static String pathToRunLeft = "objects/treasurechest/chest_full_open_anime_f3.png";
    private final static String pathToRunRight = "objects/treasurechest/chest_full_open_anime_f4.png";

    public MonsterChest (Chest chest) {

        super(hp, dmg, 1 ,xSpeed, ySpeed, pathToIdleLeft, pathToIdleRight, pathToRunLeft, pathToRunRight, new heroChaseWalk(), 10);

        setupPositionComponent(chest);
        setupInventoryComponent(chest);
        setupHealthComponent();

    }

    private void setupPositionComponent (Chest chest) {

        PositionComponent epc =
            (PositionComponent)
                chest.getComponent(PositionComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(this, epc.getPosition());


    }

    private void setupInventoryComponent (Chest chest) {

        InventoryComponent inventoryComponent =
            chest.getComponent(InventoryComponent.class)
                .map(InventoryComponent.class::cast)
                .orElseThrow(
                    () ->
                        new MissingComponentException("InventoryComponent"));
        new InventoryComponent(this, inventoryComponent.getMaxSize());


    }

    private void setupHealthComponent(Chest chest) {

            HealthComponent healthComponent =
            this.getComponent(HealthComponent.class)
                .map(HealthComponent.class::cast)
                .orElseThrow(
                    () ->
                        new MissingComponentException("HealthComponent"));

            healthComponent.setOnDeath(new IOnDeathFunction(chest) {
                @Override
                public void onDeath(Entity entity) {
                    chest.dropItems(this);
                }
            });


    }


    @Override
    public void onDeath(Entity entity) {
        entity.
    }
}



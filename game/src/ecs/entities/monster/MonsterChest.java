package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.idle.heroChaseWalk;
import ecs.entities.Chest;
import ecs.entities.Entity;

/** class that creates Entity MonsterChest */
public class MonsterChest extends MeleeMonster {

    private static final int hp = 5;
    private static final int dmg = 1;
    private static final float xSpeed = 0.1f;
    private static final float ySpeed = 0.1f;

    private static final String pathToIdleLeft =
            "objects/treasurechest/chest_full_open_anime_f3.png";
    private static final String pathToIdleRight =
            "objects/treasurechest/chest_full_open_anime_f4.png";
    static final String pathToRunLeft = "objects/treasurechest/chest_full_open_anime_f3.png";
    private static final String pathToRunRight =
            "objects/treasurechest/chest_full_open_anime_f4.png";

    public MonsterChest(Chest chest) {

        super(
                hp,
                dmg,
                1,
                xSpeed,
                ySpeed,
                pathToIdleLeft,
                pathToIdleRight,
                pathToRunLeft,
                pathToRunRight,
                new heroChaseWalk(),
                10);

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
                        .orElseThrow(() -> new MissingComponentException("InventoryComponent"));
        new InventoryComponent(this, inventoryComponent.getMaxSize());
    }

    private void setupHealthComponent(Chest chest) {

        HealthComponent healthComponent =
                this.getComponent(HealthComponent.class)
                        .map(HealthComponent.class::cast)
                        .orElseThrow(() -> new MissingComponentException("HealthComponent"));
        new HealthComponent(
                this,
                hp,
                new IOnDeathFunction() {
                    @Override
                    public void onDeath(Entity entity) {
                        chest.setMonster(false);
                        chest.dropItems(chest);
                    }
                },
                AnimationBuilder.buildAnimation(pathToRunLeft),
                AnimationBuilder.buildAnimation(pathToIdleLeft));
    }
}

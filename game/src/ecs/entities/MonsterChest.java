package ecs.entities;

import ecs.components.InventoryComponent;
import ecs.components.ai.idle.heroChaseWalk;

public class MonsterChest extends MeleeMonster {

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
        chest.getComponent(InventoryComponent.class);



    }

    public static void setupInventoryComponent (InventoryComponent inventoryComponent) {

    }

}

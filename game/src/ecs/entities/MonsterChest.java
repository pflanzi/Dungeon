package ecs.entities;

import ecs.components.InventoryComponent;

public class MonsterChest extends MeleeMonster {

    int hp;
    int dmg;
    float xSpeed = 0.1f;
    float ySpeed = 0.1f;




    public MonsterChest (Chest chest) {




    }

    public static void setupInventoryComponent (InventoryComponent inventoryComponent) {

    }

}

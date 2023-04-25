package ecs.entities;

import ecs.components.InventoryComponent;
import ecs.components.ItemComponent;
import ecs.items.ItemCategory;

import java.util.Random;

public class Bag extends Entity {

    private static final int MIN_BAG_SIZE = 3;
    private static final int MAX_BAG_SIZE = 10;
    private ItemComponent itemComponent;
    private InventoryComponent inventoryComponent;
    private ItemCategory itemCategory;
    private int inventorySize;

    public Bag() {
        this.inventorySize = generateBagSize();
        new ItemComponent(this);
        new InventoryComponent(this, inventorySize);
    }

    public Bag(
        ItemComponent itemComponent,
        InventoryComponent inventoryComponent,
        ItemCategory itemCategory) {
        this.itemComponent = itemComponent;
        this.inventoryComponent = inventoryComponent;
        this.itemCategory = itemCategory;
        this.inventorySize = generateBagSize();
    }

    private int generateBagSize() {
        return new Random().nextInt(MIN_BAG_SIZE, MAX_BAG_SIZE + 1);
    }
}

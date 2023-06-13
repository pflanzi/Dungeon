package ecs.entities;

import ecs.components.InventoryComponent;
import ecs.components.ItemComponent;
import ecs.items.ItemCategory;
import ecs.items.ItemData;
import ecs.items.ItemType;
import graphic.Animation;
import java.util.Collections;
import java.util.Random;

public class Bag extends Entity {

    private static final int MIN_BAG_SIZE = 3;
    private static final int MAX_BAG_SIZE = 10;
    private static final String itemName = "Tasche";
    private static final String itemDescription =
            "Eine Tasche, die Platz für mehr Items bietet. Verbraucht einen Itemslot im Inventar.";
    private ItemComponent itemComponent;
    private InventoryComponent inventoryComponent;
    private ItemCategory itemCategory;
    private int inventorySize;

    public Bag() {
        this.inventorySize = generateBagSize();
        new ItemComponent(
                this,
                new ItemData(
                        ItemType.Active,
                        ItemCategory.OTHER,
                        new Animation(Collections.singleton("items/other/bag_small.png"), 1),
                        new Animation(Collections.singleton("items/other/bag_small.png"), 1),
                        itemName,
                        itemDescription));
        new InventoryComponent(this, inventorySize);
        this.itemCategory =
                ItemCategory.values()[new Random().nextInt(ItemCategory.values().length)];
    }

    private int generateBagSize() {
        return new Random().nextInt(MIN_BAG_SIZE, MAX_BAG_SIZE + 1);
    }
}

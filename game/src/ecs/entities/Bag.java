package ecs.entities;

import ecs.components.InventoryComponent;
import ecs.components.ItemComponent;

public class Bag extends Entity {

    private ItemComponent itemComponent;
    private InventoryComponent inventoryComponent;
    private int inventorySize;

    public Bag() {}


}

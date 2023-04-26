package ecs.components;

import ecs.entities.Bag;
import ecs.entities.Entity;
import ecs.items.ItemCategory;
import ecs.items.ItemData;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ecs.items.ItemType;
import logging.CustomLogLevel;

/**
 * Allows an Entity to carry Items
 */
public class InventoryComponent extends Component {

    private List<ItemData> inventory;
    /**
     * List of inventorys, to simulate bags
     */
    private List<List<ItemData>> bagInventory;
    private List<ItemCategory> bagType;
    private int maxSize;
    private final Logger inventoryLogger = Logger.getLogger(this.getClass().getName());

    /**
     * creates a new InventoryComponent
     *
     * @param entity  the Entity where this Component should be added to
     * @param maxSize the maximal size of the inventory
     */
    public InventoryComponent(Entity entity, int maxSize) {
        super(entity);
        inventory = new ArrayList<>(maxSize);
        this.maxSize = maxSize;
        bagInventory = new ArrayList<>();
        bagType = new ArrayList<>();
    }

    /**
     * Adding an Element to the Inventory does not allow adding more items than the size of the
     * Inventory.
     * <p>
     * If the Item is a bag, it creates a new list in bagInventory and references the index in ItemData
     *
     * @param itemData the item which should be added
     * @return true if the item was added, otherwise false
     */
    public boolean addItem(ItemData itemData) {
        if (inventory.size() >= maxSize && bagInventory.size() != 0) {
            for (int i = 0; i < bagInventory.size() - 1; i++) {
                if (filledBagSlots(i) < bagInventory.get(i).size() && bagType.get(i).getItemCategory().equals(itemData.getItemCategory())) {
                    return bagInventory.get(i).add(itemData);
                }
            }
            return false;
        }
        inventoryLogger.log(
            CustomLogLevel.DEBUG,
            "Item '"
                + this.getClass().getSimpleName()
                + "' was added to the inventory of entity '"
                + entity.getClass().getSimpleName()
                + "'.");
        if (itemData.getInventorySlot() == -1) {
            if (itemData.getInventorySize() > 0) {
                bagInventory.add(new ArrayList<>(itemData.getInventorySize()));
                itemData.setInventorySlot(bagInventory.size() - 1);
                bagType.add(itemData.getItemCategory());
            }
        }
        return inventory.add(itemData);
    }

    /**
     * removes the given Item from the inventory
     *
     * @param itemData the item which should be removed
     * @return true if the element was removed, otherwise false
     */
    public boolean removeItem(ItemData itemData) {
        inventoryLogger.log(
            CustomLogLevel.DEBUG,
            "Removing item '"
                + this.getClass().getSimpleName()
                + "' from inventory of entity '"
                + entity.getClass().getSimpleName()
                + "'.");
        return inventory.remove(itemData);
    }

    public boolean removeItemFromBag(ItemData removeItem, ItemData bag) {
        if (bagInventory.get(bag.getInventorySlot()).remove(removeItem)) {
            return true;
        }
        return false;
    }

    /**
     * @return the number of slots already filled with items
     */
    public int filledSlots() {
        return inventory.size();
    }

    public int filledBagSlots(int bag) {
        return bagInventory.get(bag).size();
    }

    /**
     * @return the number of slots still empty
     */
    public int emptySlots() {
        return maxSize - inventory.size();
    }

    /**
     * @return the size of the inventory
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * @return a copy of the inventory
     */
    public List<ItemData> getItems() {
        return new ArrayList<>(inventory);
    }

    /**
     * @return a copy of the list of bags
     */
    public List<List<ItemData>> getBagInventory() {
        return new ArrayList<>(bagInventory);
    }

    /**
     * @return a copy of the inventory of a bag
     */
    public List<ItemData> getBagItems(int bagSlot) {
        return new ArrayList<>(bagInventory.get(bagSlot));
    }

    /**
     * prints name and description of one item from the inventory,
     * if the given slot is not valid, the whole inventory is printed
     *
     * @param invSlot slot of the item to print
     */
    public void printInventory(int invSlot) {
        if (invSlot < 0 || invSlot > inventory.size()) {
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println(i + ":\n" + inventory.get(i).getItemName() + "\n" + inventory.get(i).getDescription() + "\n");
            }
        } else {
            System.out.println(invSlot + ":\n" + inventory.get(invSlot).getItemName() + "\n" + inventory.get(invSlot).getDescription() + "\n");
        }
    }

    public void printBagInventory(int bagSlot, int invSlot) {
        if (invSlot < 0 || invSlot > bagInventory.get(bagSlot).size()) {
            for (int i = 0; i < bagInventory.get(bagSlot).size(); i++) {
                System.out.println(i + ":\n" + bagInventory.get(bagSlot).get(i).getItemName() + "\n" + bagInventory.get(bagSlot).get(i).getDescription() + "\n");
            }
        } else {
            System.out.println(invSlot + ":\n" + bagInventory.get(bagSlot).get(invSlot).getItemName() + "\n" + bagInventory.get(bagSlot).get(invSlot).getDescription() + "\n");
        }
    }
}

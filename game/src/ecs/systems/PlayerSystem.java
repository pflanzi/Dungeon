package ecs.systems;

import com.badlogic.gdx.Gdx;
import configuration.KeyboardConfig;
import ecs.components.*;
import ecs.entities.Bag;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.ItemCategory;
import ecs.items.ItemData;
import ecs.items.ItemType;
import ecs.items.WorldItemBuilder;
import ecs.tools.interaction.InteractionTool;
import starter.Game;

import java.util.List;
import java.util.stream.IntStream;

import static ecs.entities.Chest.calculateDropPosition;

/**
 * Used to control the player
 */
public class PlayerSystem extends ECS_System {

    private record KSData(Entity e, PlayableComponent pc, VelocityComponent vc) {
    }

    private boolean inventoryOpen = false;
    private boolean bagOpen = false;
    private int inventoryItemSlotCounter = 0;
    private int bagItemSlotCounter = 0;

    @Override
    public void update() {
        Game.getEntities().stream()
            .flatMap(e -> e.getComponent(PlayableComponent.class).stream())
            .map(pc -> buildDataObject((PlayableComponent) pc))
            .forEach(this::checkKeystroke);
    }

    private void checkKeystroke(KSData ksd) {
        if (!inventoryOpen) {
            if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_UP.get()))
                ksd.vc.setCurrentYVelocity(1 * ksd.vc.getYVelocity());
            else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_DOWN.get()))
                ksd.vc.setCurrentYVelocity(-1 * ksd.vc.getYVelocity());
            else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_RIGHT.get()))
                ksd.vc.setCurrentXVelocity(1 * ksd.vc.getXVelocity());
            else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_LEFT.get()))
                ksd.vc.setCurrentXVelocity(-1 * ksd.vc.getXVelocity());

            if (Gdx.input.isKeyPressed(KeyboardConfig.INTERACT_WORLD.get()))
                InteractionTool.interactWithClosestInteractable(ksd.e);
            else if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
                inventoryOpen = true;
                System.out.println("Inventory opened!\n");
                accessInventoryPrintInventory(ksd.e);
            }
            // check skills
            else if (Gdx.input.isKeyPressed(KeyboardConfig.FIRST_SKILL.get()))
                ksd.pc.getSkillSlot1().ifPresent(skill -> skill.execute(ksd.e));
            else if (Gdx.input.isKeyPressed(KeyboardConfig.SECOND_SKILL.get()))
                ksd.pc.getSkillSlot2().ifPresent(skill -> skill.execute(ksd.e));
        } else if (!bagOpen) {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
                inventoryOpen = false;
                System.out.println("Inventory closed!\n");
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.MOVEMENT_RIGHT.get())) {
                inventoryItemSlotCounter++;
                accessInventoryPrintItem(ksd.e, inventoryItemSlotCounter);
            } else if (Gdx.input.isKeyJustPressed(KeyboardConfig.MOVEMENT_LEFT.get())) {
                inventoryItemSlotCounter--;
                accessInventoryPrintItem(ksd.e, inventoryItemSlotCounter);
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INTERACT_WORLD.get())) {
                accessInventoryUseActiveItem(ksd.e, inventoryItemSlotCounter);
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_THROW.get())) {
                accessInventoryDropItem(ksd.e, inventoryItemSlotCounter);
                inventoryItemSlotCounter=0;
            }

        } else {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
                bagOpen = false;
                System.out.println("Bag closed!\nBack to regular Inventory");
                accessInventoryPrintInventory(ksd.e);
                inventoryItemSlotCounter = 0;
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.MOVEMENT_RIGHT.get())) {
                bagItemSlotCounter++;
                accessBagPrintItem(ksd.e, inventoryItemSlotCounter, bagItemSlotCounter);
            } else if (Gdx.input.isKeyJustPressed(KeyboardConfig.MOVEMENT_LEFT.get())) {
                bagItemSlotCounter--;
                accessBagPrintItem(ksd.e, inventoryItemSlotCounter, bagItemSlotCounter);
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INTERACT_WORLD.get())) {
                accessBagUseActiveItem(ksd.e, inventoryItemSlotCounter, bagItemSlotCounter);
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_THROW.get())) {
                accessBagDropItem(ksd.e, inventoryItemSlotCounter, bagItemSlotCounter);
                bagItemSlotCounter=0;
                accessBagPrintItem(ksd.e, inventoryItemSlotCounter, bagItemSlotCounter);
            }
        }
    }

    private KSData buildDataObject(PlayableComponent pc) {
        Entity e = pc.getEntity();

        VelocityComponent vc =
            (VelocityComponent)
                e.getComponent(VelocityComponent.class)
                    .orElseThrow(PlayerSystem::missingVC);

        return new KSData(e, pc, vc);
    }

    //Todo write exception for drop
    private static MissingComponentException missingVC() {
        return new MissingComponentException("VelocityComponent");
    }

    private void accessInventoryPrintInventory(Entity e) {
        e.getComponent(InventoryComponent.class)
            .ifPresent(ic -> {
                ((InventoryComponent) ic).printInventory(-1);
                inventoryItemSlotCounter = 0;
            });
    }

    private void accessInventoryPrintItem(Entity e, int slot) {
        if (!bagOpen) {
            e.getComponent(InventoryComponent.class)
                .ifPresent(ic -> {
                    if (slot >= 0) {
                        if (slot < ((InventoryComponent) ic).filledSlots()) {
                            ((InventoryComponent) ic).printInventory(slot);
                        } else {
                            inventoryItemSlotCounter = 0;
                            ((InventoryComponent) ic).printInventory(inventoryItemSlotCounter);
                        }
                    } else {
                        inventoryItemSlotCounter = ((InventoryComponent) ic).filledSlots() - 1;
                        ((InventoryComponent) ic).printInventory(inventoryItemSlotCounter);
                    }
                });
        }
    }

    private void accessInventoryUseActiveItem(Entity e, int slot) {
        e.getComponent(InventoryComponent.class)
            .ifPresent(ic -> {
                if (((InventoryComponent) ic).getItems().get(slot).getItemType() == ItemType.Active) {
                    System.out.println("Hey");
                    ((InventoryComponent) ic).getItems().get(slot).triggerUse(e);
                } else if (((InventoryComponent) ic).getItems().get(slot).getItemCategory() == ItemCategory.BAG) {
                    System.out.println("he");
                    int bagSlot = ((InventoryComponent) ic).getItems().get(slot).getInventorySlot();
                    if (((InventoryComponent) ic).getBagItems(bagSlot).size() > 0) {
                        bagOpen = true;
                        accessBagPrintBag(e, slot);
                    } else {
                        System.out.println("This bitch empty\n");
                    }
                }
            });
    }

    /**
     *
     * @param entity
     * @param slot
     */
    private void accessInventoryDropItem(Entity entity, int slot) {
        //Todo check if item exists before dropping
        entity.getComponent(InventoryComponent.class)
            .ifPresent(ic -> {
                if (slot >= 0 && slot < ((InventoryComponent) ic).filledSlots()) {
                    ((InventoryComponent) ic).removeItem(((InventoryComponent) ic).getItems().get(slot));

                    InventoryComponent inventoryComponent =
                        entity.getComponent(InventoryComponent.class)
                            .map(InventoryComponent.class::cast)
                            .orElseThrow(
                                () ->
                                    missingVC());
                    PositionComponent positionComponent =
                        entity.getComponent(PositionComponent.class)
                            .map(PositionComponent.class::cast)
                            .orElseThrow(
                                () ->
                                    missingVC());
                    List<ItemData> itemData = inventoryComponent.getItems();
                    double count = itemData.size();

                    itemData.get(slot)
                        .triggerDrop(
                            entity,
                            calculateDropPosition(
                                positionComponent, slot / count));
                    System.out.println("Item at " + slot + " removed");
                }
            });
    }

    private void accessBagPrintBag(Entity e, int bagSlot) {
        e.getComponent(InventoryComponent.class).ifPresent(ic -> {
            ((InventoryComponent) ic).printBagInventory(bagSlot, -1);
        });
    }

    private void accessBagPrintItem(Entity e, int bagSlot, int itemSlot) {
        e.getComponent(InventoryComponent.class)
            .ifPresent(ic -> {
                if (itemSlot >= 0) {
                    if (itemSlot < ((InventoryComponent) ic).filledBagSlots(bagSlot)) {
                        ((InventoryComponent) ic).printBagInventory(bagSlot, itemSlot);
                    } else {
                        bagItemSlotCounter = 0;
                        ((InventoryComponent) ic).printBagInventory(bagSlot, 0);
                    }
                } else {
                    bagItemSlotCounter = ((InventoryComponent) ic).filledBagSlots(bagSlot) - 1;
                    ((InventoryComponent) ic).printBagInventory(bagSlot, itemSlot);
                }
            });
    }

    private void accessBagUseActiveItem(Entity e, int bagSlot, int itemSlot) {
        e.getComponent(InventoryComponent.class)
            .ifPresent(ic -> {
                if (((InventoryComponent) ic).getBagInventory().get(bagSlot).get(itemSlot).getItemType() == ItemType.Active) {
                    ((InventoryComponent) ic).getBagInventory().get(bagSlot).get(itemSlot).triggerUse(e);
                }
            });
    }

    private void accessBagDropItem(Entity entity, int bagSlot, int itemSlot) {
        entity.getComponent(InventoryComponent.class)
            .ifPresent(ic -> {
                if (bagSlot >= 0 && bagSlot < ((InventoryComponent) ic).filledSlots()) {
                    ((InventoryComponent) ic).removeItemFromBag(
                        ((InventoryComponent) ic).getItems().get(bagSlot),
                        ((InventoryComponent) ic).getBagInventory().get(bagSlot).get(itemSlot));

                    InventoryComponent inventoryComponent =
                        entity.getComponent(InventoryComponent.class)
                            .map(InventoryComponent.class::cast)
                            .orElseThrow(
                                () ->
                                    missingVC());
                    PositionComponent positionComponent =
                        entity.getComponent(PositionComponent.class)
                            .map(PositionComponent.class::cast)
                            .orElseThrow(
                                () ->
                                    missingVC());
                    List<ItemData> itemData = inventoryComponent.getBagItems(bagSlot);
                    double count = itemData.size();

                    itemData.get(itemSlot)
                        .triggerDrop(
                            entity,
                            calculateDropPosition(
                                positionComponent, itemSlot / count));
                    System.out.println("Item at " + itemSlot + " removed");
                }
            });
    }

}

package ecs.systems;

import com.badlogic.gdx.Gdx;
import configuration.KeyboardConfig;
import ecs.components.*;
import ecs.entities.Bag;
import ecs.entities.Entity;
import ecs.entities.Hero;
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
    private int inventoryItemSlotCounter = -1;
    private int bagItemSlotCounter = -1;

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
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.MOVEMENT_UP.get())) {
                inventoryItemSlotCounter++;
                accessInventoryPrintItem(ksd.e, inventoryItemSlotCounter);
            } else if (Gdx.input.isKeyJustPressed(KeyboardConfig.MOVEMENT_DOWN.get())) {
                inventoryItemSlotCounter--;
                accessInventoryPrintItem(ksd.e, inventoryItemSlotCounter);
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INTERACT_WORLD.get())) {
                accessInventoryUseActiveItem(ksd.e, inventoryItemSlotCounter);
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_THROW.get())) {
                accessInventoryDropItem(ksd.e, inventoryItemSlotCounter);
            }

        } else if (bagOpen) {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
                bagOpen = false;
                System.out.println("Bag closed!\n");
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.MOVEMENT_UP.get())) {
                bagItemSlotCounter++;
                accessInventoryPrintItem(ksd.e, bagItemSlotCounter);
            } else if (Gdx.input.isKeyJustPressed(KeyboardConfig.MOVEMENT_DOWN.get())) {
                bagItemSlotCounter--;
                accessInventoryPrintItem(ksd.e, bagItemSlotCounter);
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INTERACT_WORLD.get())) {
                //activate item
            }
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_THROW.get())) {
                accessInventoryDropItem(ksd.e, bagItemSlotCounter);
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
        } else {
            //TODO add bag functionality
        }
    }

    private void accessInventoryUseActiveItem(Entity e, int slot) {
        e.getComponent(InventoryComponent.class)
            .ifPresent(ic -> {
                if (((InventoryComponent) ic).getItems().get(slot).getItemType() == ItemType.Active) {
                    ((InventoryComponent) ic).getItems().get(slot).triggerUse(e);
                }/* else if (((InventoryComponent) ic).getItems().get(slot).getItemType() == ItemType.BAG) {
                    bagOpen=true;
                }*/
            });
    }

    private void accessInventoryDropItem(Entity entity, int slot) {
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

                    IntStream.range(0, itemData.size())
                        .forEach(
                            index ->
                                itemData.get(index)
                                    .triggerDrop(
                                        entity,
                                        calculateDropPosition(
                                            positionComponent, index / count)));
                    System.out.println("Item at " + slot + " removed");
                }
            });
    }



}

package ecs.items.effects;

import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.entities.Entity;
import ecs.items.IOnUse;
import ecs.items.ItemData;

import java.util.Random;

public class HealingEffect implements IOnUse {

    private int healthPoints;
    private Random random = new Random();

    @Override
    public void onUse(Entity e, ItemData item) {
        e.getComponent(HealthComponent.class)
            .ifPresent(hc -> {
                int currentHP = ((HealthComponent) hc).getCurrentHealthpoints();
                int maxHP = ((HealthComponent) hc).getMaximalHealthpoints();

                if (currentHP < maxHP) {
                    int min = 1;

                    healthPoints = random.nextInt(maxHP - min + 1) + min;
                    int finalHP = currentHP + healthPoints;

                    if (finalHP <= maxHP) {
                        ((HealthComponent) hc).setCurrentHealthpoints(finalHP);

                        e.getComponent(InventoryComponent.class)
                            .ifPresent(ic -> {
                                ((InventoryComponent) ic).removeItem(item);
                            });

                        System.out.printf("%s hat %d HP regeneriert!\n", e.id, finalHP);
                    } else {
                        System.out.println("Trank konnte nicht verwendet werden.");
                    }
                } else {
                    System.out.printf("%s hast schon die maximale Anzahl an HP.\n", e.id);
                }
            });
    }
}

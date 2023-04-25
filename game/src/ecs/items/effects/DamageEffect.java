package ecs.items.effects;

import ecs.components.HealthComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.items.IOnUse;
import ecs.items.ItemData;
import ecs.items.WorldItemBuilder;

import java.util.Random;

public class DamageEffect implements IOnUse {

    private int damagePoints;
    private Random random = new Random();

    @Override
    public void onUse(Entity e, ItemData item) {
        e.getComponent(HealthComponent.class)
            .ifPresent(hc -> {
                int maxHP = ((HealthComponent) hc).getMaximalHealthpoints();
                int min = maxHP / 2;
                damagePoints = (random.nextInt(maxHP - min + 1) + min);

                Damage dmg = new Damage(
                    damagePoints,
                    DamageType.PHYSICAL,
                    WorldItemBuilder.buildWorldItem(item)
                );

                ((HealthComponent) hc).receiveHit(dmg);
                System.out.printf("Du hast %d Schaden erhalten!\n", dmg.damageAmount());

            });

    }
}

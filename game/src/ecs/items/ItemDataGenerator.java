package ecs.items;

import ecs.components.stats.DamageModifier;
import ecs.damage.DamageType;
import ecs.items.effects.DamageEffect;
import ecs.items.effects.HealingEffect;
import graphic.Animation;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** Generator which creates a random ItemData based on the Templates prepared. */
public class ItemDataGenerator {
    private static final List<String> missingTexture = List.of("animation/missingTexture.png");

    private List<ItemData> templates =
            List.of(
                    new ItemData(
                            ItemType.Active,
                            ItemCategory.POTION,
                            new Animation(
                                    Collections.singleton(
                                            "items/potions/healthPotion/potion_heal.png"),
                                    1),
                            new Animation(
                                    Collections.singleton(
                                            "items/potions/healthPotion/potion_heal.png"),
                                    1),
                            "Trank der Heilung",
                            "Ein besonderer Trank, der beim Trinken eine zufällige Anzahl Lebenspunkte regeneriert.",
                            new HealingEffect()),
                    new ItemData(
                            ItemType.Active,
                            ItemCategory.POTION,
                            new Animation(
                                    Collections.singleton(
                                            "items/potions/poisonPotion/potion_poison.png"),
                                    1),
                            new Animation(
                                    Collections.singleton(
                                            "items/potions/poisonPotion/potion_poison.png"),
                                    1),
                            "Elixier der Wut",
                            "Ein besonderer Trank, der beim Trinken Schaden verursacht.",
                            new DamageEffect()),
                    new ItemData(
                            ItemType.Active,
                            ItemCategory.WEAPON,
                            new Animation(
                                    Collections.singleton(
                                            "items/weapons/sword/sword_anim_l_f0.png"),
                                    1),
                            new Animation(
                                    Collections.singleton(
                                            "items/weapons/sword/sword_anim_l_f0.png"),
                                    1),
                            "Schwert",
                            "Ein einfaches Schwert.",
                            createDamageModifier(DamageType.PHYSICAL, 1.1f)),
                    new ItemData(
                            ItemType.Basic,
                            ItemCategory.BAG,
                            new Animation(Collections.singleton("items/other/bag_small.png"), 1),
                            new Animation(Collections.singleton("items/other/bag_small.png"), 1),
                            "kleine Tasche",
                            "Eine kleine Tasche, in der bis zu 5 Gegenstände aufbewahrt werden können",
                            5));
    private Random rand = new Random();

    /**
     * @return a new randomItemData
     */
    public ItemData generateItemData() {
        return templates.get(rand.nextInt(templates.size()));
    }

    /**
     * Creates a new damagemodifier
     *
     * @param type type of damage
     * @param value modifier value
     * @return DamageModifier object
     */
    private DamageModifier createDamageModifier(DamageType type, float value) {
        DamageModifier dmg = new DamageModifier();
        dmg.setMultiplier(type, value);
        return dmg;
    }
}

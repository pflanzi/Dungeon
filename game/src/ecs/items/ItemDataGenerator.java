package ecs.items;

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
                            ItemType.Basic,
                            new Animation(missingTexture, 1),
                            new Animation(missingTexture, 1),
                            "Buch",
                            "Ein sehr lehrreiches Buch."),
                    new ItemData(
                            ItemType.Basic,
                            new Animation(missingTexture, 1),
                            new Animation(missingTexture, 1),
                            "Tuch",
                            "Ein sauberes Tuch.."),
                    new ItemData(
                            ItemType.Basic,
                            new Animation(missingTexture, 1),
                            new Animation(missingTexture, 1),
                            "Namensschild",
                            "Ein Namensschild wo der Name nicht mehr lesbar ist.."),
                    new ItemData(
                            ItemType.Active,
                            ItemCategory.POTION,
                            new Animation(Collections.singleton("items/potions/healthPotion/potion_heal.png"), 1),
                            new Animation(Collections.singleton("items/potions/healthPotion/potion_heal.png"), 1),
                            "Trank der Heilung",
                            "Ein besonderer Trank, der beim Trinken eine zufällige Anzahl Lebenspunkte regeneriert.",
                            new HealingEffect()),
                    new ItemData(
                            ItemType.Active,
                            ItemCategory.POTION,
                            new Animation(Collections.singleton("items/potions/healthPotion/potion_heal.png"), 1),
                            new Animation(Collections.singleton("items/potions/healthPotion/potion_heal.png"), 1),
                            "Elixier der Wut",
                            "Ein besonderer Trank, der beim Trinken Schaden verursacht.",
                            new DamageEffect()),
                    new ItemData(
                            ItemType.Basic,
                            ItemCategory.WEAPON,
                            new Animation(Collections.singleton("items/weapons/sword/sword_anim_l_f0.png"), 1),
                            new Animation(Collections.singleton("items/weapons/sword/sword_anim_l_f0.png"), 1),
                            "Schwert",
                            "Ein einfaches Schwert.")
            );
    private Random rand = new Random();

    /**
     * @return a new randomItemData
     */
    public ItemData generateItemData() {
        return templates.get(rand.nextInt(templates.size()));
    }
}

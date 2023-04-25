package ecs.items.effects;

import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.items.IOnUse;
import ecs.items.ItemData;

import java.util.Random;

public class SpeedEffect implements IOnUse {
    private Random random = new Random();

    @Override
    public void onUse(Entity e, ItemData item) {
        e.getComponent(VelocityComponent.class)
            .ifPresent(vc -> {
                float maxXSpeed = ((VelocityComponent) vc).getXVelocity();
                float maxYSpeed = ((VelocityComponent) vc).getYVelocity();

                float min = maxXSpeed / 4;
                float max = maxXSpeed / 2;

                float velocityBoost = random.nextFloat(max + 0.01f) + min;

                ((VelocityComponent) vc).setCurrentXVelocity(maxXSpeed + velocityBoost);
                ((VelocityComponent) vc).setCurrentYVelocity(maxYSpeed + velocityBoost);

                System.out.printf("Deine Geschwindigkeit wurde um %f erhöht", velocityBoost);
            });
    }
}

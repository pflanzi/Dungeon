package ecs.components.interactions;

import ecs.components.IInteraction;
import ecs.entities.Entity;
import ecs.entities.Lever;


public class LeverInteraction implements IInteraction {

    /**
     * Deactivates the trap associated with the lever
     */
    @Override
    public void onInteraction(Entity entity) {
        ((Lever) entity).getTrap().deactivate();
    }
}

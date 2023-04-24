package ecs.components.interactions;

import ecs.components.IInteraction;
import ecs.entities.Entity;
import ecs.entities.Lever;
import ecs.entities.Trap;


public class leverInteraction implements IInteraction {


    @Override
    /**
     * Deactives the trap associated with the lever
     */
    public void onInteraction(Entity entity) {
        ((Lever) entity).getTrap().deactivate();
    }
}

package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.interactions.LeverInteraction;
import graphic.Animation;

public class Lever extends Entity {

    private Trap trap;
    private final String idlePath = "dungeon/default/floor/leverDefault";
    private AnimationComponent animationComponent;
    IInteraction interact;

    /**
     * Creates a lever which can deactivate the trap it's connected to
     *
     * @param trap the trap to connect the lever to
     */
    public Lever(Trap trap) {
        super();
        setTrap(trap);
        new PositionComponent(this);
        new HitboxComponent(this);

        interact = new LeverInteraction();

        new InteractionComponent(this, InteractionComponent.DEFAULT_RADIUS, false, interact);
        Animation idleAnimation = AnimationBuilder.buildAnimation(idlePath);
        animationComponent = new AnimationComponent(this, idleAnimation, idleAnimation);

    }

    public void setTrap(Trap t) {
        this.trap = t;
    }

    public Trap getTrap() {
        return this.trap;
    }

}

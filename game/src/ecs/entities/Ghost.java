package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.FollowHeroWalk;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.FriendlyTransition;
import ecs.components.ai.transition.ITransition;
import graphic.Animation;

public class Ghost extends Entity {

    private Tombstone tombstone;
    private float speed = 0.06f;

    // Ghost textures source: https://dyru.itch.io/pixel-ghost-template
    private String pathToAnimationLeft = "character/ghost/animationLeft";
    private String pathToAnimationRight = "character/ghost/animationRight";
    private IIdleAI idleAI = new FollowHeroWalk();
    boolean isVisible;

    /** Creates a new Ghost and connects it with a corresponding tombstone. */
    public Ghost() {
        super();

        tombstone = new Tombstone(this);
        isVisible = true;

        setupPositionComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        setupVelocityComponent();
        setupAIComponent(idleAI);
    }

    private void setupPositionComponent() {
        new PositionComponent(this);
    }

    private void setupAnimationComponent() {
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToAnimationLeft);
        Animation idleRight = AnimationBuilder.buildAnimation(pathToAnimationRight);

        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> System.out.println("ghostCollisionEnter"),
                (you, other, direction) -> System.out.println("ghostCollisionLeave"));
    }

    private void setupVelocityComponent() {
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToAnimationLeft);
        Animation moveRight = AnimationBuilder.buildAnimation(pathToAnimationRight);

        new VelocityComponent(this, speed, speed, moveLeft, moveRight);
    }

    private void setupAIComponent(IIdleAI idleAI) {
        ITransition transitionAI = new FriendlyTransition();
        new AIComponent(this, null, idleAI, transitionAI);
    }

    /**
     * Makes the ghost visible or invisible by (re-)setting the Animation and VelocityComponent with
     * a new empty/transparent texture.
     */
    public void changeVisibility() {
        if (isVisible) {
            pathToAnimationLeft = "hud/empty.png";
            pathToAnimationRight = "hud/empty.png";
        } else {
            pathToAnimationLeft = "character/ghost/animationLeft";
            pathToAnimationRight = "character/ghost/animationRight";
        }

        toggleVisibility();
        setupAnimationComponent();
        setupVelocityComponent();
    }

    private void toggleVisibility() {
        isVisible = !isVisible;
    }
}

package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.FollowHeroWalk;
import ecs.components.ai.idle.IIdleAI;
import graphic.Animation;

public class Ghost extends Entity {

    private Tombstone tombstone;
    private float speed = 0.2f;

    // Ghost textures source: https://dyru.itch.io/pixel-ghost-template
    private String pathToAnimationLeft = "character/ghost/animationLeft";
    private String pathToAnimationRight = "character/ghost/animationRight/ghostIdleRight1.png";
    private IIdleAI idleAI = new FollowHeroWalk();


    /**
     * Creates a new Ghost and connects it with a corresponding tombstone
     */
    public Ghost() {
        super();

        tombstone = new Tombstone(this);
        setupPositionComponent();
        setupAnimationComponent();
        setupVelocityComponent();
        setupHitboxComponent();
        setupAIComponent(idleAI);
    }

    /**
     * Creates the ghost's idle animation
     */
    private void setupAnimationComponent() {
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToAnimationLeft);
        Animation idleRight = AnimationBuilder.buildAnimation(pathToAnimationRight);

        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupAIComponent(IIdleAI idleAI) {
        new AIComponent(this, idleAI);
    }

    private void setupPositionComponent() {}

    private void setupVelocityComponent() {}

    private void setupHitboxComponent() {}

}

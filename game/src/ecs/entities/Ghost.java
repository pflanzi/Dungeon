package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.FollowHeroWalk;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.FriendlyTransition;
import ecs.components.ai.transition.ITransition;
import graphic.Animation;

import java.util.Optional;

public class Ghost extends Entity {

    private Tombstone tombstone;
    private float speed = 0.08f;

    // Ghost textures source: https://dyru.itch.io/pixel-ghost-template
    private String pathToAnimationLeft = "character/ghost/animationLeft";
    private String pathToAnimationRight = "character/ghost/animationRight";
    private IIdleAI idleAI = new FollowHeroWalk();


    /**
     * Creates a new Ghost and connects it with a corresponding tombstone.
     */
    public Ghost() {
        super();

        tombstone = new Tombstone(this);

        setupPositionComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        setupVelocityComponent();
        setupAIComponent(idleAI);
    }

    private void setupPositionComponent() {
        new PositionComponent(this);
    }

    /**
     * Creates the ghost's idle animation
     */
    private void setupAnimationComponent() {
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToAnimationLeft);
        Animation idleRight = AnimationBuilder.buildAnimation(pathToAnimationRight);

        new AnimationComponent(this, idleLeft, idleRight);
    }

    /**
     * Adds a hitbox to the ghost.
     */
    private void setupHitboxComponent() {
        new HitboxComponent(this,
            (you, other, direction) -> System.out.println("ghostCollisionEnter"),
            (you, other, direction) -> System.out.println("ghostCollisionLeave"));
    }

    /**
     * Adds a walking animation and speed to the ghost.
     */
    private void setupVelocityComponent() {
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToAnimationLeft);
        Animation moveRight = AnimationBuilder.buildAnimation(pathToAnimationRight);

        new VelocityComponent(this, speed, speed, moveLeft, moveRight);
    }

    /**
     * Adds AI behaviour to the ghost.
     * Follows the hero around while maintaining a small distance.
     * @param idleAI AI behaviour
     */
    private void setupAIComponent(IIdleAI idleAI) {
        ITransition transitionAI = new FriendlyTransition();
        new AIComponent(this, null, idleAI, transitionAI);
    }

}

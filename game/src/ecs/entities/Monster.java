package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.skill.*;
import graphic.Animation;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
abstract public class Monster extends Entity { //abstract =  bauanleitungsklasse, davon soll kein objekt erstellt werden können


    /*private final String pathToIdleLeft = "monster/chort/idleLeft";
    private final String pathToIdleRight = "monster/chort/idleRight";
    private final String pathToRunLeft = "monster/chort/runLeft";
    private final String pathToRunRight = "monster/chort/runRight";*/

    private final String pathToIdleLeft = "monster/demon/idleLeft";
    private final String pathToIdleRight = "monster/demon/idleRight";
    private final String pathToRunLeft = "monster/demon/runLeft";
    private final String pathToRunRight = "monster/demon/runRight";

    /** Entity with Components */
    public Monster(
        int healthpoints,
        int dmg,
        float xSpeed,
        float ySpeed,
        String pathToIdleLeft,
        String pathToIdleRight,
        String pathToRunLeft,
        String pathToRunRight)
    {
        super();
        setupVelocityComponent(xSpeed, ySpeed, pathToRunRight, pathToRunLeft  );
        setupAnimationComponent(pathToIdleRight,pathToRunLeft);
        setupHitboxComponent();
        setupPositionComponent();
        setupAIComponent();
    }


    private void setupVelocityComponent(float xSpeed, float ySpeed, String pathToRunRight, String pathToRunLeft) {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent(String pathToIdleRight, String pathToIdleLeft) {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }


    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> System.out.println("heroCollisionEnter"),
            (you, other, direction) -> System.out.println("heroCollisionLeave"));
    }

    private void setupPositionComponent() {

        new PositionComponent(this);

    }

    private void setupAIComponent() {

        new AIComponent(this);



    }



}

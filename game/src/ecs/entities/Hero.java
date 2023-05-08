package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import graphic.Animation;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity {

    private final int fireballCoolDown = 1;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;

    private int health = 20;

    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
    private final String pathToHitRight = "knight/hit";
    private Skill firstSkill;
    private Skill secondSkill;

    private  Animation hitRight;

    /** Entity with Components */
    public Hero() {
        super();
        new PositionComponent(this);
        new InventoryComponent(this, 10);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        setupHealthComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupMindcontrollSkill();
        //setupFireballSkill();
        setupGodmodeSkill();
        SkillComponent skillComponent = new SkillComponent(this);
        skillComponent.addSkill(firstSkill);
        skillComponent.addSkill(secondSkill);

        pc.setSkillSlot1(firstSkill);
        pc.setSkillSlot2(secondSkill);
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);

        hitRight = AnimationBuilder.buildAnimation(pathToHitRight);

        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupFireballSkill() {
        firstSkill =
                new Skill(
                        new FireballSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
    }
    private void setupMindcontrollSkill(){
        firstSkill =
                new Skill(
                    new MindcontrollSkill(), 25);
    }

    private void setupGodmodeSkill(){
        secondSkill =
                new Skill(
                    new GodmodeSkill(), 15, 10);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
                this,null,null);
    }

    private void setupHealthComponent() {
        new HealthComponent(this,health,null,hitRight,hitRight);
    }
}

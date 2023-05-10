package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import graphic.Animation;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity implements ILevelUp{

    private final int fireballCoolDown = 1;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;

    private int health = 20;
    private int dmg = 1;

    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
    private final String pathToHitRight = "knight/hit";
    private Skill firstSkill;
    private Skill secondSkill;

    private  Animation hitRight;
    private PlayableComponent pc;
    private SkillComponent skillComponent;

    /** Entity with Components */
    public Hero() {
        super();
        new PositionComponent(this);
        new InventoryComponent(this, 10);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        setupHealthComponent();
        this.pc = new PlayableComponent(this);
        this.skillComponent = new SkillComponent(this);
        setupFireballSkill();
        setupXPComponent();
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
                new FireballSkill(this.dmg, SkillTools::getCursorPositionAsPoint), fireballCoolDown);
        this.pc.setSkillSlot1(firstSkill);
        this.skillComponent.addSkill(firstSkill);
    }

    private void setupMindcontrollSkill(){
        firstSkill =
            new Skill(
                new MindcontrollSkill(), 25, 5);
        this.pc.setSkillSlot1(firstSkill);
        skillComponent.addSkill(firstSkill);
    }

    private void setupGodmodeSkill(){
        secondSkill =
            new Skill(
                new GodmodeSkill(), 15, 10);
        this.pc.setSkillSlot2(secondSkill);
        skillComponent.addSkill(secondSkill);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
            this,null,null);
    }

    private void setupXPComponent() {
        XPComponent xpcomponent = new XPComponent(this, this::onLevelUp);
        xpcomponent.setCurrentLevel(1);
    }


    private void setupHealthComponent() {
        new HealthComponent(this,health,null,hitRight,hitRight);
    }

    /**
     * Determins what to do on levelup
     * @param nexLevel is the new level of the entity
     */
    @Override
    public void onLevelUp(long nexLevel) {
        this.health += 2;
        this.dmg += 1;
        System.out.println("Hero gained +2 health and +1 damage\n");
        if(nexLevel == 5){
            this.setupMindcontrollSkill();
            System.out.println("Hero gained the skill Mindcontroll, to use it, press q\n");
        }
        if(nexLevel == 10){
            this.setupGodmodeSkill();
            System.out.println("Hero gained the skill Godmode, to use it, press r\n");
        }
    }
}


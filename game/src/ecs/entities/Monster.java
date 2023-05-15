package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.ai.transition.SelfDefendTransition;
import ecs.components.skill.*;
import ecs.components.xp.XPComponent;
import graphic.Animation;

import java.util.Collections;

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

    private AIComponent aiComponent;

    private IIdleAI defaultIdle;
    private IFightAI defaultFight;

    /** Entity with Components
     * param lootAmount amount of loot that should be dropped on death.
     */
    public Monster(
        int healthpoints,
        int dmg,
        int scaling,
        float xSpeed,
        float ySpeed,
        String pathToIdleLeft,
        String pathToIdleRight,
        String pathToRunLeft,
        String pathToRunRight,
        long XPonDeath)
    {
        super();
        setupVelocityComponent(xSpeed, ySpeed, pathToRunRight, pathToRunLeft  );
        setupAnimationComponent(pathToIdleRight,pathToRunLeft);
        setupHitboxComponent();
        setupPositionComponent();
        setupAIComponent();
        new HealthComponent(this, healthpoints * scaling, lf->{System.out.println("Mob died\n");}, new Animation(Collections.singleton(pathToIdleLeft),1), new Animation(Collections.singleton(pathToIdleLeft),1));
        setupXPComponent(XPonDeath);
    }

    /** Entity with Components with custom IdleAI */
    public Monster(
        int healthpoints,
        int dmg,
        int scaling,
        float xSpeed,
        float ySpeed,
        String pathToIdleLeft,
        String pathToIdleRight,
        String pathToRunLeft,
        String pathToRunRight,
        IIdleAI IdleAI,
        long XPonDeath)
    {
        super();
        setupVelocityComponent(xSpeed, ySpeed, pathToRunRight, pathToRunLeft  );
        setupAnimationComponent(pathToIdleRight,pathToRunLeft);
        setupHitboxComponent();
        setupPositionComponent();
        setupAIComponent(IdleAI);
        new HealthComponent(this, healthpoints * scaling, lf->{System.out.println("Mob died\n");}, new Animation(Collections.singleton(pathToIdleLeft),1), new Animation(Collections.singleton(pathToIdleLeft),1));
        setupXPComponent(XPonDeath);

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

    private void setupAIComponent(IIdleAI idleAI) {
        ITransition transitionAI = new RangeTransition(5f);
        IFightAI fightAI = new CollideAI(2f);
        aiComponent = new AIComponent(this, fightAI, idleAI, transitionAI);
    }

    public void setStrategy(IIdleAI idle, IFightAI fight){
        aiComponent.setIdleAI(idle);
        aiComponent.setFightAI(fight);
    }

    private void setupXPComponent(long XPonDeath) {
        XPComponent xpcomponent = new XPComponent(this);
        xpcomponent.setLootXP(XPonDeath);
    }

}

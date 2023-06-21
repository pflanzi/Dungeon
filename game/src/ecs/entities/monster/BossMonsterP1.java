package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.skill.*;
import ecs.entities.Entity;

public class BossMonsterP1 extends BossMonster {

    private static final int healthpoints = 5;
    private static final int dmg = 2;
    private static final float xSpeed = 0.025f;
    private static final float ySpeed = 0.025f;
    private static final long XPonDeath = 10;

    private static final String pathToIdleLeft = "monster/BossMonster/idleLeft";
    private static final String pathToIdleRight = "monster/BossMonster/idleRight";
    private static final String pathToRunLeft = "monster/BossMonster/runLeft";
    private static final String pathToRunRight = "monster/BossMonster/runRight";

    // private SkillComponent skillComponent;

    // private Skill firstSkill;
    // private final int fireballCoolDown = 3;

    // private AIComponent aiComponent;

    public BossMonsterP1(int scaling) {
        super(
                healthpoints,
                dmg,
                scaling,
                xSpeed,
                ySpeed,
                pathToIdleLeft,
                pathToIdleRight,
                pathToRunLeft,
                pathToRunRight,
                XPonDeath);
        setupHealthComponent();

        // this.skillComponent = new SkillComponent(this);
        // new AIComponent(this);
        // PositionComponent positionComponent = this.getComponent(AIComponent.class);

    }

    private void setupHealthComponent() {
        int i = 10;

        PositionComponent epc =
                (PositionComponent)
                        this.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));

        HealthComponent healthComponent =
                this.getComponent(HealthComponent.class)
                        .map(HealthComponent.class::cast)
                        .orElseThrow(() -> new MissingComponentException("HealthComponent"));
        new HealthComponent(
                this,
                healthpoints,
                new IOnDeathFunction() {
                    @Override
                    public void onDeath(Entity entity) {
                        new BossMonsterP2(epc.getPosition());
                    }
                },
                AnimationBuilder.buildAnimation(pathToRunLeft),
                AnimationBuilder.buildAnimation(pathToIdleLeft));
    }
}

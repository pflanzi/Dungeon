package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import starter.Game;
import tools.Point;

public class DamageMeleeSkill implements ISkillFunction {

    private String pathToTexturesOfProjectileR;
    private String pathToTexturesOfProjectileL;
    private float projectileSpeed;

    private float projectileRange;
    private Damage projectileDamage;
    private Point projectileHitboxSize;
    private float recoilMagnitude;

    public DamageMeleeSkill(
            String pathToTexturesOfProjectileR,
            String pathToTexturesOfProjectileL,
            float projectileSpeed,
            Damage projectileDamage,
            Point projectileHitboxSize,
            float projectileRange,
            float recoilMagnitude) {
        this.pathToTexturesOfProjectileR = pathToTexturesOfProjectileR;
        this.pathToTexturesOfProjectileL = pathToTexturesOfProjectileL;
        this.projectileDamage = projectileDamage;
        this.projectileSpeed = projectileSpeed;
        this.projectileRange = projectileRange;
        this.projectileHitboxSize = projectileHitboxSize;
        this.recoilMagnitude = recoilMagnitude;
    }

    /**
     * Executes the melee skill, builds the entity and deals damage if it hits another entity
     *
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {
        // Create entity
        Entity hit = new Entity();
        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));

        new PositionComponent(hit, epc.getPosition());

        Animation animationR = AnimationBuilder.buildAnimation(pathToTexturesOfProjectileR);
        Animation animationL = AnimationBuilder.buildAnimation(pathToTexturesOfProjectileL);
        new AnimationComponent(hit, animationL, animationR);
        Point target = epc.getPosition();

        // Set target point of projectile based on last walked direction
        switch (((Hero) entity).getLastInputDirection()) {
            case UP -> {
                target.y += 0.5f;
                epc.setPosition(new Point(epc.getPosition().x, epc.getPosition().y - 0.5f));
            }
            case DOWN -> {
                target.y -= 0.5f;
                epc.setPosition(new Point(epc.getPosition().x, epc.getPosition().y + 0.5f));
            }
            case RIGHT -> {
                target.x += 0.5f;
                epc.setPosition(new Point(epc.getPosition().x - 0.5f, epc.getPosition().y));
            }
            case LEFT -> {
                target.x -= 0.5f;
                epc.setPosition(new Point(epc.getPosition().x + 0.5f, epc.getPosition().y));
            }
            default -> throw new IllegalStateException(
                    "Unexpected value: " + ((Hero) entity).getLastInputDirection());
        }

        Point targetPoint =
                SkillTools.calculateLastPositionInRange(epc.getPosition(), target, projectileRange);
        Point velocity =
                SkillTools.calculateVelocity(epc.getPosition(), targetPoint, projectileSpeed);
        VelocityComponent vc =
                new VelocityComponent(hit, velocity.x, velocity.y, animationL, animationR);
        new ProjectileComponent(hit, epc.getPosition(), targetPoint);
        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc).receiveHit(projectileDamage);
                                            SkillTools.executeKnockback(a, b, recoilMagnitude);
                                            Game.removeEntity(hit);
                                        });
                    }
                };

        new HitboxComponent(hit, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);
    }
}

package ecs.components.skill;

import ecs.components.HealthComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.MindcontrolledFight;
import ecs.components.ai.idle.MindControlledIdle;
import ecs.components.ai.transition.MindcontrolledTransition;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.entities.Monster;
import starter.Game;

/**
 * Changes the strategy of closest Monster to try to kill the Monster closest to him
 */
public class MindcontrollSkill extends TimeBasedSkill {

    private Entity usedOn, targetEntity;

    @Override
    public void execute(Entity entity) {
        usedOn = AITools.closestMonsterToEntity(entity);
        targetEntity = AITools.closestMonsterToEntity(usedOn);
        if (usedOn != null && targetEntity != null) {
            usedOn.getComponent(AIComponent.class)
                .ifPresent(
                    AI -> {
                        ((AIComponent) AI).setIdleAI(new MindControlledIdle(targetEntity));
                        ((AIComponent) AI).setFightAI(new MindcontrolledFight(2f, targetEntity));
                        ((AIComponent) AI).setTransitionAI(new MindcontrolledTransition(5f));
                        System.out.println("Strategie von Monster " + usedOn.id + " Verändert\nSein Ziel ist Monster "+targetEntity.id+"\n");
                    });
        }else{
            System.out.println("Zauber schlug fehl!\n");
        }
    }

    /**
     * Kills the mindcontrolled Monster, called some seconds after activation
     */
    public void deactivate(){
        //@Todo Delete after presentation of functionality
        usedOn.getComponent(HealthComponent.class)
            .ifPresent(hc -> {
                ((HealthComponent)hc).receiveHit(new Damage(500, DamageType.PHYSICAL, Game.getHero().get()));
            });
    }
}

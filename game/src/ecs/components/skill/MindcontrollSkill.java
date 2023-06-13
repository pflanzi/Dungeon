package ecs.components.skill;

import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.fight.MindcontrolledFight;
import ecs.components.ai.idle.MindControlledIdle;
import ecs.components.ai.transition.MindcontrolledTransition;
import ecs.entities.Entity;

/** Changes the strategy of closest Monster to try to kill the Monster closest to him */
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
                                ((AIComponent) AI)
                                        .setFightAI(new MindcontrolledFight(2f, targetEntity));
                                ((AIComponent) AI)
                                        .setTransitionAI(new MindcontrolledTransition(5f));
                                System.out.println(
                                        "Strategie von Monster "
                                                + usedOn.id
                                                + " Verändert\nSein Ziel ist Monster "
                                                + targetEntity.id
                                                + "\n");
                            });
        } else {
            System.out.println("Zauber schlug fehl!\n");
        }
    }

    /** Kills the mindcontrolled Monster, called some seconds after activation */
    public void deactivate() {}
}

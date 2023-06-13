package ecs.components.skill;

import ecs.entities.Entity;

public abstract class TimeBasedSkill implements ISkillFunction {

    protected int cooldownInSeconds;
    protected int durationInSeconds;

    protected Entity user;

    @Override
    /** Function to execute the skill */
    public void execute(Entity entity) {}

    /** Function to be execute to deactivate/revert effects of the skill */
    public void deactivate() {}
}

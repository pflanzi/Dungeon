package ecs.components.skill;

import ecs.entities.Entity;
import tools.Constants;

public class Skill {

    private ISkillFunction skillFunction;
    private int coolDownInFrames;
    private int currentCoolDownInFrames;

    private int durationInSeconds;

    /**
     * @param skillFunction Function of this skill
     */
    public Skill(ISkillFunction skillFunction, float coolDownInSeconds) {
        this.skillFunction = skillFunction;
        this.coolDownInFrames = (int) (coolDownInSeconds * Constants.FRAME_RATE);
        this.currentCoolDownInFrames = 0;
        this.durationInSeconds=0;
    }

    public Skill(ISkillFunction skillFunction, float coolDownInSeconds, int durationInSeconds){
        this.durationInSeconds=durationInSeconds;
        this.skillFunction = skillFunction;
        this.coolDownInFrames = (int) (coolDownInSeconds * Constants.FRAME_RATE);
        this.currentCoolDownInFrames = 0;
    }

    /**
     * Execute the method of this skill
     *
     * @param entity entity which uses the skill
     */
    public void execute(Entity entity) {
        if (!isOnCoolDown()) {
            skillFunction.execute(entity);
            activateCoolDown();
        }
    }

    /**
     * @return true if cool down is not 0, else false
     */
    public boolean isOnCoolDown() {
        return currentCoolDownInFrames > 0;
    }

    /** activate cool down */
    public void activateCoolDown() {
        currentCoolDownInFrames = coolDownInFrames;
    }

    /** reduces the current cool down by frame
     *  if the ability is timebased, it checks if the time gone by
     *  since activation is equal to the duration and deactivates if it is
     * */
    public void reduceCoolDown() {
        checkDeactivate();
        currentCoolDownInFrames = Math.max(0, --currentCoolDownInFrames);
    }

    /**
     * Checks if the skill has a duration and calls deactivate if needed
     */
    public void checkDeactivate(){
        if(durationInSeconds>0&&currentCoolDownInFrames%Constants.FRAME_RATE==0){
            if(durationInSeconds == ((coolDownInFrames/Constants.FRAME_RATE) - (currentCoolDownInFrames/Constants.FRAME_RATE))){
                ((TimeBasedSkill)skillFunction).deactivate();
            }
        }
    }
}

package ecs.systems;

import com.badlogic.gdx.Gdx;
import configuration.KeyboardConfig;
import ecs.components.MissingComponentException;
import ecs.components.PlayableComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.tools.interaction.InteractionTool;
import starter.Game;

/** Used to control the player */
public class PlayerSystem extends ECS_System {

    private record KSData(Entity e, PlayableComponent pc, VelocityComponent vc) {}
    private boolean inventoryOpen=false;
    @Override
    public void update() {
        Game.getEntities().stream()
                .flatMap(e -> e.getComponent(PlayableComponent.class).stream())
                .map(pc -> buildDataObject((PlayableComponent) pc))
                .forEach(this::checkKeystroke);
    }

    private void checkKeystroke(KSData ksd) {
        if (!inventoryOpen) {
            if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_UP.get()))
                ksd.vc.setCurrentYVelocity(1 * ksd.vc.getYVelocity());
            else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_DOWN.get()))
                ksd.vc.setCurrentYVelocity(-1 * ksd.vc.getYVelocity());
            else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_RIGHT.get()))
                ksd.vc.setCurrentXVelocity(1 * ksd.vc.getXVelocity());
            else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_LEFT.get()))
                ksd.vc.setCurrentXVelocity(-1 * ksd.vc.getXVelocity());

            if (Gdx.input.isKeyPressed(KeyboardConfig.INTERACT_WORLD.get()))
                InteractionTool.interactWithClosestInteractable(ksd.e);
            else if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
                inventoryOpen=true;
                System.out.println("Inventory opened!\n");

            }
            // check skills
            else if (Gdx.input.isKeyPressed(KeyboardConfig.FIRST_SKILL.get()))
                ksd.pc.getSkillSlot1().ifPresent(skill -> skill.execute(ksd.e));
            else if (Gdx.input.isKeyPressed(KeyboardConfig.SECOND_SKILL.get()))
                ksd.pc.getSkillSlot2().ifPresent(skill -> skill.execute(ksd.e));
        } else {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.MOVEMENT_UP.get())) {
                //inv up
            }
            else if (Gdx.input.isKeyJustPressed(KeyboardConfig.MOVEMENT_DOWN.get())) {
                //inv down
            } else if (Gdx.input.isKeyJustPressed(KeyboardConfig.INTERACT_WORLD.get())) {
                //activate item
            } else if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
                inventoryOpen=false;
                System.out.println("Inventory closed!\n");
            }

        }
    }
    private KSData buildDataObject(PlayableComponent pc) {
        Entity e = pc.getEntity();

        VelocityComponent vc =
                (VelocityComponent)
                        e.getComponent(VelocityComponent.class)
                                .orElseThrow(PlayerSystem::missingVC);

        return new KSData(e, pc, vc);
    }

    private static MissingComponentException missingVC() {
        return new MissingComponentException("VelocityComponent");
    }
}

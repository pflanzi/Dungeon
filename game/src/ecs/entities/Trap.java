package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.collision.ICollide;
import ecs.components.collision.TrapDamageCollision;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import graphic.Animation;

/**
 * Traps deal damage when entities walk into them
 */
public class Trap extends Entity{

    private String pathToVisibleIdle = "dungeon/default/floor/trapsVisible";
    private String pathToHiddenIdle = "dungeon/default/floor/trapsHidden";
    private String pathToBloodyIdle = "dungeon/default/floor/trapsBloody";
    private String pathtoDeactivatedIdle = "dungeon/default/floor/trapsDeactivated";
    private boolean visible, deactivatable, repeatable;

    private int damage;

    private ICollide onCollision;

    private AnimationComponent animationComponent;
    private HitboxComponent hitboxComponent;
    /**
     * Creates a trap
     * @param scalingFactor factor to increase trap damage
     */
    public Trap(int scalingFactor){
        super();
        damage = 3 * scalingFactor;

        visible = randomBoolean(0.6);
        deactivatable = randomBoolean(0.3);
        repeatable = randomBoolean(0.1);

        new PositionComponent(this);
        if(visible) {
            Animation idleAnimation = AnimationBuilder.buildAnimation(pathToVisibleIdle);
            animationComponent = new AnimationComponent(this, idleAnimation, idleAnimation);
        }else{
            Animation idleAnimation = AnimationBuilder.buildAnimation(pathToHiddenIdle);
            animationComponent = new AnimationComponent(this, idleAnimation, idleAnimation);

        }
        onCollision = new TrapDamageCollision();

        hitboxComponent = new HitboxComponent(
            this,
            onCollision,
            null
        );

        System.out.println("Trap has been created, visible: "+visible+" ,deactivatable: "+deactivatable+" repeatable: "+repeatable+" Damage: "+damage+"\n");
    }

    public boolean isDeactivatable() {
        return deactivatable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public Damage getDamage() {
        return new Damage(damage, DamageType.PHYSICAL, this);
    }

    /**
     * Returns random boolean value, if propability is > 1 the chances are 50/50
     * @param propability propability to be true, needs to be a double < 1
     * @return true or false
     */
    private boolean randomBoolean(double propability){
        if(propability<1){
            return Math.random() < propability;
        }else{
            return Math.random() < 0.5;
        }
    }

    /**
     * Changes trap texture to bloody
     */
    public void setTrapToUsed(){
        Animation usedTrap = AnimationBuilder.buildAnimation(pathToBloodyIdle);
        animationComponent.setCurrentAnimation(usedTrap);
    }

    /**
     * Deactivates the trap and changes the textures
     */
    public void deactivate(){
        hitboxComponent.setiCollideEnter(null);
        Animation deactivedAnimation = AnimationBuilder.buildAnimation(pathtoDeactivatedIdle);
        animationComponent.setCurrentAnimation(deactivedAnimation);
    }
}

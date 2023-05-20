package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;


public class MeleeAttackSkill extends DamageMeleeSkill {

    /**
     * Creates an instance of a melee attack
     * @param dmg the damage the attack will deal
     */
    public MeleeAttackSkill(int dmg) {
        super(
            "knight/attack_r",
            "knight/attack_l",
            0.11f,
            new Damage(dmg, DamageType.PHYSICAL, null),
            new Point(0.5f, 0.5f),
            1.2f,
            0.8f);
    }
}

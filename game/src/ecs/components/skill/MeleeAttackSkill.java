package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class MeleeAttackSkill extends DamageMeleeSkill {
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

package ecs.entities;

import ecs.components.PositionComponent;

public class Necromancer extends RangedMonster {

    private final int healthpoints = 7;


    private final float xSpeed = 0.2f;
    private final float ySpeed = 0.2f;

    private final int dmg = 5;






    public Necromancer() {
        super();
        /*new PositionComponent(this);
        super.setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();

        setupPositionComponent();
        setupAIComponent();*/

    }


}

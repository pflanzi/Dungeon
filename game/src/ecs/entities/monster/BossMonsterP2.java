package ecs.entities.monster;

import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.entities.Chest;
import tools.Point;

public class BossMonsterP2 extends BossMonster {

    private static final int healthpoints = 10;
    private static final int dmg = 3;
    private static final float xSpeed = 0.05f;
    private static final float ySpeed = 0.05f;
    private static final long XPonDeath = 10;
    private static final int scaling = 1;

    private static final String pathToIdleLeft = "monster/BossMonster2/idleLeft";
    private static final String pathToIdleRight = "monster/BossMonster2/idleRight";
    private static final String pathToRunLeft = "monster/BossMonster2/runLeft";
    private static final String pathToRunRight = "monster/BossMonster2/runRight";

    /** Creates a new BossMonsterP2. */

    public BossMonsterP2(Point point) {
        super(healthpoints, dmg, scaling, xSpeed, ySpeed, pathToIdleLeft, pathToIdleRight, pathToRunLeft, pathToRunRight, XPonDeath);

        setupPositionComponent(point);
    }

    /** Set the Position of BossMonsterP2 */

    private void setupPositionComponent(Point point) {

        new PositionComponent(this, point);
    }



}

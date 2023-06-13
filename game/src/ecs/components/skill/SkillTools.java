package ecs.components.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import starter.Game;
import tools.Point;

public class SkillTools {

    /**
     * calculates the last position in range regardless of aimed position
     *
     * @param startPoint position to start the calculation
     * @param aimPoint   point to aim for
     * @param range      range from start to
     * @return last position in range if you follow the directon from startPoint to aimPoint
     */
    public static Point calculateLastPositionInRange(
        Point startPoint, Point aimPoint, float range) {

        // calculate distance from startPoint to aimPoint
        float dx = aimPoint.x - startPoint.x;
        float dy = aimPoint.y - startPoint.y;

        // vector from startPoint to aimPoint
        Vector2 scv = new Vector2(dx, dy);

        // normalize the vector (length of 1)
        scv.nor();

        // resize the vector to the length of the range
        scv.scl(range);

        return new Point(startPoint.x + scv.x, startPoint.y + scv.y);
    }

    public static Point calculateVelocity(Point start, Point goal, float speed) {
        float x1 = start.x;
        float y1 = start.y;
        float x2 = goal.x;
        float y2 = goal.y;

        float dx = x2 - x1;
        float dy = y2 - y1;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float velocityX = dx / distance * speed;
        float velocityY = dy / distance * speed;
        return new Point(velocityX, velocityY);
    }

    /**
     * gets the current cursor position as Point
     *
     * @return mouse cursor position as Point
     */
    public static Point getCursorPositionAsPoint() {
        Vector3 mousePosition =
            Game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Point(mousePosition.x, mousePosition.y);
    }

    /**
     * Knocks back an Entity after recieving a hit
     *
     * @param attacker Entity that performs the hit
     * @param reciever Entity recieving the hit
     */
    public static void executeKnockback(Entity attacker, Entity reciever, float recoilMagnitude) {
        PositionComponent attPos, recPos;
        attPos = (PositionComponent) attacker.getComponent(PositionComponent.class).orElseThrow(() -> new MissingComponentException("PositionComponent"));
        recPos = (PositionComponent) reciever.getComponent(PositionComponent.class).orElseThrow(() -> new MissingComponentException("PositionComponent"));
        Point knockbackPos = calculateKnockback(attPos.getPosition(),recPos.getPosition(), recoilMagnitude);
        if(isAccessible(knockbackPos))
            recPos.setPosition(knockbackPos);
    }

    /**
     * Calculates where to knock the entity after recieving a hit
     * @param posA Position of entity attacking
     * @param posB Position of entity recieving the hit
     * @param recoilMagnitude How far to knock the entity back
     * @return new Position of entity after knockback
     */
    private static Point calculateKnockback(Point posA, Point posB, float recoilMagnitude){
        float attackerX = posA.x;
        float attackerY = posA.y;

        float enemyX = posB.x;
        float enemyY = posB.y;

        float diffX = attackerX - enemyX;
        float diffY = attackerY - enemyY;

        float distance = (float) Math.sqrt(diffX * diffX + diffY * diffY);

        float directionX = diffX / distance;
        float directionY = diffY / distance;

        float recoilX = -directionX * recoilMagnitude;
        float recoilY = -directionY * recoilMagnitude;

        enemyX += recoilX;
        enemyY += recoilY;

        return new Point(enemyX,enemyY);
    }

    /**
     * Checks if a tile is accessible
     * @param p point of the tile
     * @return true if the tile is accessible
     */
    private static boolean isAccessible(Point p){
        if(Game.currentLevel.getTileAt(p.toCoordinate()).isAccessible())
            return true;
        return false;
    }
}

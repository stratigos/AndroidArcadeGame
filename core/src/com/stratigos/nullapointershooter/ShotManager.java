package com.stratigos.nullapointershooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle shooting of projectiles from a Sprite. Handles number of shots fired, range, animation, etc.
 * Created by stratigos on 7/25/15 (via Pluralsight Android Game App Dev course).
 */
public class ShotManager
{
    /**
     * Position of center of Y axis from which the Sprite's weapons begin to appear. Used to determine starting location
     *  of projectile animation.
     */
    public static final int SHOT_Y_OFFSET = 6;

    /**
     * Value to store the velocity of the projectile.
     */
    public static final int SHOT_SPEED = 300;

    /**
     * Texture to hold the Sprite map of the animated shot.
     */
    private final Texture shotTexture;

    /**
     * List of currently active shots / projectiles from the ship.
     */
    private List<AnimatedSprite> shots = new ArrayList<AnimatedSprite>;

    public ShotManager(Texture shotTexture)
    {
        this.shotTexture = shotTexture;
    }

    /**
     * Fires projectiles from Sprite, handling animation of the projectile / weapon.
     * @param shipCenterXLocation The center of the Sprite with a weapon.
     */
    public void firePlayerShot(int shipCenterXLocation)
    {
        if (canFireShot()) {
            Sprite newShot                 = new Sprite(shotTexture);
            AnimatedSprite newShotAnimated = new AnimatedSprite(newShot);

            newShotAnimated.setPosition(shipCenterXLocation, SHOT_Y_OFFSET);
            newShotAnimated.setVelocity(new Vector2(0, SHOT_SPEED)); // Shot only moves on the Y axis, so X is zero.

            // Add new shot to list of current shots.
            shots.add(newShotAnimated);
        }
    }

    public void update()
    {
         for (AnimatedSprite shot : shots) {
             shot.move();
         }
    }

    /**
     * Determines if Sprite is able to fire a shot or not, based on throttling criteria.
     * @return Boolean
     */
    private boolean canFireShot() {
        return true; // Mocking this for now, will add logic / handling / throttling later.
    }


}

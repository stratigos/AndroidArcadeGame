package com.stratigos.nullapointershooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private static final int SHOT_Y_OFFSET = 110;

    /**
     * Value to store the velocity of the projectile.
     */
    private static final int SHOT_SPEED = 300;

    /**
     * Value which represents rate of fire.
     */
    private static final float MINIMUM_TIME_BETWEEN_SHOTS = 0.5f;

    /**
     * Amount of time which has passed since the last shot.
     */
    private float timeSinceLastShot = 0f;

    /**
     * Texture to hold the Sprite map of the animated shot.
     */
    private final Texture shotTexture;

    /**
     * List of currently active shots / projectiles from the ship.
     */
    private List<AnimatedSprite> shots = new ArrayList<AnimatedSprite>();

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

            // Reset time since last shot.
            timeSinceLastShot = 0f;
        }
    }

    /**
     * Loop through List of shots, and draw each.
     * @param batch reference to the game's SpriteBatch instance.
     */
    public void draw(SpriteBatch batch)
    {
        for (AnimatedSprite shot : shots) {
            shot.draw(batch);
        }
    }

    /**
     * Moves each Sprite (shot) in the list.
     */
    public void update()
    {
        for (AnimatedSprite shot : shots) {
            shot.move();
        }

        timeSinceLastShot += Gdx.graphics.getDeltaTime();
    }

    /**
     * Determines if Sprite is able to fire a shot or not, based on throttling criteria.
     * @return Boolean
     */
    private boolean canFireShot()
    {
        return timeSinceLastShot > MINIMUM_TIME_BETWEEN_SHOTS;
    }
}

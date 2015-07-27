package com.stratigos.nullapointershooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class to handle shooting of projectiles from a Sprite. Handles number of shots fired, range, animation, etc.
 * Created by stratigos on 7/25/15 (via Pluralsight Android Game App Dev course).
 */
public class ShotManager
{
    /**
     * Position of center of Y-axis from which the Sprite's weapons begin to appear. Used to determine starting location
     *  of projectile animation.
     */
    private static final int SHOT_Y_OFFSET = 110;

    /**
     * Position of center of Y-axis for the alien Sprite.
     */
    public static final float ENEMY_SHOT_Y_OFFSET = 400;

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
     * Texture to hold the Sprite map of the Enemy animated shot.
     */
    private final Texture enemyShotTexture;

    /**
     * List of currently active shots / projectiles from the ship.
     */
    private List<AnimatedSprite> shots = new ArrayList<AnimatedSprite>();

    /**
     * List of currently active enemy shots.
     */
    private List<AnimatedSprite> enemyShots = new ArrayList<AnimatedSprite>();

    /**
     * Audio effect for firing a shot.
     */
    private Sound laser = Gdx.audio.newSound(Gdx.files.internal("shoot.wav"));

    /**
     * Audnio effect for Enemy firing a shot,
     */
    private Sound plasma = Gdx.audio.newSound(Gdx.files.internal("alienshot.mp3"));

    public ShotManager(Texture shotTexture, Texture enemyShotTexture)
    {
        this.shotTexture      = shotTexture;
        this.enemyShotTexture = enemyShotTexture;
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
        for (AnimatedSprite shot : enemyShots) {
            shot.draw(batch);
        }
    }

    /**
     * Moves each Sprite (shot) in the list. If shots move beyond the screen height, they are removed from the list
     *  of active shots.
     */
    public void update()
    {
        Iterator<AnimatedSprite> i = shots.iterator();
        Iterator<AnimatedSprite> j = enemyShots.iterator();

        // Animate player Sprite shots.
        while (i.hasNext()) {
            AnimatedSprite shot = i.next();
            shot.move();

            // Remove shot if it went off the screen.
            if (shot.getY() > ShooterGame.SCREEN_HEIGHT) {
                i.remove();
            }
        }

        // Animate Enemy Sprite shots.
        while (j.hasNext()) {
            AnimatedSprite enemyShot = j.next();
            enemyShot.move();

            // Remove shot if it went off the screen (0 is the bottom).
            if (enemyShot.getY() < 0) {
                j.remove();
            }
        }

        timeSinceLastShot += Gdx.graphics.getDeltaTime();
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

            // Play audio for shot.
            laser.play();
        }
    }

    /**
     * Handler for the AI to fire projectiles. Assumes Enemy class handles logic for deciding if shot is appropriate.
     * @param alienCenterXLocation Location of center of AI Sprite.
     */
    public void fireEnemyShot(int alienCenterXLocation)
    {
        Sprite newShot                 = new Sprite(enemyShotTexture);
        AnimatedSprite newShotAnimated = new AnimatedSprite(newShot);

        newShotAnimated.setPosition(alienCenterXLocation, ENEMY_SHOT_Y_OFFSET);
        newShotAnimated.setVelocity(new Vector2(0, -SHOT_SPEED)); // Shoot opposite direction from player.

        // Add new shot to list of current shots.
        enemyShots.add(newShotAnimated);

        // Play audio for enemy shot.
        plasma.play();
    }

    /**
     * Check if any player shots are within a bounding box / Rectangle.
     * @param boundingBox The area in which to detect a collision. Typically the bounding box of the Enemy ship.
     * @return TRUE if player shot is within Rectangle.
     */
    public boolean playerShotTouches(Rectangle boundingBox)
    {
        Iterator<AnimatedSprite> i = shots.iterator();
        Rectangle intersection     = new Rectangle(0, 0, 0, 0);

        // Check all player shots to see if they collide with the Rectangle.
        while (i.hasNext()) {
            AnimatedSprite shot = i.next();
            if (Intersector.intersectRectangles(shot.getBoundingBox(), boundingBox, intersection)) {
                i.remove();
                return true;
            }
        }

        return false;
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

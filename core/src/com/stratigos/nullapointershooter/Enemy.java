package com.stratigos.nullapointershooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Alien spaceship Sprite class.
 * Created by stratigos on 7/26/15 via Pluralsight "Building your First Game for Android..." course.
 */
public class Enemy
{
    /**
     * Velocity of alien spaceship on screen.
     */
    public static final float ENEMY_SPEED = 250;

    /**
     * Texture to hold spritemap for the alien ship.
     */
    private final Texture enemyTexture;

    /**
     * Main game loop's ShotManager instance.
     */
    private final ShotManager shotManager;

    /**
     * Sprite of the alien ship.
     */
    private AnimatedSprite animatedSprite;

    public Enemy(Texture enemyTexture, ShotManager shotManager)
    {
        this.enemyTexture = enemyTexture;
        this.shotManager  = shotManager;

        spawn();
    }

    /**
     * Draw the alien Sprite on the screen.
     * @param batch Main game loop SpriteBatch
     */
    public void draw(SpriteBatch batch)
    {
        animatedSprite.draw(batch);
    }

    /**
     * Move the alien ship, and fire a shot.
     */
    public void update()
    {
        if (shouldChangeDirection()) {
            animatedSprite.changeDirection();
        }
        if (shouldShoot()) {
            shotManager.fireEnemyShot(animatedSprite.getX());
        }
        animatedSprite.move();
    }

    /**
     * Instance a Sprite and set its position and velocity on the screen.
     */
    private void spawn()
    {
        Sprite enemySprite = new Sprite(enemyTexture);
        animatedSprite     = new AnimatedSprite(enemySprite);
        int xPosition      = createRandomPosition();

        animatedSprite.setPosition(xPosition, (ShooterGame.SCREEN_HEIGHT - animatedSprite.getHeight()));
        animatedSprite.setVelocity(new Vector2(ENEMY_SPEED, 0));
    }

    /**
     * Returns an X-axis pixel suitable for a starting position for a ship to spawn.
     * @return starting point for the X-axis.
     */
    private int createRandomPosition()
    {
        // Create random number between half of the width of the enemy ship, and the screen width less half the width of
        //  the enemy ship. The half-ship-width border prevents half the ship from being drawn off the screen.
        Random random    = new Random();
        int randomNumber = random.nextInt((ShooterGame.SCREEN_WIDTH - animatedSprite.getWidth()) + 1);

        return randomNumber + (animatedSprite.getWidth() / 2);
    }

    /**
     * Determines if alien ship should change direction from left to right, or right to left.
     * @return TRUE if direction should change.
     */
    private boolean shouldChangeDirection()
    {
        Random random = new Random();
        return random.nextInt(41) == 0;
    }

    /**
     *
     * @return TRUE if weapon should be shot.
     */
    private boolean shouldShoot()
    {
        Random random = new Random();
        return random.nextInt(61) == 0;
    }
}

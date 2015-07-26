package com.stratigos.nullapointershooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Alien spaceship Sprite class.
 * Created by stratigos on 7/26/15 via Pluralsight "Building your First Game for Android..." course.
 */
public class Enemy
{
    /**
     * Texture to hold spritemap for the alien ship.
     */
    private final Texture enemyTexture;

    private AnimatedSprite animatedSprite;

    public Enemy(Texture enemyTexture)
    {
        this.enemyTexture = enemyTexture;

        spawn();
    }

    /**
     * Draw the alien Sprite on the screen.
     * @param batch Main game loop SpriteBatch
     */
    public void draw(SpriteBatch batch) {
        animatedSprite.draw(batch);
    }

    private void spawn()
    {
        Sprite enemySprite = new Sprite(enemyTexture);
        animatedSprite     = new AnimatedSprite(enemySprite);
        int xPosition      = createRandomPosition();

        animatedSprite.setPosition(xPosition, (ShooterGame.SCREEN_HEIGHT - animatedSprite.getHeight()));
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
}

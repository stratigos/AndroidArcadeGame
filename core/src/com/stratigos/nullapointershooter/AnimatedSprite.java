package com.stratigos.nullapointershooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Class to handle drawing / animating sprites.
 * Created by stratigos on 7/20/15 (via Pluralsight Android Game App Dev course).
 */
public class AnimatedSprite
{
    /**
     * Rate of movement, in pixels, the Sprite moves per game tick.
     */
    public static final int SHIP_SPEED = 300;

    /**
     * Stored value to represent number of horizontal animation tiles in a Sprite map.
     */
    private static final int FRAMES_COL = 2;

    /**
     * Stored value to represent number of vertical animation tiles in a Sprite map.
     */
    private static final int FRAMES_ROW = 2;

    /**
     * Represents the current Sprite being animated.
     */
    private Sprite sprite;

    /**
     * Helper for animation, deduces frame, time elapsed, fps, etc.
     */
    private Animation animation;

    /**
     * Array of frames.
     */
    private TextureRegion[] frames;

    /**
     * Current frame.
     */
    private TextureRegion currentFrame;

    /**
     * Holds time passed since last image update. Helps track frame to be displayed.
     */
    private float stateTime;

    /**
     * A variable to hold the value of the position of an object. Initializes at 0x,0y.
     */
    private Vector2 velocity = new Vector2();

    /**
     * @param sprite Image/map to be animated.
     */
    public AnimatedSprite(Sprite sprite)
    {
        // Create resources for Sprite mapping.
        this.sprite            = sprite;
        Texture texture        = sprite.getTexture();
        TextureRegion[][] temp = TextureRegion.split(
            texture,
            (int) getSpriteWidth(),
            texture.getHeight() / FRAMES_ROW
        );
        frames = new TextureRegion[FRAMES_COL * FRAMES_ROW];

        // Create "table" to store animated frames.
        int index = 0;
        for (int i = 0; i < FRAMES_ROW; i++) {
           for (int j = 0; j < FRAMES_COL; j++) {
               frames[index++] = temp[i][j];
           }
        }

        animation = new Animation(0.1f, frames);
        stateTime = 0f;
    }

    /**
     * Draw current frame at Sprite's X and Y coordinates.
     * @param spriteBatch SpriteBatch
     */
    public void draw(SpriteBatch spriteBatch)
    {
        stateTime   += Gdx.graphics.getDeltaTime(); // Get time passed since last draw() call.
        currentFrame = animation.getKeyFrame(stateTime, true);

        spriteBatch.draw(currentFrame, sprite.getX(), sprite.getY());
    }

    /**
     * Handles position of the current frame on the screen.
     * @param x float Center of the Sprite
     * @param y float Bottom of Sprite
     */
    public void setPosition(float x, float y)
    {
        sprite.setPosition((x - getSpriteCenterOffset()), y);
    }

    /**
     * Update the velocity of this Sprite.
     * @param velocity Vector2 instance used to manipulate Sprite's velocity on screen.
     */
    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    /**
     * Controls the right-directional velocity of the Sprite. Moves at 300px / frame,
     */
    public void moveRight()
    {
        velocity = new Vector2(SHIP_SPEED, 0);
    }

    /**
     * Controls the left-directional velocity of the Sprite. Moves at 300px / frame,
     */
    public void moveLeft()
    {
        velocity = new Vector2(-SHIP_SPEED, 0);
    }

    /**
     * Moves the Sprite to the left or right, or up, based on current velocity. Prevents Sprite from moving off the
     *  screen for X axis movement.
     */
    public void move()
    {
        // Determine how many pixels the Sprite should move based on time passed.
        int xMovement = (int) (velocity.x * Gdx.graphics.getDeltaTime());
        int yMovement = (int) (velocity.y * Gdx.graphics.getDeltaTime());

        // Set new position of Sprite based on changed velocity.
        sprite.setPosition(sprite.getX() + xMovement, sprite.getY() + yMovement);

        // Prevent Sprite from moving off the screen along the X axis.
        if (sprite.getX() < 0) {
            sprite.setX(0);
        }
        if ((sprite.getX() + getSpriteWidth()) > ShooterGame.SCREEN_WIDTH) {
            sprite.setX(ShooterGame.SCREEN_WIDTH - getSpriteWidth());
        }
    }

    /**
     * Invert the Sprite's velocity (swap left or right movement).
     */
    public void changeDirection()
    {
        velocity.x = -velocity.x;
    }

    /**
     * Get the X position of the center of the Sprite.
     * @return Pixel x-position of center of the Sprite.
     */
    public int getX()
    {
        return (int) (sprite.getX() + getSpriteCenterOffset());
    }

    /**
     * Get the Y position of the Sprite.
     * @return Pixel y-position of the Sprite.
     */
    public int getY()
    {
        return (int) (sprite.getY());
    }

    /**
     * Get the width of an individual frame of the animated Sprite, cast as an integer.
     * @return width
     */
    public int getWidth()
    {
        return (int) (getSpriteWidth());
    }

    /**
     * Get the height of an individual frame of the animated Sprite, cast as an integer.
     * @return height
     */
    public int getHeight()
    {
        return (int) (sprite.getHeight() / FRAMES_ROW);
    }

    /**
     * Find the distance between the edge of the Sprite and it's center.
     * @return Distance in pixels from Sprite center pixel.
     */
    private float getSpriteCenterOffset()
    {
        return getSpriteWidth() / 2;
    }

    /**
     * Finds the width of an individual frame of the spritemap.
     * @return Width of the Sprite.
     */
    private float getSpriteWidth()
    {
        return sprite.getWidth() / FRAMES_COL;
    }
}

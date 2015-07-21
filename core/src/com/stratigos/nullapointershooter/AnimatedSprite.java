package com.stratigos.nullapointershooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.PrimitiveIterator;

/**
 * Class to handle drawing / animating Sprites.
 * Created by stratigos on 7/20/15 (via Pluralsight Android Game App Dev course).
 */
public class AnimatedSprite {
    /**
     * Stored value to represent number of horizontal animation tiles in a sprite map.
     */
    private static final int FRAMES_COL = 2;

    /**
     * Stored value to represent number of vertical animation tiles in a sprite map.
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
     * @param sprite Image/map to be animated.
     */
    public AnimatedSprite(Sprite sprite)
    {
        // Create resources for Sprite mapping.
        this.sprite            = sprite;
        Texture texture        = sprite.getTexture();
        TextureRegion[][] temp = TextureRegion.split(
            texture,
            texture.getWidth() / FRAMES_COL,
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
        float widthOffset = sprite.getWidth() / FRAMES_COL;
        sprite.setPosition((x - widthOffset / 2), y);
    }
}

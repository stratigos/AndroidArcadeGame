package com.stratigos.nullapointershooter;

import com.badlogic.gdx.graphics.Texture;

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

    public Enemy(Texture enemyTexture)
    {
        this.enemyTexture = enemyTexture;
    }
}

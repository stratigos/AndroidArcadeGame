package com.stratigos.nullapointershooter;

/**
 * Class to handle collision detection.
 * Created by stratigos on 7/26/15 via Pluralsight "Building Your First Game for Android..." course.
 */
public class CollisionManager
{
    /**
     * Reference to main game loop's player Sprite.
     */
    private final AnimatedSprite spaceshipAnimated;

    /**
     * Reference to main game loop's alien Sprite.
     */
    private final Enemy enemy;

    /**
     * Reference to shots fired.
     */
    private final ShotManager shotManager;

    public CollisionManager(AnimatedSprite spaceshipAnimated, Enemy enemy, ShotManager shotManager)
    {
        this.spaceshipAnimated = spaceshipAnimated;
        this.enemy             = enemy;
        this.shotManager       = shotManager;
    }
}

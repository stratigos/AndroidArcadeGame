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

    /**
     * Check to see if player or alien shot each other.
     */
    public void handleCollisions()
    {
        handleEnemyWasShot();
        handlePlayerWasShot();
    }

    /**
     * Check if player shot alien, and if so, keel 'um.
     */
    private void handleEnemyWasShot()
    {
        // Check shotManager to see if any boundaries are crossing.
        if (shotManager.playerShotTouches(enemy.getBoundingBox())) {
            enemy.hit();
        }
    }

    /**
     * Check if alien shot player, and if so, get 'em outta here!
     */
    private void handlePlayerWasShot()
    {
        // Check shotManager to see if any boundaries are crossing.
        if (shotManager.enemyShotTouches(spaceshipAnimated.getBoundingBox())) {
            spaceshipAnimated.setDead(true);
        }
    }
}

package com.stratigos.nullapointershooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ShooterGame extends ApplicationAdapter
{
    /**
     * Stores values which represent dimensions of the screen / viewport.
     */
    public static final int SCREEN_WIDTH  = 800;
    public static final int SCREEN_HEIGHT = 480;

    /**
     * A camera (viewpoint / perspective?).
     */
    private OrthographicCamera camera;

    /**
     * Used to draw sprites.
     */
    SpriteBatch batch;

    /**
     * A property to hold the background image of outer space.
     *  Expects assignment of an image which is 800w x 480h, and kind of looks like outer space.
     */
    private Texture background;

    /**
     * A property to hold the spaceship image.
     *  Expects assignment of an image which is 120w x 120h, and looks like a badass space fighter ship.
     */
    private Sprite spaceshipSprite;

    /**
     * Sprite for spaceship animations. Expects assignment of animation frames image which is 240w x 240h, and looks
     *  like a badass space fighter ship.
     */
    private AnimatedSprite spaceshipAnimated;

    /**
     * Sprite for alien spaceships.
     */
    private Enemy enemy;

    /**
     * Object which handles the shooting feature of the spaceship.
     */
    private ShotManager shotManager;

    /**
     * Object which handles game background music.
     */
    private Music gameMusic;

    @Override
    public void create ()
    {
        // The following function "setEnforcePotImages(false);" is no longer available, as the corresponding tutorial
        //  for this program is very out of date. See the following SO link:
        //  http://stackoverflow.com/questions/29398237/texture-setenforcepotimagesfalse-cannot-resolve-mothed-error-under-android-st
        //  This originally was meant to allow non "power of two" images to be drawn (which is a req for old games).
        // Texture.setEnforcePotImages(false);

        // Initialize viewpoint and resolution.
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT); // Sets resolution.

        // Initialise SpriteBatch.
        batch = new SpriteBatch();

        // Create background.
        background = new Texture(Gdx.files.internal("spacebackground.png"));

        // Create Texture to display shots, and ShotManager instance to track/animate shooting.
        Texture shotTexture      = new Texture(Gdx.files.internal("shotspritemap.png"));
        Texture enemyShotTexture = new Texture(Gdx.files.internal("alienshotspritemap.png"));
        shotManager 		     = new ShotManager(shotTexture, enemyShotTexture);

        // Create the spaceship.
        Texture spaceshipTexture = new Texture(Gdx.files.internal("spaceshipspritemap.png"));
        spaceshipSprite          = new Sprite(spaceshipTexture);

        // Create animated spaceship.
        spaceshipAnimated = new AnimatedSprite(spaceshipSprite);
        spaceshipAnimated.setPosition((SCREEN_WIDTH / 2), 0);

        // Create alien spaceship.
        Texture enemyTexture = new Texture(Gdx.files.internal("alienshipspritemap.png"));
        enemy                = new Enemy(enemyTexture, shotManager);

        // Play groovy game music.
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("ambient.flac"));
        gameMusic.setVolume(0.25f); // Low volume.
        gameMusic.setLooping(true);
        gameMusic.play();
    }

    @Override
    public void dispose()
    {
        batch.dispose();
    }

    @Override
    public void render ()
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set projection.
        batch.setProjectionMatrix(camera.combined);

        // Start drawing screen.
        batch.begin();

        // Draw background.
        batch.draw(background, 0, 0);

        // Draw spaceship Sprite.
        spaceshipAnimated.draw(batch);

		// Draw the alien spaceship.
		enemy.draw(batch);

        // Draw the spaceship's projectiles / shots.
        shotManager.draw(batch);

        // End drawing screen.
        batch.end();

        // Check for touch input.
        handleInput();

        // Animate spaceship movement.
        spaceshipAnimated.move();

        // Animate alien spaceship movement.
        enemy.update();

        // Animate projectiles / shots movement.
        shotManager.update();
    }

    /**
     * Check if screen is touched, and if ship should move to the left or right. Also fires ship's weapons on touch.
     * TODO Store shaceshipAnimated.getX() in a local var, and dont call it 2x.
     */
    private void handleInput()
    {
        if (Gdx.input.isTouched()) {
            // Using a 3D vector (though ignoring the Z parameter) to store touch coordinates.
            Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            // Storing touch position. This alleviates issue where ship starts moving in opposite direction if touched
            //  too many times in the same direction, by setting the touch coordinates to be the same as the screen
            //  drawing coordinates.
            camera.unproject(touchPosition);

            // If the position of the X touch input is beyond the spaceship's X position, then the ship should move
            //  to the right. Otherwise, it should move to the left.
            if (touchPosition.x > spaceshipAnimated.getX()) {
                spaceshipAnimated.moveRight();
            } else {
                spaceshipAnimated.moveLeft();
            }

            // Handle firing weapons.
            shotManager.firePlayerShot(spaceshipAnimated.getX());
        }
    }
}

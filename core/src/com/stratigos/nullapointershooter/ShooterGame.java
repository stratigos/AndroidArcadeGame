package com.stratigos.nullapointershooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShooterGame extends ApplicationAdapter
{
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
		camera.setToOrtho(false, 800, 480); // Sets resolution.

		// Initialise SpriteBatch.
		batch = new SpriteBatch();

		// Create background.
		background = new Texture(Gdx.files.internal("spacebackground.png"));

		// Create the spaceship.
		Texture spaceshipTexture = new Texture(Gdx.files.internal("spaceshipspritemap.png"));
		spaceshipSprite          = new Sprite(spaceshipTexture);

        // Create animated spaceship.
        spaceshipAnimated = new AnimatedSprite(spaceshipSprite);
        spaceshipAnimated.setPosition((800 / 2), 0);
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

		// End drawing screen.
		batch.end();

		// Check for touch input.
		handleInput();

		spaceshipAnimated.move();
	}

	/**
	 * Check if screen is touched, and if ship should move to the left or right.
	 */
	private void handleInput() {
		if (Gdx.input.isTouched()) {
			// The ship only moves left or right, not up or down, and has a predefined velocity, so its only necessary
			//  to know if it was the X input was manipulated.
			int xTouch = Gdx.input.getX();

			// If the position of the X touch input is beyond the spaceship's X position, then the ship should move
			//  to the right. Otherwise, it should move to the left.
			if (xTouch > spaceshipAnimated.getX()) {
				spaceshipAnimated.moveRight();
			} else {
				spaceshipAnimated.moveLeft();
			}
		}
	}
}

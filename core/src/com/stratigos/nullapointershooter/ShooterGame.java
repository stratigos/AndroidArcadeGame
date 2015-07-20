package com.stratigos.nullapointershooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShooterGame extends ApplicationAdapter {
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
	
	@Override
	public void create () {
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
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Set projection.
		batch.setProjectionMatrix(camera.combined);

		// Start drawing screen.
		batch.begin();

		batch.draw(background, 0, 0);

		// End drawing screen.
		batch.end();
	}
}

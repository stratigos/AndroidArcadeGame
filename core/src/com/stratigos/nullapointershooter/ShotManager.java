package com.stratigos.nullapointershooter;

import com.badlogic.gdx.graphics.Texture;

/**
 * Class to handle shooting of projectiles from a Sprite. Handles number of shots fired, range, animation, etc.
 * Created by stratigos on 7/25/15 (via Pluralsight Android Game App Dev course).
 */
public class ShotManager {

    /**
     * Texture to hold the Sprite map of the animated shot.
     */
    private final Texture shotTexture;

    public ShotManager(Texture shotTexture) {
        this.shotTexture = shotTexture;
    }
}

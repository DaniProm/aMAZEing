package com.csanydroid.game;

import com.badlogic.gdx.physics.box2d.Shape;

public class WallActor extends MyActor {

    private int strength = Integer.MAX_VALUE; // a fal erőssége

    @Override
    protected void createSprite() {

    }

    @Override
    protected Shape getShape() {
        return null;
    }

    @Override
    public void dispose() {

    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

}

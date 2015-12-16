package com.csanydroid.game;

import com.badlogic.gdx.physics.box2d.Shape;

public class HoleActor extends MyActor {

    /*
    * null: black hole
    * this: normal hole
    * other: behave as a pipe
    * */
    private HoleActor endpoint = this;

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

    public void setEndpoint(HoleActor endpoint) {
        this.endpoint = endpoint;
    }
}

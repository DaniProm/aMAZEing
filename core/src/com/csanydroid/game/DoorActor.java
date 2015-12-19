package com.csanydroid.game;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class DoorActor extends GameActor {

    @Override
    protected Shape getShape() {

	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(GameScreen.BASE_SIZE, GameScreen.BASE_SIZE);

	    return shape;


    }

    @Override
    public void dispose() {

    }

}

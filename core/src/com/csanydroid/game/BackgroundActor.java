package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by tanulo on 2016.01.13..
 */
public class BackgroundActor extends GameActor {
    private static Texture texture = new Texture("background.png");
    public BackgroundActor() {
        sprite = new Sprite(texture);
    }

    @Override
    public void act(float delta) {

    }
}

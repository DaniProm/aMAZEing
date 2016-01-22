package com.csanydroid.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by tuskeb on 2016.01.22..
 */
public class MenuBackgroundActor extends Actor {
    Sprite background, mole, logo;
    float elapsedTime = 0.0f;
    public MenuBackgroundActor() {
        background = new Sprite(Assets.manager.get(Assets.MENU_BACKGROUND));
        mole = new Sprite(Assets.manager.get(Assets.MENU_MOLE));
        logo = new Sprite(Assets.manager.get(Assets.MENU_LOGO));

    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        background.setSize(width, height);
        mole.setSize(width , height * mole.getTexture().getHeight() / background.getTexture().getHeight());
        logo.setSize(width * logo.getTexture().getWidth()/background.getTexture().getWidth(),height * logo.getTexture().getHeight()/background.getTexture().getHeight());
        logo.setPosition(getWidth() / 2 - logo.getWidth() / 2, getHeight() / 2);
        logo.setPosition(getWidth()/2-logo.getWidth()/2, getHeight()/2);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        background.draw(batch);
        mole.draw(batch);
        logo.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
        logo.setRotation((float)Math.sin((double)elapsedTime)*5);

    }
}

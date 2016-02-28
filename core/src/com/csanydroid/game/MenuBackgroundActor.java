package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by tuskeb on 2016.01.22..
 */
public class MenuBackgroundActor extends Actor {
    Sprite background, mole, logo, csany_logo, pd_logo;
    float elapsedTime = 0.0f;
    public MenuBackgroundActor() {
        background = new Sprite(Assets.manager.get(Assets.MENU_BACKGROUND));
        mole = new Sprite(Assets.manager.get(Assets.MENU_MOLE));
        logo = new Sprite(Assets.manager.get(Assets.MENU_LOGO));
        csany_logo = new Sprite(Assets.manager.get(Assets.CSANY_LOGO));
        pd_logo = new Sprite(Assets.manager.get(Assets.PenDroid_LOGO));

    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        //float aspect = Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        //Gdx.app.log("asp",aspect + "");
        background.setSize(width, height);
        mole.setSize(width, mole.getTexture().getHeight() * width / mole.getWidth());
        //mole.setScale(width / mole.getWidth());

        logo.setSize(width * 0.6f, logo.getTexture().getHeight() * width * 0.6f / logo.getWidth());
        logo.setPosition(getWidth() / 2 - logo.getWidth() / 2, getHeight() - logo.getHeight() - getHeight() / 8);
        logo.setOrigin(getWidth() / 2, getHeight() / 2);

        csany_logo.setSize(100, 100);
        csany_logo.setPosition(MenuScreen.getButtonPosX, Gdx.input.getY());//MenuScreen.getButtonPosY-MenuScreen.getButtonHeight <-- Y

        pd_logo.setSize(100, 100);
        pd_logo.setPosition(MenuScreen.getButtonPosX+csany_logo.getWidth(), Gdx.input.getY()); //MenuScreen.getButtonPosY <-- Y
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
        csany_logo.draw(batch);
        pd_logo.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
        logo.setRotation((float)Math.sin((double)elapsedTime)*5);
        //logo.setScale(logo.getScaleX()*0.99f);
    }
}

package com.csanydroid.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by tanarur on 2016.01.17..
 */
public class NextLevelWindow extends MyWindow {
    public NextLevelWindow() {
        super();
        setTitle("Katt az ablakra!");
        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
}

package com.csanydroid.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyButton extends TextButton {

    public MyButton(String text, Skin skin) {
        super(text, skin);
        applyClickSound();
    }

    public MyButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        applyClickSound();
    }

    public MyButton(String text, TextButtonStyle style) {
        super(text, style);
        applyClickSound();
    }

    private void applyClickSound() {
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.manager.get(Assets.CLICK_SOUND).play();
            }
        });

    }

}

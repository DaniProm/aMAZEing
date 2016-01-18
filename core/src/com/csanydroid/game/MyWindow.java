package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Created by tanarur on 2016.01.17..
 */
abstract public class MyWindow extends Window {
    private static WindowStyle windowStyle = new WindowStyle();
    protected final static Label.LabelStyle labelStyle = new Label.LabelStyle();
    private final static Label.LabelStyle titleStyle = new Label.LabelStyle();
    protected final static TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    private static String CHARS = "0123456789öüóqwertzuiopőúasdfghjkléáűíyxcvbnm'+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_*<>#&@{}[],-.";
    private static BitmapFont bitmapFont;
    private Label titleLabel;

    static
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/sitka.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.shadowOffsetX = 1;
        parameter.shadowOffsetY = 1;
        parameter.shadowColor = Color.valueOf("237700");
        parameter.characters = CHARS;
        bitmapFont = generator.generateFont(parameter);
        bitmapFont.setColor(0, 0, 0, 1f);
        generator.dispose();


        windowStyle.titleFont = bitmapFont;
        Sprite sprite = new Sprite(Assets.manager.get(Assets.WINDOW));
        sprite.setSize(200, 100);
        windowStyle.background = new SpriteDrawable(sprite);
        windowStyle.titleFont = bitmapFont;
        windowStyle.titleFontColor = Color.WHITE;
        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.drawPixel(0,0,Color.rgba8888(0,0,0,0.5f));
        windowStyle.stageBackground = new SpriteDrawable(new Sprite(new Texture(pixmap)));

        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.valueOf("43a708");

        titleStyle.font = bitmapFont;
        titleStyle.fontColor = Color.WHITE;

        textButtonStyle.font = bitmapFont;
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.over = new SpriteDrawable(new Sprite(Assets.manager.get(Assets.BUTTON_ATLAS).getRegions().get(0)));
        textButtonStyle.down = new SpriteDrawable(new Sprite(Assets.manager.get(Assets.BUTTON_ATLAS).getRegions().get(1)));
        textButtonStyle.up = new SpriteDrawable(new Sprite(Assets.manager.get(Assets.BUTTON_ATLAS).getRegions().get(2)));


    }

    public MyWindow() {
        super("", windowStyle);
        setResizable(false);
        setMovable(false);
        setFillParent(false);
        setBounds(204, 153, 612, 459);
        titleLabel = new Label("Hello world",titleStyle);
        titleLabel.setPosition(5, 405);
        titleLabel.setWidth(getWidth());
        titleLabel.setAlignment(1, 1);
        addActor(titleLabel);
        //getTitleLabel().setPosition(5,20);



        AlphaAction action = new AlphaAction();
        action.setDuration(1);
        action.setReverse(true);
        addAction(action);
    }

    public void setTitle(String title)
    {
        titleLabel.setText(title);
    }
/*
    @Override
    public Dialog show(Stage stage) {
        AlphaAction action = new AlphaAction();
        action.setDuration(1);
        action.setReverse(true);
        return super.show(stage, action);
        //return super.show(stage);
    }

    @Override
    public Dialog show(Stage stage, Action action) {
        return super.show(stage, action);
    }*/
}


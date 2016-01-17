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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Created by tanarur on 2016.01.17..
 */
abstract public class MyWindow extends Window {
    private static WindowStyle windowStyle = new WindowStyle();
    private static Label.LabelStyle labelStyle = new Label.LabelStyle();
    private static Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
    private static String CHARS = "0123456789öüóqwertzuiopőúasdfghjkléáűíyxcvbnm'+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_*<>#&@{}[],-.";
    private static BitmapFont bitmapFont;
    private Label titleLabel;

    static
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/sitka.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = Color.BLACK;
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
    }

    public MyWindow() {
        super("", windowStyle);
        setResizable(false);
        setMovable(false);
        setFillParent(false);
        setBounds(204, 153, 612, 459);
        titleLabel = new Label("Hello world",labelStyle);
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


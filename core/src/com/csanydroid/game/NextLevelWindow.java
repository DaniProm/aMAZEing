package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class NextLevelWindow extends MyWindow {
    Maze maze;
    public NextLevelWindow(byte collectedStars, byte totalStars) {
        super();

            setTitle("Gratulálok!");
            Label label = new Label("Sikeresen teljesítetetted a pályát, "+collectedStars+" db csillagot gyűjtöttél össze a(z)"+totalStars+" db-ból! \nÜgyes vagy! :)", labelStyle);
            label.setWrap(true);
            label.setAlignment(Align.topLeft, Align.bottomLeft);
            label.setPosition(10, 350);
            label.setWidth(getWidth() - 20);
            label.setFontScale(0.6f);
            addActor(label);


        final MazeActor mazeActor = new MazeActor(Maze.findMaze("1"));
        mazeActor.setSize(180, 180);
        mazeActor.setPosition(getWidth() / 2 - 95, 20);

        TextButton textButtonNext = new TextButton("Újra", textButtonStyle);
        textButtonNext.setSize(170, 60);
        textButtonNext.setPosition(20, 80);
        textButtonNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                maze.getNextMaze().beginPlay();
            }
        });

        TextButton textButtonRepeat = new TextButton("Tovább", textButtonStyle);
        textButtonRepeat.setSize(170, 60);
        textButtonRepeat.setPosition(getWidth() - 200, 80);
        textButtonRepeat.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                maze.beginPlay();
            }
        });
        TextButton textButtonSelector = new TextButton("Pályák", textButtonStyle);
        textButtonNext.setSize(170, 60);
        textButtonNext.setPosition(20, 80);
        textButtonNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((AmazingGame) Gdx.app.getApplicationListener()).setScreen(new MazeSelectorScreen());
            }
        });

        addActor(mazeActor);
        addActor(textButtonNext);
        addActor(textButtonRepeat);
        addActor(textButtonSelector);

        /*addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });*/
    }
}


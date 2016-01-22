package com.csanydroid.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class MazeSelectorScreen extends MyScreen implements ApplicationListener{
    SpriteBatch batch = new SpriteBatch();
    Stage stage;

    MazeSelectorScreen(){
        super();
         setBackgroundColor(0f,0.3f,0f);
        stage = new Stage() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                    case Input.Keys.BACK:
                        ((Game) Gdx.app.getApplicationListener())
                                .setScreen(new MenuScreen());
                        break;
                }
                return false;
            }

        };

        stage.setDebugAll(true);
        Table table = new Table();

       for (final Maze maze : Maze.getMazes()) {

            MazeActor mazeActor = new MazeActor(maze);

           mazeActor.addListener(new ClickListener() {
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   try {
                       maze.beginPlay();
                   } catch (Exception e) { }
               }
           });

            mazeActor.setSize(256, 256);

           table.add(mazeActor).pad(12);

            table.row();

        }

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFillParent(true);
        scrollPane.layout();

        stage.addActor(scrollPane);

	    // gombok hozzáadása


	    TextButton button = new MyButton("Vissza", TEXT_BUTTON_STYLE);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
	    });
	    button.setPosition(0, 0);

	    stage.addActor(button);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);

        stage.act();
        stage.draw();

        batch.end();

    }

    @Override
    public void create() {}

    @Override
    public void render() {}


    private Music music = Assets.manager.get(Assets.MAZESELECTING_MUSIC);

    @Override
    public void hide() {
        super.hide();

        music.pause();
    }

    @Override
    public void show() {
        super.show();
        music.play();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}


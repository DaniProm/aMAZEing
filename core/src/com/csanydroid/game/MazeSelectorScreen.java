package com.csanydroid.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    TextButton button;
    MazeSelectorScreen(){
        super();
        stage = new Stage() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                    case Input.Keys.BACK:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                        break;
                }
                return false;
            }

        };



        System.out.println("Log: 'Tábla' létrehozva.");

        TextButton button;
       /* Label label = new Label("Pályák", LABEL_STYLE);
        label.setAlignment(Align.center, Align.center);
        stage.add...
                .width(500f)
                .height(130f);*/
        System.out.println("Log: 'Label' létrehozva.");


        final float ROW_HEIGHT = 75f;

        // gombok hozzáadása


        button = new TextButton("Vissza", MyScreen.TEXT_BUTTON_STYLE);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Log: Klikk a 'Kilépés' gombra.");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });
        button.setPosition(0, 0);

        stage.addActor(button);

        /*table.add(mazeActor);
        stage.addActor(mazeActor);*/

        //table.add(mazeActor);
        //stage.addActor(mazeActor);

        Table table = new Table();
        table.setFillParent(true);
        for (final Maze maze : Maze.getMazes()) {

            MazeActor mazeActor = new MazeActor(maze);
            //mazeActor.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 4);
            //mazeActor.setPosition(0,0);
            mazeActor.setSize(128, 128);

            /*
            button = new TextButton(String.format("#%d pálya: ", maze.getMazeIndex() + 1, maze.getDescription()), MyScreen.TEXT_BUTTON_STYLE);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    maze.beginPlay();
                }
            });
            table.add(button);
            */
            table.add(mazeActor);
            table.row();

        }

        table.layout();
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFillParent(true);
        scrollPane.layout();

        stage.addActor(scrollPane);

        /*mazeActor = new MazeActor(false);
        mazeActor.setPosition(mazeActor.getX(), mazeActor.getY());
        stage.addActor(mazeActor);*/

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

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}


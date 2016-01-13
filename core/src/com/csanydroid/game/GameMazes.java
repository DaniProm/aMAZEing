package com.csanydroid.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class GameMazes extends MyScreen implements ApplicationListener{
    SpriteBatch batch = new SpriteBatch();
    Stage stage;
    MazeActor mazeActor;
    TextButton button;
    GameMazes(){
        super();
        stage = new Stage() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                    case Input.Keys.BACK:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameMenu());
                        break;
                }
                return false;
            }

        };

        Table table = new Table();
        table.setFillParent(true);

        System.out.println("Log: 'Tábla' létrehozva.");

        TextButton button;
        Label label = new Label("Pályák", LABEL_STYLE);
        label.setAlignment(Align.center, Align.center);
        table.add(label)
                .width(500f)
                .height(130f);
        System.out.println("Log: 'Label' létrehozva.");
        stage.addActor(table);

        // gombok hozzáadása

        final float ROW_HEIGHT = 75f;
        table.row().height(ROW_HEIGHT);
        /*table.add(mazeActor);
        stage.addActor(mazeActor);*/
        button = new TextButton("Vissza", MyScreen.TEXT_BUTTON_STYLE);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Log: Klikk a 'Kilépés' gombra.");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameMenu());
            }
        });
        button.setPosition(0, 0);
        table.row().height(ROW_HEIGHT);
        stage.addActor(button);

        mazeActor = new MazeActor(true);//szint képet fog csinálni
        mazeActor.setPosition(1024 / 4, 768 / 4);
        mazeActor.setSize(256, 256);
        //table.add(mazeActor);
        //stage.addActor(mazeActor);

        for (final Maze maze: Maze.getMazes()) {

            button = new TextButton(maze.getName(), MyScreen.TEXT_BUTTON_STYLE);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    maze.beginPlay();
                }
            });
            table.add(button);
            table.row();


        }
        /*mazeActor = new MazeActor(false);
        mazeActor.setPosition(mazeActor.getX(), mazeActor.getY());
        stage.addActor(mazeActor);*/

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        //stage.act(Gdx.graphics.getDeltaTime());
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


package com.csanydroid.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;


/** A játék menüje*/
public class GameMenu extends MyScreen{
    Stage stage;
    private final Sound s = Gdx.audio.newSound(Gdx.files.internal("teleport.mp3"));
    AmazingGame ag = new AmazingGame();
    public GameMenu(){
      super();
        stage = new Stage() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                    case Input.Keys.BACK:
                        System.exit(0);
                        break;
                }
                return false;
            }

        };
        System.out.println("Log: 'GameMenu' meghíva.");
        Table table = new Table();
        table.setFillParent(true);

        System.out.println("Log: 'Tábla' létrehozva.");

        TextButton button;
        Label label = new Label("aMAZEing", LABEL_STYLE); // Ha már magyar a kezelőfelület, akkor ez is lehetne magyar
        label.setAlignment(Align.center, Align.center);
        table.add(label)
                .width(500f)
                .height(130f);
        System.out.println("Log: 'Label' létrehozva.");

        // gombok hozzáadása

        final float ROW_HEIGHT = 75f;

        button = new TextButton("Első szint", MyScreen.TEXT_BUTTON_STYLE);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Maze.findMaze("1").beginPlay();
                System.out.println("Log: Klikk az '1.szint' gombra.");
            }
        });
        table.add(button);
        table.row().height(ROW_HEIGHT);
        System.out.println("Log: '1.szint' gomb létrehozva.");

        button = new TextButton("Második szint", MyScreen.TEXT_BUTTON_STYLE);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Maze.findMaze("2").beginPlay();
                System.out.println("Log: Klikk az '2.szint' gombra.");
            }
        });
        table.add(button);
        table.row().height(ROW_HEIGHT);
        System.out.println("Log: '2.szint' gomb létrehozva.");

        button = new TextButton("Teszt szint", MyScreen.TEXT_BUTTON_STYLE);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Maze.findMaze("test").beginPlay();
                System.out.println("Log: Klikk az 'Test szint' gombra.");
            }
        });
        table.add(button);
        table.row().height(ROW_HEIGHT);
        System.out.println("Log: '3.szint' gomb létrehozva.");

        button = new TextButton("Kilépés", MyScreen.TEXT_BUTTON_STYLE);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Log: Klikk a 'Kilépés' gombra.");
                System.exit(0);
            }
        });
        table.row().height(ROW_HEIGHT);
        table.add(button);
        System.out.println("Log: 'Kilépés' gomb létrehozva.");
        stage.addActor(table);
        System.out.println("Log: A 'table' hozzáadva a 'stage'hez");
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
    }


    @Override
    public void hide() {
        super.hide();
        s.pause();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        /*s.setVolume(1,0.5f);
        s.play();
        s.setLooping(1,true);*/
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
}

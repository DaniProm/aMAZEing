package com.csanydroid.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/** A játék menüje*/
public class MenuScreen extends MyScreen{
    Stage stage;
    private final Sound s = Gdx.audio.newSound(Gdx.files.internal("teleport.mp3"));

    GameActor actor;

	public MenuScreen() {
      super();
        setBackgroundColor(0.3f,0.8f,0.9f);

        stage = new Stage() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                    case Input.Keys.BACK:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MazeSelectorScreen());
                        break;
                }
                return false;
            }

        };

        System.out.println("Log: 'GameMdenu' meghíva.");
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

        button = new TextButton("Játék indítása", MyScreen.TEXT_BUTTON_STYLE);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MazeSelectorScreen());
                System.out.println("Log: Klikk a 'Játék indítása' gombra.");
            }
        });
        table.row();
        table.add(button).height(ROW_HEIGHT);
        System.out.println("Log: 'Játék indítása' gomb létrehozva.");

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

        actor = new BallActor();
        actor.setSize(128, 128);
        camera = new OrthographicCamera(1024,768);
        camera.translate(512,384);
        viewport = new ExtendViewport(1024, 768, camera);
        stage.setViewport(viewport);
        //stage.addActor(actor);
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
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
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
    }
}

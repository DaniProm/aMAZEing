package com.csanydroid.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class InterMazeWindow extends MyWindow {

   private Music music = Assets.manager.get(Assets.NEXTLEVEL_MUSIC);

    @Override
    public boolean remove() {
        try {
            music.pause();
        } catch(Exception ex) { }
        return super.remove();
    }

    @Override
    public void addActor(Actor actor) {
        try {
            music.play();
        } catch(Exception ex) { }
        super.addActor(actor);

    }

    private final GameStage gameStage;
    public InterMazeWindow(GameStage gameStage) {
        super();
        this.gameStage = gameStage;
        switch (gameStage.getState()) {
            case WON:
                showNextMazeWindow();
                break;
            case PAUSED:
                showPausedWindow();
                break;
            case LOST:
                showFailMazeWindow();
                break;
            case WINNER:
                showWinnerWindow();
                break;
        }
    }

    private void showPausedWindow() {

        setTitle("Játék megállítva!");

        MyButton button;

        final MazeActor mazeActor = new MazeActor(gameStage.getMaze());
        mazeActor.setSize(200, 200);
        mazeActor.setPosition(getWidth() / 2 - 100, 180);

        addActor(mazeActor);

        TextButton textButtonNext = new MyButton("Újra", textButtonStyle);
        textButtonNext.setSize(180, 60);
        textButtonNext.setPosition(getWidth() / 2 - 90, 30);
        textButtonNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    gameStage.getMaze().beginPlay();
                } catch (Exception ignored) {
                }
            }
        });

        addActor(textButtonNext);

        button = new MyButton("Folytatás", textButtonStyle);
        button.setSize(210, 60);
        button.setPosition(30, 110);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    gameStage.resume();
                } catch (Exception ignored) {
                }
            }
        });
        addActor(button);


        button = new MyButton("Feladom", textButtonStyle);
        button.setSize(210, 60);
        button.setPosition(360, 110);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    ((AmazingGame) Gdx.app.getApplicationListener())
                            .setScreen(new MazeSelectorScreen());
                } catch (Exception ignored) {
                }
            }
        });
        addActor(button);

    }

    private void showNextMazeWindow() {


        setTitle("Pálya teljesítve!");

        Label label = new Label(
                String.format(
                        "Sikeresen teljesítetetted a pályát! %d darab csillagot gyűjtöttél össze a(z) %d darabból! %nÜgyes vagy! :)",
                        gameStage.getCollectedStars(),
                        gameStage.getMaze().getStarsCount()
                ),
                labelStyle);
        label.setWrap(true);
        label.setAlignment(Align.topLeft, Align.bottomLeft);
        label.setPosition(10, 300);
        label.setWidth(getWidth() - 20);
        label.setFontScale(0.6f);
        addActor(label);


        final MazeActor mazeActor = new MazeActor(gameStage.getMaze().getNextMaze());
        mazeActor.setSize(180, 180);
        mazeActor.setPosition(getWidth() / 2 - 95, 20);

        TextButton textButtonNext = new MyButton("Újra", textButtonStyle);
        textButtonNext.setSize(170, 60);
        textButtonNext.setPosition(20, 110);
        textButtonNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    gameStage.getMaze().beginPlay();
                } catch (Exception ignored) {
                }
            }
        });

        TextButton textButtonRepeat = new MyButton("Tovább", textButtonStyle);
        textButtonRepeat.setSize(170, 60);
        textButtonRepeat.setPosition(getWidth() - 200, 110);
        textButtonRepeat.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    gameStage.getMaze().getNextMaze().beginPlay();
                } catch (Exception ignored) {
                }
            }
        });

        TextButton textButtonSelector = new MyButton("Pályák", textButtonStyle);
        textButtonSelector.setSize(170, 60);
        textButtonSelector.setPosition(20, 45);
        textButtonSelector.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((AmazingGame) Gdx.app.getApplicationListener())
                        .setScreen(new MazeSelectorScreen());
            }
        });

        addActor(mazeActor);
        addActor(textButtonNext);
        addActor(textButtonRepeat);
        addActor(textButtonSelector);

    }

    private void showFailMazeWindow(){
        setTitle("Sajnos vesztettél!");

        Label label = new Label(
                String.format(
                        "Sajnos nem sikerült teljesítened a pályát. Ne feledd, az összes gömbönek célba kell érnie! Próbáld újra! :("),
                labelStyle);
        label.setWrap(true);
        label.setAlignment(Align.topLeft, Align.bottomLeft);
        label.setPosition(10, 300);
        label.setWidth(getWidth() - 20);
        label.setFontScale(0.6f);
        addActor(label);

        TextButton textButtonNext = new MyButton("Újra", textButtonStyle);
        textButtonNext.setSize(170, 60);
        textButtonNext.setPosition(20, 110);
        textButtonNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    gameStage.getMaze().beginPlay();
                } catch (Exception ignored) {
                }
            }
        });

        TextButton textButtonSelector = new MyButton("Pályák", textButtonStyle);
        textButtonSelector.setSize(170, 60);
        textButtonSelector.setPosition(20, 45);
        textButtonSelector.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((AmazingGame) Gdx.app.getApplicationListener())
                        .setScreen(new MazeSelectorScreen());
            }
        });

        addActor(textButtonNext);
        addActor(textButtonSelector);
    }

    private void showWinnerWindow(){
        setTitle("Nyertél!");

        Label label = new Label(
                String.format(
                        "Sikeresen teljesítetetted az összes pályát, gratulálok! :)\n Most egy bónusz pályát vihetsz végig! :)"),
                labelStyle);
        label.setWrap(true);
        label.setAlignment(Align.topLeft, Align.bottomLeft);
        label.setPosition(10, 300);
        label.setWidth(getWidth() - 20);
        label.setFontScale(0.6f);
        addActor(label);


        TextButton textButtonSelector = new MyButton("Tovább", textButtonStyle);
        textButtonSelector.setSize(170, 60);
        textButtonSelector.setPosition(20, 45);
        textButtonSelector.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {Maze.createRandomMaze().beginPlay();}
                catch (Exception e){}
            }
        });

        addActor(textButtonSelector);
    }

}


package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class MazeActor extends Actor {
    private static Texture lockTexture = new Texture("lock.png");
    private final Sprite spriteMaze;
    private final Maze maze;

    MazeActor(Maze maze){
        super();
        this.maze = maze;

        Texture texture = new Texture("level1TestPic.png");
        spriteMaze = new Sprite(texture);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        spriteMaze.draw(batch, parentAlpha);

        if(maze.isLocked()) {
            lockTexture.getHeight();

            batch.draw(lockTexture,
                    (spriteMaze.getWidth() - lockTexture.getWidth()) / 2,
                    (spriteMaze.getHeight()) - lockTexture.getHeight() / 2);
        }

    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        spriteMaze.setPosition(x, y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        spriteMaze.setSize(width, height);
        spriteMaze.setOrigin(width / 2, height / 2);
    }

}

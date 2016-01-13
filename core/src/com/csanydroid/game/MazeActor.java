package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class MazeActor extends Actor {
    Texture texture = new Texture("hole.png");
    Texture texture2 = new Texture("wormhole.png");
    Sprite spriteMaze;
    Sprite spriteLock;


    MazeActor(boolean b){
        super();
        if(b)makeLevelPic(); //Ha true, akkor a szint képét csinálja
        else makeLockPic();
    }

    private void makeLevelPic() {
        spriteMaze = new Sprite(texture);
    }

    private void makeLockPic(){
        spriteLock = new Sprite(texture2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        spriteMaze.draw(batch, parentAlpha);
        //spriteLock.draw(batch, parentAlpha);
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
//        spriteLock.setSize(width, height);
        //spriteLock.setOrigin(width / 2, height / 2);
    }


}

package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class WallActor extends GameActor {

    protected static int strength = Integer.MAX_VALUE, pwOfExpWall = strength/2; // a fal erőssége
    //Azért, mert csak akkor fog felrobbani, ha a ütköző-erő nagyobb
    static boolean itWillExp = false;

    private int fireFrame = 0;

	protected static Texture textureNormWall, textureExpWall;

    private Sprite spriteNorm, spriteExp, spriteExplosion;

    private Animation animationExpWall;

    protected static TextureAtlas textureAtlasExpWall;

	public WallActor(boolean b) {
        if(b)createANormalWall();
        else createAExplosionWall();
	}

    private void createANormalWall(){
        textureNormWall = new Texture("normwall.png");
        spriteNorm = new Sprite(textureNormWall);
        spriteNorm.setSize(GameScreen.BASE_SIZE, GameScreen.BASE_SIZE);
        int pw = strength;
        }

    private void createAExplosionWall(){
        textureExpWall = new Texture("expwall.png");
        spriteExp = new Sprite(textureExpWall);
        spriteExp.setSize(GameScreen.BASE_SIZE, GameScreen.BASE_SIZE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(itWillExp){
            textureAtlasExpWall = new TextureAtlas("bummingForWall.atlas");
            animationExpWall = new Animation(1 / 30f, textureAtlasExpWall.getRegions());
            spriteExplosion = new Sprite();
            spriteExp.setRegion(textureAtlasExpWall.getRegions().get(fireFrame));
            spriteExp.draw(batch); //s.play(parentAlpha );
            if (textureAtlasExpWall.getRegions().size-1 > fireFrame) {
                fireFrame++;
            }
        }
    }



    @Override @SuppressWarnings("unused") public void dispose() {}


}

package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Platform extends Actor {
    Rectangle bounds;
    AssetManager manager;
    GameScreen gameScreen;
    String type = "";

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public Platform()
    {
        setSize(88-10, 52);
        bounds = new Rectangle();
    }
    @Override
    public void act(float delta)
    {
        bounds.set(getX(), getY(), getWidth(), getHeight());
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(type.equals("normal")){
        batch.draw( manager.get( "platform.png", Texture.class), getX()-5, getY() - gameScreen.getScrolly() );
        }
        else{
            batch.draw( manager.get( "platformspecial.png", Texture.class), getX()-5, getY() - gameScreen.getScrolly() );
        }

    }
    public Rectangle getBounds() {
        return bounds;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
}

package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    Rectangle bounds;
    AssetManager manager;
    float speedy, gravity, speedx;
    Platform platformGround;
    GameScreen gameScreen;


    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public Platform getPlatformGround() {
        return platformGround;
    }

    public void setPlatformGround(Platform platformGround) {
        this.platformGround = platformGround;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public Player()
    {
        setX(170);
        setY(400 / 2 - 64 / 2);
        setSize(59,59);
        bounds = new Rectangle();
        speedy = 0;
        speedx = 0;
        gravity = 850f;
        platformGround = null;
    }
    @Override
    public void act(float delta)
    {
        //Actualitza la posició del jugador amb la velocitatvertical
        moveBy(speedx * delta, speedy * delta);
        if ((getX()<= 0)){
            speedx = 200f;
        }
        if (getX()>=450){
            speedx = -200f;
        }
//Actualitza la velocitat vertical amb la gravetat
        if(platformGround != null){
            this.speedy= 0;
            this.speedx =0;
            setY(platformGround.getY()+platformGround.getHeight()-1);
        }
        else{
            speedy -= gravity * delta;
            if (speedy < -1000){
                speedy = -1000;
            }
        }
        bounds.set(getX(), getY(), getWidth(), getHeight());
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(manager.get("player.png", Texture.class),
                getX(), getY()- gameScreen.getScrolly());
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }

    public void salto() {
        if(platformGround.type.equals("special")){
            speedy = 600f*2;
        }
        else {speedy = 600f;
        }
        platformGround = null;

    }
    public void saltoderecha() {
        if(platformGround.type.equals("special")){
            speedy = 600f*2;
        }
        else {speedy = 600f;
        }
        speedx = 200f;
        platformGround = null;

    }
    public void saltoizquierda() {
        if(platformGround.type.equals("special")){
            speedy = 600f*2;
        }
        else {speedy = 600f;
        }
        speedx = -200f;
        platformGround = null;
    }





}


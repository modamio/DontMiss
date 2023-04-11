package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final DontMiss game;
    OrthographicCamera camera;
    public MainMenuScreen(final DontMiss gam) {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 711);
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.manager.get("background.jpg",
                Texture.class), 0, 0);
        game.bigFont.draw(game.batch, "Welcome to \nDon't miss \n     game!", 50, 600);
                game.bigFont.draw(game.batch, "Tap \n    to begin!",
                        20, 250);
        game.batch.end();
        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void show() {
    }
    @Override
    public void hide() {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void dispose() {
    }
}


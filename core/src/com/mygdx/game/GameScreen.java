package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import org.graalvm.compiler.loop.MathUtil;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class GameScreen implements Screen {
    final DontMiss game;
    OrthographicCamera camera;
    Stage stage;
    Player player;
    Array<Platform> platforms;
    boolean sePuedeSaltar = true;
    boolean pintarMasPlataformas = true;
    int lastPlatform;
    int contador = 6;
    private Rectangle leftRect;
    private Rectangle middleRect;
    private Rectangle rightRect;
    private float scrolly;
    private int puntos;
    private float ny;

    public float getScrolly() {
        return scrolly;
    }

    public void setScrolly(float scrolly) {
        this.scrolly = scrolly;
    }

    public GameScreen(final DontMiss gam) {
        this.game = gam;
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 711);
        player = new Player();
        player.setManager(game.manager);
        player.setGameScreen(this);
        stage = new Stage();
        stage.getViewport().setCamera(camera);
        stage.addActor(player);
        scrolly = 0;
        ny = 0;
        // create the obstacles array and spawn the first obstacle
        platforms = new Array<Platform>();
        leftRect = new Rectangle(0, 0, 480 / 3, 711);
        middleRect = new Rectangle(480 / 3, 0, 480 / 3, 711);
        rightRect = new Rectangle((480 / 3) * 2, 0, 480 / 3, 711);



    }
    @Override
    public void render(float delta) {
        // clear the screen with a color
        ScreenUtils.clear(0.3f, 0.8f, 0.8f, 1);
// tell the camera to update its matrices.
        camera.update();
// tell the SpriteBatch to render in the
// coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
// begin a new batch
        game.batch.begin();
        ny = -((scrolly/2) % 711);
        game.batch.draw(game.manager.get("background.jpg", Texture.class), 0,
                ny);
        game.batch.draw(game.manager.get("background.jpg", Texture.class), 0,
                ny+711);
        game.batch.end();
        // Stage batch: Actors
        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.draw();
        // process user input

        if (pintarMasPlataformas){
            spawnPlatform();
        }

        if(contador > 0){
            lastPlatform = lastPlatform+200;
            Platform platforma1 = new Platform();
            platforma1.setX(random());
            platforma1.setY(lastPlatform);
            platforma1.type = "normal";
            platforma1.setManager(game.manager);
            platforma1.setGameScreen(this);
            platforms.add(platforma1);
            stage.addActor(platforma1);
            Platform platforma2 = new Platform();
            platforma2.setX(random());
            platforma2.setY(lastPlatform);
            platforma2.type = "normal";
            platforma2.setManager(game.manager);
            platforma2.setGameScreen(this);
            platforms.add(platforma2);
            stage.addActor(platforma2);
            contador --;

        }
        else {
            lastPlatform = lastPlatform+200;
            Platform platformaSpecial = new Platform();
            platformaSpecial.setX(random());
            platformaSpecial.setY(lastPlatform);
            platformaSpecial.type = "special";
            platformaSpecial.setManager(game.manager);
            platformaSpecial.setGameScreen(this);
            platforms.add(platformaSpecial);
            stage.addActor(platformaSpecial);
            contador = 6;
        }

        if (scrolly < player.getY() -400){
            scrolly = player.getY()-400;
        }
        if (scrolly > player.getY() -200){
            scrolly = player.getY()-100;
        }
        ny = (-scrolly/2) % 711;



        if (Gdx.input.justTouched() && sePuedeSaltar) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if(rightRect.contains(touchPos.x,touchPos.y)){
                player.saltoderecha();
            }
            else if(middleRect.contains(touchPos.x,touchPos.y)){
                player.salto();
            }
            else if(leftRect.contains(touchPos.x,touchPos.y)){
                player.saltoizquierda();

            }

            sePuedeSaltar = false;
        }

        if (player.speedy < 0){
            Platform platformGround = null;
            Iterator<Platform> iter = platforms.iterator();
            while (iter.hasNext()) {
                Platform platform = iter.next();
                if (platform.getBounds().overlaps(player.getBounds())&& player.getY() >= platform.getY()+platform.getHeight()-20) {
                    puntos++;
                    platformGround = platform;
                }

            }

            if(platformGround != null)
            {
                player.setPlatformGround(platformGround);
                sePuedeSaltar = true;
            }
            else
            {
                player.setPlatformGround(null);
            }

        }


        stage.act();
        game.batch.begin();
        game.smallFont.draw(game.batch, "Score: " + (int) puntos, 20, 700);
        game.batch.end();




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
    public int random(){
        return MathUtils.random(0,300);
    }

    private void spawnPlatform() {
        if(platforms.size == 0){
            lastPlatform = 41;
            Platform platformInicial = new Platform();
            platformInicial.setX(0);
            platformInicial.setY(41);
            platformInicial.type = "normal";
            platformInicial.setManager(game.manager);
            platformInicial.setGameScreen(this);
            platforms.add(platformInicial);
            stage.addActor(platformInicial);
            Platform platformInicial2 = new Platform();
            platformInicial2.setX(90);
            platformInicial2.setY(41);
            platformInicial2.type = "normal";
            platformInicial2.setManager(game.manager);
            platformInicial2.setGameScreen(this);
            platforms.add(platformInicial2);
            stage.addActor(platformInicial2);
            Platform platformInicial3 = new Platform();
            platformInicial3.setX(180);
            platformInicial3.setY(41);
            platformInicial3.type = "normal";
            platformInicial3.setManager(game.manager);
            platformInicial3.setGameScreen(this);
            platforms.add(platformInicial3);
            stage.addActor(platformInicial3);
            Platform platformInicial4 = new Platform();
            platformInicial4.setX(260);
            platformInicial4.setY(41);
            platformInicial4.type = "normal";
            platformInicial4.setManager(game.manager);
            platformInicial4.setGameScreen(this);
            platforms.add(platformInicial4);
            stage.addActor(platformInicial4);
            Platform platformInicial5 = new Platform();
            platformInicial5.setX(350);
            platformInicial5.setY(41);
            platformInicial5.type = "normal";
            platformInicial5.setManager(game.manager);
            platformInicial5.setGameScreen(this);
            platforms.add(platformInicial5);
            stage.addActor(platformInicial5);
            Platform platformInicial6 = new Platform();
            platformInicial6.setX(440);
            platformInicial6.setY(41);
            platformInicial6.type = "normal";
            platformInicial6.setManager(game.manager);
            platformInicial6.setGameScreen(this);
            platforms.add(platformInicial6);
            stage.addActor(platformInicial6);

        }


    }
}



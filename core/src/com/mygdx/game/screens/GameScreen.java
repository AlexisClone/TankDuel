package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Tank;
import com.mygdx.game.TankDuel;
import com.mygdx.game.Tools.WorldContactListener;
import com.mygdx.game.scenes.Hud;


/**
 * Created by Alexis on 28/07/2017.
 */

public class GameScreen implements Screen {

    private TankDuel game;

    private Tank greenTank, blueTank;
    private World world;
    private Viewport gamePort;

    private OrthographicCamera gameCam;

    private Hud hud;

    //MAP
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Box2DDebugRenderer b2dr;

    public GameScreen(TankDuel game){
        this.game = game;
        this.world = new World(new Vector2(0,0), true);

        b2dr = new Box2DDebugRenderer();

        gameCam = new OrthographicCamera();
        this.gamePort = new FitViewport(TankDuel.V_WIDTH / TankDuel.PPM, TankDuel.V_HEIGHT / TankDuel.PPM, gameCam);

        //-----------------------------------------
        gameCam.setToOrtho(true, TankDuel.V_WIDTH / TankDuel.PPM, TankDuel.V_HEIGHT / TankDuel.PPM);
        //Bidouille !
        gameCam.translate(0, -56 / TankDuel.PPM);
        gameCam.update();

        greenTank = new Tank(world, Tank.GREEN);
        blueTank = new Tank(world, Tank.BLUE);

        game.batch = new SpriteBatch();

        hud = new Hud(game.batch, greenTank, blueTank);

        Controllers.addListener(new com.mygdx.game.controllers.Controller(greenTank, blueTank));

        maploader = new TmxMapLoader();
        map = maploader.load("map3.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / TankDuel.PPM);

        //Create Walls Bodies
        BodyDef bdef = new BodyDef();
        Body body;
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2) / TankDuel.PPM, (rect.getY() + rect.getHeight()/2) / TankDuel.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2) / TankDuel.PPM, (rect.getHeight()/2) / TankDuel.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        world.setContactListener(new WorldContactListener());

    }

    @Override
    public void show() {
    }

    private void inputHandle(){
        greenTank.move = true;
        blueTank.move = true;

        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            greenTank.setDirection(180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            greenTank.setDirection(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            greenTank.setDirection(270);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            greenTank.setDirection(90);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            greenTank.move = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            greenTank.hasToShot = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Z)){
            blueTank.setDirection(180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            blueTank.setDirection(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            blueTank.setDirection(270);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)){
            blueTank.setDirection(90);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            blueTank.move = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            blueTank.hasToShot = true;
        }

    }

    private void update(float delta){
        inputHandle();

        world.step(1/60f, 6, 2);

        greenTank.update(delta);
        blueTank.update(delta);

        hud.update();

        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {

        update(delta);

        if(greenTank.isDead() || blueTank.isDead())
            game.setScreen(new GameOverScreen(game));

        //DRAW
        Gdx.gl.glClearColor(0.8f, 0.7f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        b2dr.render(world, gameCam.combined);


        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        greenTank.draw(game.batch);
        blueTank.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        //END DRAW

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

}

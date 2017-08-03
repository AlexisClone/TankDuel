package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import controllers.FireTouch;
import controllers.Pad;

import com.mygdx.game.Tank;
import com.mygdx.game.TankDuel;


/**
 * Created by Alexis on 28/07/2017.
 */

public class GameScreen implements Screen {

    int BLUE_TANK;
    int GREEN_TANK;

    TankDuel game;

    Texture img;
    Texture UIGreen, UIBlue;
    Pad padGreen, padBlue;
    FireTouch fireTouchGreen, fireTouchBlue;
    Tank greenTank, blueTank;

    public GameScreen(TankDuel game){
        this.game = game;

        GREEN_TANK = 1;
        BLUE_TANK = 2;

        img = new Texture("img/fond.jpg");

        UIGreen = new Texture("img/UIGreen.png");
        UIBlue = new Texture("img/UIBlue.png");

        padGreen = new Pad(60, 720);
        padBlue = new Pad(1560, 60);

        fireTouchGreen = new FireTouch(110, 110);
        fireTouchBlue = new FireTouch(1610, 770);

        greenTank = new Tank(500, 498, GREEN_TANK);
        blueTank = new Tank(1336, 498, BLUE_TANK);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.7f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //START DRAW
        game.batch.begin();
        game.batch.draw(img, 420, 0, 1080, 1080);

        game.batch.draw(UIGreen, 0, 0, 420, 1080);
        game.batch.draw(UIBlue, 1500, 0, 420, 1080);

        greenTank.draw(game.batch);
        blueTank.draw(game.batch);

        padGreen.draw(game.batch);

        greenTank.drawHearth(game.batch);
        blueTank.drawHearth(game.batch);

        fireTouchGreen.draw(game.batch);
        fireTouchBlue.draw(game.batch);

        game.batch.end();
        //END DRAW

        //COLLIDES
        boolean stop = false;
        stop = greenTank.collides(blueTank);
        stop = (stop || blueTank.collides(greenTank));

        if (stop){
            game.setScreen(new GameOverScreen(game));
        }

        //UPDATES
        greenTank.updateShot(Gdx.graphics.getDeltaTime());
        blueTank.updateShot(Gdx.graphics.getDeltaTime());

        for (int i = 0; i < 10; i++) {
            if (Gdx.input.isTouched(i)) {
                greenTank.move(padGreen.getTouch(Gdx.input.getX(i), Gdx.input.getY(i)), Gdx.graphics.getDeltaTime());
                blueTank.move(padBlue.getTouch(Gdx.input.getX(i), Gdx.input.getY(i)), Gdx.graphics.getDeltaTime());

                greenTank.fire(fireTouchGreen.isTouched(Gdx.input.getX(i), Gdx.input.getY(i)));
                blueTank.fire(fireTouchBlue.isTouched(Gdx.input.getX(i), Gdx.input.getY(i)));
            }
        }

        //END UPDATES
    }

    @Override
    public void resize(int width, int height) {

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
        img.dispose();
    }
}

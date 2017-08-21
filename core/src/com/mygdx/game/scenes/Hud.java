package com.mygdx.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.game.Tank;
import com.mygdx.game.TankDuel;


/**
 * Created by Alexis on 10/08/2017.
 */

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private Label greenHeal, blueHeal;
    private Image bgGreen, bgBlue;
    private Table bgTable, infoTable;
    private Tank greenTank, blueTank;

    public Hud(SpriteBatch sb, Tank greenTank, Tank blueTank){

        this.greenTank = greenTank;
        this.blueTank = blueTank;

        viewport = new FitViewport(TankDuel.V_WIDTH, TankDuel.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        bgGreen = new Image(new Texture("img/bgGreen.png"));
        bgBlue = new Image(new Texture("img/bgBlue.png"));

        greenHeal = new Label(greenTank.getHeal(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        blueHeal = new Label(blueTank.getHeal(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        bgTable = new Table();
        bgTable.top();
        bgTable.setFillParent(true);
        bgTable.add(bgGreen).expandX().padTop(0);
        bgTable.add(bgBlue).expandX().padTop(0);

        bgTable.setDebug(true);

        infoTable = new Table();
        infoTable.top();
        infoTable.setFillParent(true);
        infoTable.add(greenHeal).expandX().padTop(20);
        infoTable.add(blueHeal).expandX().padTop(20);

        infoTable.setDebug(true);

        //add table to the stage
        stage.addActor(bgTable);
        stage.addActor(infoTable);

    }

    public void update(){
        greenHeal.setText(greenTank.getHeal());
        blueHeal.setText(blueTank.getHeal());
    }

    @Override
    public void dispose() {

    }
}

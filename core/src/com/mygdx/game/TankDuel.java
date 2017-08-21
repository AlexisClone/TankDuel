package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.GameScreen;

public class TankDuel extends Game {

	public static final String TITLE = "TankDuel";

	//Virtual Screen size and Box2D Scale(Pixels Per Meter)
	public static final int V_WIDTH = 1920;
	public static final int V_HEIGHT = 1080;
    public static final float PPM = 100;

	//Box2D Collision Bits
	//public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short TANK_BIT = 2;
	public static final short SHOT_BIT = 4;

	public SpriteBatch batch;

	@Override
	public void create () {
        batch = new SpriteBatch();
        setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}

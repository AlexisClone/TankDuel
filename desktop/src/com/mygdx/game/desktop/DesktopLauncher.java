package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.TankDuel;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.title = TankDuel.TITLE;
		cfg.width = TankDuel.V_WIDTH;
		cfg.height = TankDuel.V_HEIGHT;
		cfg.fullscreen = true;

		new LwjglApplication(new TankDuel(), cfg);
	}
}

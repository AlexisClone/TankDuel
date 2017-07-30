package controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Alexis on 26/07/2017.
 */

public class FireTouch {

    private Rectangle fire;
    private Texture image;
    private float x, y;

    public FireTouch(float x, float y){
        this.x = x;
        this.y = y;
        fire = new Rectangle(x, 1080-200-y, 200, 200);
        image = new Texture("img/fireButton.png");
    }

    public boolean isTouched(float x, float y){
        return fire.contains(x, y);
    }

    public void draw(SpriteBatch batch){
        batch.draw(image, x, y);
    }

}

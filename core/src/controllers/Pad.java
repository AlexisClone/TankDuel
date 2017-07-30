package controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Alexis on 26/07/2017.
 */

public class Pad {

    private float x, y;

    private Rectangle up;
    private Rectangle down;
    private Rectangle left;
    private Rectangle right;
    private Texture image;

    public Pad(float x, float y){
        this.x = x;
        this.y = y;
        up = new Rectangle(x+100, 1080-300-y, 100, 100);
        down = new Rectangle(x+100, 1080-100-y, 100, 100);
        left = new Rectangle(x, 1080-300-y+100, 100, 100);
        right = new Rectangle(x+200, 1080-300-y+100, 100, 100);

        image = new Texture("img/pad.png");
    }

    public int getTouch(float x, float y){
        if(up.contains(x, y)){ return 0; }
        else if(down.contains(x, y)){ return 1; }
        else if(left.contains(x, y)){ return 2; }
        else if(right.contains(x, y)){ return 3; }
        else{
            return -1;
        }
    }

    public void draw(SpriteBatch batch){
        batch.draw(image, x, y);
    }

}

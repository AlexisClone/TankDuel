package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Alexis on 27/07/2017.
 */

public class Shot {

    private static final float SHOT_SPEED = 900;
    public static final int NUMBER_BOUNDS = 2;

    public static final int SIZE = 24;

    private Rectangle hitbox;
    private Sprite image;
    private float direction;
    private double speedX, speedY;

    public int numberBounds;

    public Shot(float x, float y, float dir){
        image = new Sprite(new Texture("img/shot.png"));

        direction = dir;
        numberBounds = NUMBER_BOUNDS;

        hitbox = new Rectangle();

        if (direction >= 0 && direction < 90){
            hitbox.x = x + (84/2 - SIZE/2) + (dir/90)*(-84/2 - SIZE/2);
            hitbox.y = y + (84/2 - SIZE/2) + (-dir/90 + 1)*(84/2 + SIZE/2);
        }else if (direction >= 90 && direction < 180){
            hitbox.x = x + (84/2 - SIZE/2) + (dir/90 - 2)*(84/2 + SIZE/2);
            hitbox.y = y + (84/2 - SIZE/2) + (dir/90 - 1)*(-84/2 - SIZE/2);
        }else if (direction >= 180 && direction < 270){
            hitbox.x = x + (84/2 - SIZE/2) + (dir/90 - 2)*(84/2 + SIZE/2);
            hitbox.y = y + (84/2 - SIZE/2) + (-dir/90 + 3)*(-84/2 - SIZE/2);
        }else if (direction >= 270 && direction <= 360){
            hitbox.x = x + (84/2 - SIZE/2) + (4 - dir/90)*(84/2 + SIZE/2);
            hitbox.y = y + (84/2 - SIZE/2) + (-dir/90 + 3)*(-84/2 - SIZE/2);
        }

        hitbox.width = SIZE;
        hitbox.height = SIZE;

        if (direction >= 0 && direction < 90){
            speedX = - SHOT_SPEED * (direction / 100);
            speedY = SHOT_SPEED * (0.9 - (direction / 100));
        }else if (direction >= 90 && direction < 180){
            speedX = - SHOT_SPEED * (1.8 - (direction / 100));
            speedY = SHOT_SPEED * (0.9 - (direction / 100));
        }else if (direction >= 180 && direction < 270){
            speedX = SHOT_SPEED * ((direction / 100) - 1.8);
            speedY = - SHOT_SPEED * (0.9 - ((direction / 100) - 1.8));
        }else if (direction >= 270 && direction <= 360){
            speedX = SHOT_SPEED * (3.6 - (direction / 100));
            speedY = - SHOT_SPEED * (2.7 - (direction / 100));
        }
    }

    public void move(float delta) {
        hitbox.x += speedX * delta;
        hitbox.y += speedY * delta;

        if(hitbox.x + hitbox.width >= 1500 || hitbox.x <= 420 || hitbox.y + hitbox.height >= 1080 || hitbox.y <= 0) {
            if (hitbox.x <= 420 || hitbox.x + hitbox.width >= 1500) {
                speedX = -speedX;
            } else {
                speedY = -speedY;
            }
            numberBounds -= 1;
        }

        if (direction < 0)
            direction = 360 + direction;
        if( direction > 360)
            direction = 360 - direction;
    }

    public float getHitboxX(){
        return hitbox.x;
    }

    public float getHitboxY(){
        return hitbox.y;
    }

    public float getHitboxWidth(){
        return hitbox.width;
    }

    public float getHitboxHeight(){
        return hitbox.height;
    }

    public int getNumberBounds(){
        return numberBounds;
    }

    public void draw(SpriteBatch batch){
        batch.draw(image, hitbox.x, hitbox.y);
    }

}

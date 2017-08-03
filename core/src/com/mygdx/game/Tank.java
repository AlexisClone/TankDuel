package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Alexis on 26/07/2017.
 */

public class Tank {

    private static final int TANK_SPEED = 200;
    private static final float ROTATE_SPEED = 0.00375f * TANK_SPEED * 360;
    private static final double RELOAD_TIME = 0.8;

    private static final int TANK_WIDTH = 84;
    private static final int TANK_HEIGHT = 84;
    private static final float ANIMATION_SPEED = 0.09f;

    private float stateTime;

    private Rectangle hitbox;
    private Animation roll;
    private int heal;
    private int direction;

    private int color;

    private Sprite hearthFull, hearthEmpty;

    private double reload;
    Array<Shot> shots;

    public Tank(int x, int y, int color) {

        TextureRegion[][] rollSpriteSheet = new TextureRegion().split(new Texture("img/spriteSheet.png"), TANK_WIDTH, TANK_HEIGHT);

        if (color == 1) {
            roll = new Animation(ANIMATION_SPEED, rollSpriteSheet[0]);
            direction = 270;
            this.color = 1;
        } else if (color == 2) {
            roll = new Animation(ANIMATION_SPEED, rollSpriteSheet[1]);
            direction = 90;
            this.color = 2;
        }

        hearthFull = new Sprite(new Texture("img/HearthFull.png"));
        hearthEmpty = new Sprite(new Texture("img/HearthEmpty.png"));

        hitbox = new Rectangle();
        hitbox.x = x+2;
        hitbox.y = y+2;
        hitbox.width = 84-4;
        hitbox.height = 84-4;
        heal = 3;

        shots = new Array<Shot>();
        reload = RELOAD_TIME;
    }

    public void draw(SpriteBatch batch) {
        batch.draw((TextureRegion) roll.getKeyFrame(stateTime, true), hitbox.x, hitbox.y, TANK_WIDTH / 2, TANK_HEIGHT / 2, TANK_WIDTH, TANK_HEIGHT, 1, 1, direction);
        for (Shot shot : shots)
            shot.draw(batch);
    }

    public void drawHearth(SpriteBatch batch){
        int i, j;
        for (i=0; i<heal; i++) {
            if (color == 1) {
                batch.draw(hearthFull, 168, 515 - 100*i, 86, 86, 86, 86, 1, 1, 270);
            }else{
                batch.draw(hearthFull, 1920 - 338, 1080 - 680 + 100*i, 86, 86, 86, 86, 1, 1, 90);
            }
        }
        for (j=i; j<3; j++){
            if (color == 1) {
                batch.draw(hearthEmpty, 168, 515 - 100*j, 86, 86, 86, 86, 1, 1, 270);
            }else{
                batch.draw(hearthEmpty, 1920 - 338, 1080 - 680 + 100*j, 86, 86, 86, 86, 1, 1, 90);
            }
        }
    }

    public void move(int dir, double delta) {

        int value = 0;

        switch (dir) {
            case 0:
                if (direction < 10 && direction > -10) {
                    direction = 0;
                    hitbox.y += TANK_SPEED * delta;
                } else if (direction >= 180) {
                    value = 1;
                } else if (direction < 180) {
                    value = -1;
                }
                break;
            case 1:
                if (direction < 190 && direction > 170) {
                    direction = 180;
                    hitbox.y -= TANK_SPEED * delta;
                } else if (direction <= 190) {
                    value = 1;
                } else if (direction >= 170) {
                    value = -1;
                }
                break;
            case 2:
                if (direction < 100 && direction > 80) {
                    direction = 90;
                    hitbox.x -= TANK_SPEED * delta;
                } else if (direction < 90 || direction > 270) {
                    value = 1;
                } else if (direction > 90) {
                    value = -1;
                }
                break;
            case 3:
                if (direction < 280 && direction > 260) {
                    direction = 270;
                    hitbox.x += TANK_SPEED * delta;
                } else if (direction <= 90 || direction >= 270) {
                    value = -1;
                } else {
                    value = 1;
                }
                break;
        }

        direction += value * ROTATE_SPEED * delta;

        if (direction < 0)
            direction = 360 + direction;
        if (direction > 360)
            direction = 360 - direction;

        if (hitbox.x < 420) {
            hitbox.x = 420;
        }
        if (hitbox.x + hitbox.width > 1500){
            hitbox.x = 1416;
        }
        if (hitbox.y < 0){
            hitbox.y = 0;
        }
        if (hitbox.y + hitbox.height > 1080){
            hitbox.y = 996;
        }

        if (dir != -1) {
            stateTime += delta;
        }

    }

    public void fire(boolean bool){
        if(bool && reload >= RELOAD_TIME) {
            shots.add(new Shot(hitbox.x, hitbox.y, direction));
            reload = 0;
        }
    }

    public void updateShot(float delta){
        for (Shot shot : shots) {
            shot.move(delta);
            if (shot.numberBounds == -1)
                shots.removeValue(shot, true);
        }
        reload += delta;
    }

    public Array<Shot> getShots(){
        return shots;
    }

    public void removeShot(Shot shot){
        shots.removeValue(shot, true);
    }

    public boolean collides(Tank otherTank){
        for (Shot otherShot : otherTank.getShots()) {
            if ( hitbox.contains(otherShot.getHitboxX(), otherShot.getHitboxY())
                    || hitbox.contains(otherShot.getHitboxX()+otherShot.getHitboxWidth(), otherShot.getHitboxY())
                    || hitbox.contains(otherShot.getHitboxX(), otherShot.getHitboxY()+otherShot.getHitboxHeight())
                    || hitbox.contains(otherShot.getHitboxX()+otherShot.getHitboxWidth(), otherShot.getHitboxY()+otherShot.getHitboxHeight()) )
            {
                heal -= 1;
                otherTank.removeShot(otherShot);
            }
        }
        for (Shot shot : shots) {
            if ( hitbox.contains(shot.getHitboxX(), shot.getHitboxY())
                    || hitbox.contains(shot.getHitboxX()+shot.getHitboxWidth(), shot.getHitboxY())
                    || hitbox.contains(shot.getHitboxX(), shot.getHitboxY()+shot.getHitboxHeight())
                    || hitbox.contains(shot.getHitboxX()+shot.getHitboxWidth(), shot.getHitboxY()+shot.getHitboxHeight()) )
            {
                if (shot.getNumberBounds() < shot.NUMBER_BOUNDS) {
                    heal -= 1;
                    removeShot(shot);
                }
            }
        }
        if(heal == 0){
            return true;
        }
        return false;
    }

}

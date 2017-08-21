package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Alexis on 26/07/2017.
 */

public class Tank extends Sprite{

    public static final int GREEN = 1;
    public static final int BLUE = 2;

    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;

    private static final float SPEED = 2.5f;

    public World world;
    public Body body;

    private int heal;

    public static final float RELOAD_TIME = 0.5f;
    public double reload;

    //Attributes for controllers
    public boolean move;
    public boolean hasToShot;
    //------------------------

    Array<Shot> shots;

    public Tank(World world, int color){
        this.world = world;

        if (color == GREEN) {
            setPosition(500, 488);
            setRegion(new TextureRegion(new Texture("img/spriteSheet.png"), 0, 0, 84, 84));
        } else if (color == BLUE) {
            setPosition(1200, 488);
            setRegion(new TextureRegion(new Texture("img/spriteSheet.png"), 0, 84, 84, 84));
        }

        heal = 3;

        shots = new Array<Shot>();
        reload = RELOAD_TIME;

        move = false;

        setBounds(getX(), getY(), WIDTH / TankDuel.PPM, HEIGHT / TankDuel.PPM);
        setOrigin((WIDTH/2) / TankDuel.PPM, (HEIGHT/2) / TankDuel.PPM);

        defineTank();

    }

    private void defineTank(){
        BodyDef bdef = new BodyDef();
        bdef.position.set((getX() + WIDTH/2) / TankDuel.PPM, (getY() + HEIGHT/2) / TankDuel.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((WIDTH/2) / TankDuel.PPM, (HEIGHT/2) / TankDuel.PPM);

        fdef.filter.categoryBits = TankDuel.TANK_BIT;
        fdef.filter.maskBits = TankDuel.GROUND_BIT | TankDuel.SHOT_BIT;

        fdef.shape = shape;

        body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch) {
        super.draw(batch);
        for (Shot shot : shots)
            shot.draw(batch);
    }

    public void outch(Shot shot){
        shot.setToDestroy();
        heal--;
    }

    public void update(float delta) {
        for (Shot shot : shots){
            if (!shot.isDestroyed()) {
                shot.update(delta);
            }else{
                shots.removeValue(shot, true);
            }
       }
        reload += delta;
        move();
        fire();
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void setDirection(int direction){
        body.setTransform(body.getPosition().x, body.getPosition().y, (float) Math.toRadians(direction));
    }

    public void move() {
        if(move) {
            switch ((int) Math.toDegrees(body.getAngle())) {
                case 0:
                    body.setLinearVelocity(0, SPEED);
                    break;
                case 90:
                    body.setLinearVelocity(-SPEED, 0);
                    break;
                case 180:
                    body.setLinearVelocity(0, -SPEED);
                    break;
                case 270:
                    body.setLinearVelocity(SPEED, 0);
                    break;
            }
        }else{
            body.setLinearVelocity(0, 0);
        }
    }

    public boolean fire() {
        if(hasToShot && reload >= RELOAD_TIME) {
            shots.add(new Shot(world, getX(), getY(), getRotation()));
            reload = 0;
            hasToShot = false;
        }
        return true;
    }

    public boolean isDead(){
        return (heal <= 0);
    }

    public String getHeal(){
        return Integer.toString(heal) + " LIVES";
    }

}

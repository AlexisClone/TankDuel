package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alexis on 27/07/2017.
 */

public class Shot extends Sprite{

    private static final float SPEED = 0.12f;

    //MAX_TIME (secondes)
    private static final float MAX_TIME = 2.5f;
    private float time;

    private static final int RADIUS = 16;
    private static final int TMP = 10;

    private World world;
    private Body body;

    private Vector2 velocity;

    private boolean toDestroy;
    private boolean destroyed;

    public Shot(World world, float x, float y, float direction){
        this.world = world;
        time = 0;
        toDestroy = false;
        destroyed = false;

        switch ((int) direction){
            //BOTTOM
            case 0:
                setPosition(x + (Tank.WIDTH/2 - RADIUS/2) / TankDuel.PPM, y + (Tank.HEIGHT + TMP) / TankDuel.PPM);
                System.out.println("direction 0");
                velocity = new Vector2(0, SPEED);
                break;
            case 90:
                setPosition((x - (RADIUS + TMP)/ TankDuel.PPM), (y + (Tank.HEIGHT/2 - RADIUS/2) / TankDuel.PPM));
                System.out.println("direction 90");
                velocity = new Vector2(-SPEED, 0);
                break;
            case 180:
                setPosition((x + (Tank.WIDTH/2 - RADIUS/2)/ TankDuel.PPM), (y - (RADIUS + TMP) / TankDuel.PPM));
                System.out.println("direction 180");
                velocity = new Vector2(0, -SPEED);
                break;
            case 270:
                setPosition((x + (Tank.WIDTH + TMP) / TankDuel.PPM), (y + (Tank.HEIGHT/2 - RADIUS/2) / TankDuel.PPM));
                System.out.println("direction 270");
                velocity = new Vector2(SPEED, 0);
                break;
        }

        setBounds(getX(), getY(), RADIUS / TankDuel.PPM, RADIUS / TankDuel.PPM);
        setRegion(new TextureRegion(new Texture("img/shot.png")), 0, 0, RADIUS, RADIUS);

        defineShot();

        body.applyLinearImpulse(velocity, body.getWorldCenter(), true);

    }

    public void setToDestroy(){
        toDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public void update(float dt){
        time += dt;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        if(time >= MAX_TIME)
            setToDestroy();
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }


    public void defineShot(){

        BodyDef bdef = new BodyDef();
        bdef.position.set((getX() + RADIUS / 2 / TankDuel.PPM), (getY() + RADIUS / 2 / TankDuel.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS / 2 / TankDuel.PPM);

        fdef.filter.categoryBits = TankDuel.SHOT_BIT;
        fdef.filter.maskBits = TankDuel.TANK_BIT | TankDuel.GROUND_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        fdef.density = 0.7f;

        body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }

}

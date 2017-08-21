package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Tank;
import com.mygdx.game.Shot;
import com.mygdx.game.TankDuel;

/**
 * Created by Alexis on 19/08/2017.
 */

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case TankDuel.SHOT_BIT | TankDuel.TANK_BIT:
                if(fixA.getFilterData().categoryBits == TankDuel.TANK_BIT)
                    ((Tank) fixA.getUserData()).outch((Shot) fixB.getUserData());
                else
                    ((Tank) fixB.getUserData()).outch((Shot) fixA.getUserData());
                break;

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

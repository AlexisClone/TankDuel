package com.mygdx.game.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Tank;
import com.mygdx.game.screens.GameScreen;

/**
 * Created by Alexis on 19/08/2017.
 */

public class Controller implements ControllerListener {
    //    public static final int BUTTON_X = 2;
    //    public static final int BUTTON_Y = 3;
    public static final int BUTTON_A = 0;
    public static final int BUTTON_B = 1;
    //    public static final int BUTTON_BACK = 6;
    //    public static final int BUTTON_START = 7;
    //    public static final PovDirection BUTTON_DPAD_UP = PovDirection.north;
    //    public static final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
    //    public static final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
    //    public static final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;
    public static final int BUTTON_LB = 4;
    //    public static final int BUTTON_L3 = 8;
    public static final int BUTTON_RB = 5;
    //    public static final int BUTTON_R3 = 9;
    public static final int AXIS_LEFT_X = 1; //-1 is left | +1 is right
    public static final int AXIS_LEFT_Y = 0; //-1 is up | +1 is down
    //    public static final int AXIS_LEFT_TRIGGER = 4; //value 0 to 1f
    public static final int AXIS_RIGHT_X = 3; //-1 is left | +1 is right
    public static final int AXIS_RIGHT_Y = 2; //-1 is up | +1 is down
    //    public static final int AXIS_RIGHT_TRIGGER = 4; //value 0 to -1f

    private Tank greenTank, blueTank;

    public Controller(Tank greenTank, Tank blueTank){
        this.greenTank = greenTank;
        this.blueTank = blueTank;
    }

    @Override
    public void connected(com.badlogic.gdx.controllers.Controller controller) {

    }

    @Override
    public void disconnected(com.badlogic.gdx.controllers.Controller controller) {

    }

    @Override
    public boolean buttonDown(com.badlogic.gdx.controllers.Controller controller, int buttonCode) {
        if (buttonCode == BUTTON_LB)
            greenTank.hasToShot = true;
        if (buttonCode == BUTTON_A)
            greenTank.move = true;

        if (buttonCode == BUTTON_RB)
            blueTank.hasToShot = true;
        if (buttonCode == BUTTON_B)
            blueTank.move = true;

        return false;
    }

    @Override
    public boolean buttonUp(com.badlogic.gdx.controllers.Controller controller, int buttonCode) {
        if (buttonCode == BUTTON_A)
            greenTank.move = false;

        if (buttonCode == BUTTON_B)
            blueTank.move = false;

        return false;
    }

    @Override
    public boolean axisMoved(com.badlogic.gdx.controllers.Controller controller, int axisCode, float value) {

        if (axisCode == AXIS_LEFT_X) {
            if (value >= 0.5f)
                greenTank.setDirection(270);
            else if (value <= -0.5f)
                greenTank.setDirection(90);
        }
        if (axisCode == AXIS_LEFT_Y) {
            if (value >= 0.5f)
                greenTank.setDirection(0);
            else if (value <= -0.5f)
                greenTank.setDirection(180);
        }

        if (axisCode == AXIS_RIGHT_X) {
            if (value >= 0.5f)
                blueTank.setDirection(270);
            else if (value <= -0.5f)
                blueTank.setDirection(90);
        }
        if (axisCode == AXIS_RIGHT_Y) {
            if (value >= 0.5f)
                blueTank.setDirection(0);
            else if (value <= -0.5f)
                blueTank.setDirection(180);
        }

        return false;
    }

    @Override
    public boolean povMoved(com.badlogic.gdx.controllers.Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(com.badlogic.gdx.controllers.Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(com.badlogic.gdx.controllers.Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(com.badlogic.gdx.controllers.Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
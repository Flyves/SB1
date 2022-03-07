package util.rocket_league.io.output;

import rlbot.ControllerState;

/**
 * A helper class for returning controls for your bot.
 *
 * This class is here for your convenience, it is NOT part of the framework. You can change it as much
 * as you want, or delete it.
 */
public class ControlsOutput implements ControllerState {

    // -1 for backwards, 1 for forwards
    public float throttle;

    // -1 for left, 1 for right
    public float steer;

    // -1 for nose down, 1 for nose up
    public float pitch;

    // -1 for nose left, 1 for nose right
    public float yaw;

    // -1 for roof left, 1 for roof right
    public float roll;

    public boolean isJumping;
    public boolean isBoosting;
    public boolean isDrifting;
    public boolean isUsingItem;

    public ControlsOutput() {
        this.throttle = 0;
        this.steer = 0;
        this.pitch = 0;
        this.yaw = 0;
        this.roll = 0;
        this.isJumping = false;
        this.isBoosting = false;
        this.isDrifting = false;
        this.isUsingItem = false;
    }

    private float clamp(float value) {
        return Math.max(-1, Math.min(1, value));
    }

    @Override
    public float getSteer() {
        return clamp(steer);
    }

    @Override
    public float getThrottle() {
        return clamp(throttle);
    }

    @Override
    public float getPitch() {
        return clamp(pitch);
    }

    @Override
    public float getYaw() {
        return clamp(yaw);
    }

    @Override
    public float getRoll() {
        return clamp(roll);
    }

    @Override
    public boolean holdJump() {
        return isJumping;
    }

    @Override
    public boolean holdBoost() {
        return isBoosting;
    }

    @Override
    public boolean holdHandbrake() {
        return isDrifting;
    }

    @Override
    public boolean holdUseItem() {
        return isUsingItem;
    }
}

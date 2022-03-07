package util.timer;

import util.rocket_league.Constants;

public class FrameTimer {

    public int numberOfFramesToCount;
    private int frameCount;

    public FrameTimer(int numberOfFramesToCount) {
        this.numberOfFramesToCount = numberOfFramesToCount;
        this.frameCount = 0;
    }

    public void start() {
        frameCount = 0;
    }

    public boolean isTimeElapsed() {
        return frameCount >= numberOfFramesToCount;
    }

    public void end() {
        frameCount = numberOfFramesToCount;
    }

    public void countFrame() {
        if(frameCount < numberOfFramesToCount) {
            frameCount++;
        }
    }

    public double remainingTime() {
        return (numberOfFramesToCount - frameCount)
                * Constants.BOT_REFRESH_TIME_PERIOD;
    }
}

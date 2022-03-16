package util.rocket_league.keyboard_command_listener;

import util.rocket_league.io.output.ControlsOutput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BotHICommandListener implements KeyListener {
    public static final BotHICommandListener instance = new BotHICommandListener();
    private static final int MAX_DEBUG_INFO_PRESS_COUNT = 2;

    private boolean wPressed;
    private boolean aPressed;
    private boolean sPressed;
    private boolean dPressed;
    private boolean lShiftPressed;
    private boolean pToggled;
    private int oPressCount;

    BotHICommandListener() {
        this.wPressed = false;
        this.aPressed = false;
        this.sPressed = false;
        this.dPressed = false;
        this.lShiftPressed = false;
        this.pToggled = true;
        this.oPressCount = 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            wPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            aPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            sPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            dPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            lShiftPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_P) {
            pToggled = !pToggled;
        }
        if(e.getKeyCode() == KeyEvent.VK_O) {
            oPressCount ++;
            if(oPressCount >= MAX_DEBUG_INFO_PRESS_COUNT) {
                oPressCount = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            wPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            aPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            sPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            dPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            lShiftPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public boolean w() {
        return wPressed;
    }

    public boolean a() {
        return aPressed;
    }

    public boolean s() {
        return sPressed;
    }

    public boolean d() {
        return dPressed;
    }

    public boolean lShift() {
        return lShiftPressed;
    }

    public boolean isBotControlling() {
        return pToggled;
    }

    public int debugScreenInfoId() {
        return oPressCount;
    }

    public ControlsOutput asControlsOutput() {
        final ControlsOutput output = new ControlsOutput();
        if(BotHICommandListener.instance.w()) {
            output.throttle += 1;
        }
        if(BotHICommandListener.instance.s()) {
            output.throttle -= 1;
        }
        if(BotHICommandListener.instance.d()) {
            output.steer += 1;
        }
        if(BotHICommandListener.instance.a()) {
            output.steer -= 1;
        }
        if(BotHICommandListener.instance.lShift()) {
            output.isDrifting = true;
        }
        return output;
    }
}

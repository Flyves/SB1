package util.rocket_league.keyboard_command_listener;

import util.rocket_league.io.output.ControlsOutput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HICommandListener implements KeyListener, MouseListener {
    public static final HICommandListener instance = new HICommandListener();
    private static final int MAX_DEBUG_INFO_PRESS_COUNT = 2;

    private boolean wPressed;
    private boolean aPressed;
    private boolean sPressed;
    private boolean dPressed;
    private boolean lShiftPressed;
    private boolean pToggled;
    private int oPressCount;
    private boolean leftClickPressed;
    private boolean rightClickPressed;

    HICommandListener() {
        this.wPressed = false;
        this.aPressed = false;
        this.sPressed = false;
        this.dPressed = false;
        this.lShiftPressed = false;
        this.pToggled = true;
        this.oPressCount = 1;
        this.leftClickPressed = false;
        this.rightClickPressed = false;
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
        if(HICommandListener.instance.lShift()) {
            output.isDrifting = true;
        }
        if(HICommandListener.instance.w()) {
            output.throttle += 1;
            output.pitch -= 1;
        }
        if(HICommandListener.instance.s()) {
            output.throttle -= 1;
            output.pitch += 1;
        }
        if(HICommandListener.instance.d()) {
            output.steer += 1;
            if(output.isDrifting) {
                output.roll += 1;
            }
            else {
                output.yaw += 1;
            }
        }
        if(HICommandListener.instance.a()) {
            output.steer -= 1;
            if(output.isDrifting) {
                output.roll -= 1;
            }
            else {
                output.yaw -= 1;
            }
        }
        if(leftClickPressed) {
            output.isBoosting = true;
        }
        if(rightClickPressed) {
            output.isJumping = true;
        }
        return output;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            leftClickPressed = true;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            rightClickPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            leftClickPressed = false;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            rightClickPressed = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

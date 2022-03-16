package bot.debug;

import rlbot.render.Renderer;
import util.math.vector.Vector2;
import util.math.vector.Vector3;
import util.renderers.RenderTasks;

import java.awt.*;

public class JoystickPositionRenderer {
    public static final Vector2 BOX_RENDERING_SIZE = new Vector2(100, 100);
    public static final Vector2 JOYSTICK_RENDERING_SIZE = new Vector2(10, 10);

    public static void render(final Vector2 upperLeft, final Vector2 joystickPosition) {
        final Vector2 clampedJoystickPosition = joystickPosition.unitClamp();
        RenderTasks.append(r -> {
            renderBackGround(upperLeft, r);
            renderJoystickPosition(upperLeft, clampedJoystickPosition, r);
        });
    }

    public static void render(final Vector2 upperLeft, final Vector3 joystickPosition) {
        final Vector2 clampedJoystickPosition = joystickPosition.flatten().unitClamp();
        RenderTasks.append(r -> {
            renderBackGround(upperLeft, r);
            final Vector2 centerPosition = upperLeft.plus(BOX_RENDERING_SIZE.scaled(0.5));
            final int x = (int) (centerPosition.x + (joystickPosition.unitClamp().z*BOX_RENDERING_SIZE.scaled(0.5).x));
            final int y = (int) centerPosition.y;

            final Color color2 = new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 230);
            r.drawRectangle2d(color2, new Point(x, y), (int) JOYSTICK_RENDERING_SIZE.x, (int) JOYSTICK_RENDERING_SIZE.y, true);
            renderJoystickPosition(upperLeft, clampedJoystickPosition, r);
        });
    }

    private static void renderBackGround(Vector2 upperLeft, Renderer r) {
        final Color color1 = new Color(Color.LIGHT_GRAY.getRed(), Color.LIGHT_GRAY.getGreen(), Color.LIGHT_GRAY.getBlue(), 127);
        r.drawRectangle2d(color1, upperLeft.toAwtPoint(), (int) BOX_RENDERING_SIZE.x, (int) BOX_RENDERING_SIZE.y, true);
    }

    private static void renderJoystickPosition(Vector2 upperLeft, Vector2 clampedJoystickPosition, Renderer r) {
        final Vector2 centerPosition = upperLeft.plus(BOX_RENDERING_SIZE.scaled(0.5));
        final Vector2 joystickCenterPositionOnScreen = centerPosition.plus(clampedJoystickPosition.scaled(BOX_RENDERING_SIZE.scaled(0.5)));

        final Color color2 = new Color(Color.DARK_GRAY.getRed(), Color.DARK_GRAY.getGreen(), Color.DARK_GRAY.getBlue(), 230);
        r.drawRectangle2d(color2, joystickCenterPositionOnScreen.toAwtPoint(), (int) JOYSTICK_RENDERING_SIZE.x, (int) JOYSTICK_RENDERING_SIZE.y, true);
    }
}

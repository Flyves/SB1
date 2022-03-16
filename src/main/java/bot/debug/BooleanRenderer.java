package bot.debug;

import rlbot.render.Renderer;
import util.math.vector.Vector2;
import util.renderers.RenderTasks;

import java.awt.*;

public class BooleanRenderer {
    public static final Vector2 BOX_RENDERING_SIZE = new Vector2(17, 17);

    public static void render(Vector2 upperLeft, boolean bool) {
        RenderTasks.append(r -> renderBackGround(upperLeft, r, bool));
    }

    private static void renderBackGround(Vector2 upperLeft, Renderer r, boolean bool) {
        final Color color;
        if(bool) {
            color = Color.WHITE;
        }
        else {
            color = Color.DARK_GRAY;
        }
        r.drawRectangle2d(color, upperLeft.toAwtPoint(), (int) BOX_RENDERING_SIZE.x, (int) BOX_RENDERING_SIZE.y, true);
    }
}

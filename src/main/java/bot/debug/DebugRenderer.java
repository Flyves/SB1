package bot.debug;

import util.math.vector.Vector2;
import util.math.vector.Vector3;
import util.renderers.RenderTasks;
import util.rocket_league.io.output.ControlsOutput;
import util.rocket_league.keyboard_command_listener.BotHICommandListener;

import java.awt.*;

public class DebugRenderer {
    private static boolean wasBotControlling = true;
    private static long endTime = 0;

    public static void render(final ControlsOutput outputGeneratedByBot) {
        renderBotOrHuamanControllingIndicator();
        renderBotControls(outputGeneratedByBot);
    }

    private static void renderBotControls(final ControlsOutput outputGeneratedByBot) {
        if(BotHICommandListener.instance.debugScreenInfoId() == 1) {
            JoystickPositionRenderer.render(new Vector2(1790, 50), new Vector2(outputGeneratedByBot.steer, -outputGeneratedByBot.throttle));
            JoystickPositionRenderer.render(new Vector2(1660, 50), new Vector3(outputGeneratedByBot.yaw, -outputGeneratedByBot.pitch, outputGeneratedByBot.roll));
            RenderTasks.append(r -> r.drawString2d("Drifting :", Color.WHITE, new Point(1660, 160), 1, 1));
            BooleanRenderer.render(new Vector2(1780, 160), outputGeneratedByBot.isDrifting);
            RenderTasks.append(r -> r.drawString2d("Jumping :", Color.WHITE, new Point(1660, 180), 1, 1));
            BooleanRenderer.render(new Vector2(1780, 180), outputGeneratedByBot.isJumping);
            RenderTasks.append(r -> r.drawString2d("Boosting :", Color.WHITE, new Point(1660, 200), 1, 1));
            BooleanRenderer.render(new Vector2(1780, 200), outputGeneratedByBot.isBoosting);
        }
    }

    private static void renderBotOrHuamanControllingIndicator() {
        if(BotHICommandListener.instance.isBotControlling()) {
            if(!wasBotControlling) {
                wasBotControlling = true;
                endTime = System.currentTimeMillis() + 300;
            }
            if(System.currentTimeMillis() < endTime) {
                RenderTasks.append(r -> r.drawRectangle2d(Color.green, new Point(1890, 10), 20, 20, true));
            }
        }
        else {
            wasBotControlling = false;
            if(System.currentTimeMillis() % 1000 > 500) {
                RenderTasks.append(r -> r.drawRectangle2d(Color.red, new Point(1890, 10), 20, 20, true));
            }
        }
    }
}

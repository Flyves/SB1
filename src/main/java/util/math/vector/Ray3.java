package util.math.vector;

import util.renderers.RenderTasks;
import util.rocket_league.renderers.Renderable;

import java.awt.*;

public class Ray3 implements Renderable {
    public Vector3 offset;
    public Vector3 direction;

    public Ray3() {
        this.offset = new Vector3();
        this.direction = new Vector3();
    }

    public Ray3(final Vector3 offset, final Vector3 direction) {
        this.offset = offset;
        this.direction = direction;
    }

    @Override
    public void render(final Color color) {
        RenderTasks.append(renderer -> renderer.drawLine3d(color, offset.toFlatVector(), offset.plus(direction).toFlatVector()));
    }

    public Ray3 minus(final Ray3 that) {
        return new Ray3(offset.minus(that.offset), direction.minus(that.direction));
    }

    public Ray3 scaled(final double factor) {
        return new Ray3(offset.scaled(factor), direction.scaled(factor));
    }
}

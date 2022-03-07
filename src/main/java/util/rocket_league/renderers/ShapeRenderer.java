package util.rocket_league.renderers;

import rlbot.render.Renderer;
import util.rocket_league.object_hit_box.car.CarHitBox;
import util.rocket_league.object_hit_box.car.wheels.WheelBox;
import util.math.vector.Ray3;
import util.math.vector.Vector2;
import util.shape.Circle;
import util.shape.Circle3D;
import util.shape.Triangle3D;
import util.math.vector.Vector3;

import java.awt.*;
import java.util.function.Function;

public class ShapeRenderer {

    private final Renderer renderer;

    public ShapeRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void renderCross(Vector3 position, Color color) {
        renderer.drawLine3d(color, position.plus(new Vector3(20, 20, 20)).toFlatVector(), position.plus(new Vector3(-20, -20, -20)).toFlatVector());
        renderer.drawLine3d(color, position.plus(new Vector3(-20, 20, 20)).toFlatVector(), position.plus(new Vector3(20, -20, -20)).toFlatVector());
        renderer.drawLine3d(color, position.plus(new Vector3(20, -20, 20)).toFlatVector(), position.plus(new Vector3(-20, 20, -20)).toFlatVector());
        renderer.drawLine3d(color, position.plus(new Vector3(20, 20, -20)).toFlatVector(), position.plus(new Vector3(-20, -20, 20)).toFlatVector());
    }

    public void renderRay3(Ray3 ray, Color color) {
        renderer.drawLine3d(color, ray.offset.toFlatVector(), ray.offset.plus(ray.direction).toFlatVector());
    }

    public void renderTriangle(Triangle3D triangle, Color color) {
        renderer.drawLine3d(color, triangle.point0.toFlatVector(), triangle.point1.toFlatVector());
        renderer.drawLine3d(color, triangle.point1.toFlatVector(), triangle.point2.toFlatVector());
        renderer.drawLine3d(color, triangle.point2.toFlatVector(), triangle.point0.toFlatVector());
    }

    public void renderCircle(Circle circle, double zOffset, Color color) {
        int amountOfPoints = 100;
        double precision = Math.PI*2/amountOfPoints;
        Vector2 point = circle.findPointOnCircle(0);
        Vector2 previousPoint;

        for(int i = 1; i < amountOfPoints; i++) {
            previousPoint = point;
            point = circle.findPointOnCircle(i*precision);
            renderer.drawLine3d(color, new Vector3(previousPoint, zOffset).toFlatVector(), new Vector3(point, zOffset).toFlatVector());
        }
    }

    public void renderCircle3D(Circle3D circle, Color color) {
        int amountOfPoints = 100;
        double precision = Math.PI*2/amountOfPoints;
        Vector3 point = circle.findPointOnCircle(0);
        Vector3 previousPoint;

        for(int i = 1; i <= amountOfPoints; i++) {
            previousPoint = point;
            point = circle.findPointOnCircle(i*precision);
            renderer.drawLine3d(color, previousPoint.toFlatVector(), point.toFlatVector());
        }
    }

    public void renderTrajectory(Function<Double, Vector3> parabola, double amountOfTimeToRender, Color color) {
        Vector3 previousPosition = parabola.apply(0.0);
        for(int i = 1; i < 200; i++) {
            Vector3 nextPosition = parabola.apply(i*amountOfTimeToRender/600);
            if(nextPosition != null && previousPosition != null) {
                renderer.drawLine3d(color, nextPosition.toFlatVector(), previousPosition.toFlatVector());
            }
            previousPosition = nextPosition;
        }
    }

    public void renderHitBox(CarHitBox carHitBox, Color color) {
        Vector3 opponentNoseOrientation = carHitBox.frontOrientation;
        Vector3 opponentRoofOrientation = carHitBox.roofOrientation;
        Vector3 opponentRightOrientation = opponentNoseOrientation.crossProduct(opponentRoofOrientation);

        Vector3 hitBoxCorner111 = carHitBox.closestPointOnSurface(opponentNoseOrientation.plus(opponentRoofOrientation).plus(opponentRightOrientation).scaled(300).plus(carHitBox.centerPositionOfHitBox));
        Vector3 hitBoxCorner110 = carHitBox.closestPointOnSurface(opponentNoseOrientation.plus(opponentRoofOrientation).plus(opponentRightOrientation.scaled(-1)).scaled(300).plus(carHitBox.centerPositionOfHitBox));
        Vector3 hitBoxCorner101 = carHitBox.closestPointOnSurface(opponentNoseOrientation.plus(opponentRoofOrientation.scaled(-1)).plus(opponentRightOrientation).scaled(300).plus(carHitBox.centerPositionOfHitBox));
        Vector3 hitBoxCorner100 = carHitBox.closestPointOnSurface(opponentNoseOrientation.plus(opponentRoofOrientation.scaled(-1)).plus(opponentRightOrientation.scaled(-1)).scaled(300).plus(carHitBox.centerPositionOfHitBox));
        Vector3 hitBoxCorner011 = carHitBox.closestPointOnSurface(opponentNoseOrientation.scaled(-1).plus(opponentRoofOrientation).plus(opponentRightOrientation).scaled(300).plus(carHitBox.centerPositionOfHitBox));
        Vector3 hitBoxCorner010 = carHitBox.closestPointOnSurface(opponentNoseOrientation.scaled(-1).plus(opponentRoofOrientation).plus(opponentRightOrientation.scaled(-1)).scaled(300).plus(carHitBox.centerPositionOfHitBox));
        Vector3 hitBoxCorner001 = carHitBox.closestPointOnSurface(opponentNoseOrientation.scaled(-1).plus(opponentRoofOrientation.scaled(-1)).plus(opponentRightOrientation).scaled(300).plus(carHitBox.centerPositionOfHitBox));
        Vector3 hitBoxCorner000 = carHitBox.closestPointOnSurface(opponentNoseOrientation.scaled(-1).plus(opponentRoofOrientation.scaled(-1)).plus(opponentRightOrientation.scaled(-1)).scaled(300).plus(carHitBox.centerPositionOfHitBox));

        renderer.drawLine3d(color, hitBoxCorner111.toFlatVector(), hitBoxCorner110.toFlatVector());
        renderer.drawLine3d(color, hitBoxCorner111.toFlatVector(), hitBoxCorner101.toFlatVector());
        renderer.drawLine3d(color, hitBoxCorner111.toFlatVector(), hitBoxCorner011.toFlatVector());

        renderer.drawLine3d(color, hitBoxCorner010.toFlatVector(), hitBoxCorner011.toFlatVector());
        renderer.drawLine3d(color, hitBoxCorner010.toFlatVector(), hitBoxCorner000.toFlatVector());
        renderer.drawLine3d(color, hitBoxCorner010.toFlatVector(), hitBoxCorner110.toFlatVector());

        renderer.drawLine3d(color, hitBoxCorner001.toFlatVector(), hitBoxCorner000.toFlatVector());
        renderer.drawLine3d(color, hitBoxCorner001.toFlatVector(), hitBoxCorner011.toFlatVector());
        renderer.drawLine3d(color, hitBoxCorner001.toFlatVector(), hitBoxCorner101.toFlatVector());

        renderer.drawLine3d(color, hitBoxCorner100.toFlatVector(), hitBoxCorner101.toFlatVector());
        renderer.drawLine3d(color, hitBoxCorner100.toFlatVector(), hitBoxCorner110.toFlatVector());
        renderer.drawLine3d(color, hitBoxCorner100.toFlatVector(), hitBoxCorner000.toFlatVector());
    }

    public void renderWheelBox(WheelBox wheelBox, Color color) {
        wheelBox.render(renderer, color);
    }
}
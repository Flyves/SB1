package util.rocket_league.physics.collision;

import util.data_structure.tupple.Tuple2;
import util.math.linear_transform.LinearApproximator;
import util.math.matrix.Matrix3By3;
import util.math.vector.Vector2;
import util.math.vector.Vector3;
import util.rocket_league.Constants;
import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.CarData;

// Highly inspired from Samuel Mish's notes:
// https://samuelpmish.github.io/notes/RocketLeague/car_ball_interaction/

// Note: java naming conventions were not followed to make it easier to follow along the paper, if needed.

public class CarBallCollider {
    private static final double mu = 0.285;
    private static final double mc = Constants.CAR_MASS;
    private static final double mb = Constants.BALL_MASS;
    private static final Matrix3By3 Ic = Constants.CAR_MOMENT_OF_INERTIA_TENSOR;
    private static final Matrix3By3 Ib = Constants.BALL_MOMENT_OF_INERTIA_TENSOR;
    private static final LinearApproximator s = new LinearApproximator();
    static {
        s.sample(new Vector2(0, 0.65));
        s.sample(new Vector2(600, 0.65));
        s.sample(new Vector2(2300, 0.56));
        s.sample(new Vector2(4600, 0.3));
    }

    public static Tuple2<CarData, BallData> collide(final CarData car, final BallData ball) {
        final Vector3 p = car.carHitBox.closestPointOnSurface(ball.position);
        final Vector3 n = ball.position.minus(p);
        final Matrix3By3 Lc = p.minus(car.position).toCrossProductMatrix();
        final Matrix3By3 Lb = p.minus(ball.position).toCrossProductMatrix();
        final Vector3 Vc = car.velocity;
        final Vector3 Vb = ball.velocity;
        final Vector3 Wc = car.angularVelocity;
        final Vector3 Wb = ball.spin;

        final Vector3 deltaV =
                Vc.minus(Lc.multiply(Wc))
                        .minus(Vb.minus(Lb.multiply(Wb)));
        final Matrix3By3 inverseReducedMassMatrix =
                Matrix3By3.UNIT.scaled((1/mc) + (1/mb))
                        .minus(Lc.multiply(Ic.inverse()).multiply(Lc))
                        .minus(Lb.multiply(Ib.inverse()).multiply(Lb));
        final Vector3 unconstrainedJ =
                inverseReducedMassMatrix.inverse()
                        .multiply(deltaV)
                        .scaled(-1);
        final Vector3 Jperp = unconstrainedJ.projectOnto(n);
        final Vector3 Jpara = unconstrainedJ.minus(Jperp);
        final double Jratio = mu * (Jperp.magnitude()/Jpara.magnitude());
        final Vector3 JOnCar = Jperp.plus(Jpara.scaled(Math.min(1, Jratio))).scaled(-1);

        final Vector3 squishedCarToBall = ball.position.minus(car.position)
                .scaled(1, 1, 0.35);
        final Vector3 carNoseOrientation = car.orientation.nose;
        final Vector3 squishedCarToBallSubtraction = carNoseOrientation.scaled(0.35 * squishedCarToBall.dotProduct(carNoseOrientation));
        final Vector3 n2 = squishedCarToBall.minus(squishedCarToBallSubtraction).normalized();
        final double carToBallSpeed = Vb.minus(Vc).magnitude();
        final Vector3 additionalJForBall = n2.scaled(mb * carToBallSpeed * s.compute(carToBallSpeed));
        final Vector3 JOnBall = JOnCar.scaled(-1).plus(additionalJForBall);

        final CarData resultingCar = new CarData(car, Vc.plus(JOnCar.scaled(1/mc)), Wc.plus(Ic.inverse().multiply(Lc).multiply(JOnCar)));
        final BallData resultingBall = new BallData(ball.position, Vb.plus(JOnBall.scaled(1/mb)), Wb.plus(Ib.inverse().multiply(Lb).multiply(JOnBall)));

        return new Tuple2<>(resultingCar, resultingBall);
    }
}

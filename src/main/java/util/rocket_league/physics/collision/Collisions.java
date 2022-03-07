package util.rocket_league.physics.collision;

import util.rocket_league.dynamic_objects.ball.BallData;
import util.rocket_league.dynamic_objects.car.CarData;
import util.data_structure.tupple.Tuple2;
import util.math.linear_transform.LinearApproximator;
import util.math.matrix.Matrix3By3;
import util.math.vector.Ray3;
import util.math.vector.Vector2;
import util.math.vector.Vector3;
import util.rocket_league.Constants;

public class Collisions {
    
    public static Tuple2<CarData, BallData> collide(final CarData car, final BallData ball) {
        return CarBallCollider.collide(car, ball);
    }

    public static BallData collide(BallData ball, Ray3 normal) {
        return ball;
    }

    public static Tuple2<CarData, CarData> collide(CarData car1, CarData car2) {
        return new Tuple2<>(car1, car2);
    }

    public static Tuple2<BallData, BallData> collide(BallData ball1, BallData ball2) {
        return new Tuple2<>(ball1, ball2);
    }
}

package util.rocket_league.object_hit_box.car.wheels;

import util.rocket_league.dynamic_objects.car.Orientation;
import util.math.vector.Ray3;
import util.math.vector.Vector3;
import util.shape.Circle3D;

public class OctaneWheelBox extends WheelBox {

    // all wheel stats are from the spreadsheet provided by halfwaydead (thanks a lot):
    // https://www.youtube.com/watch?v=99j1mTN1_Vs&feature=youtu.be&ab_channel=RocketScience

    public static final Circle3D FRONT_RIGHT_WHEEL = new Circle3D(new Ray3(new Vector3(51.25, 25.9, -6), new Vector3(0, 1, 0)), 12.5);
    public static final Circle3D FRONT_LEFT_WHEEL = new Circle3D(new Ray3(new Vector3(51.25, -25.9, -6), new Vector3(0, 1, 0)), 12.5);
    public static final Circle3D BACK_RIGHT_WHEEL = new Circle3D(new Ray3(new Vector3(-33.75, 29.5, -4.3), new Vector3(0, 1, 0)), 15);
    public static final Circle3D BACK_LEFT_WHEEL = new Circle3D(new Ray3(new Vector3(-33.75, -29.5, -4.3), new Vector3(0, 1, 0)), 15);

    public OctaneWheelBox(Vector3 position, Orientation orientation) {
        super(FRONT_RIGHT_WHEEL, FRONT_LEFT_WHEEL, BACK_RIGHT_WHEEL, BACK_LEFT_WHEEL, position, orientation);
    }
}

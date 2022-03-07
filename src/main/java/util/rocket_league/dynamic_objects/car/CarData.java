package util.rocket_league.dynamic_objects.car;

import util.rocket_league.object_hit_box.car.CarHitBox;
import util.rocket_league.object_hit_box.car.wheels.OctaneWheelBox;
import util.rocket_league.object_hit_box.car.wheels.WheelBox;
import util.math.vector.Vector3;

public class CarData {

    public final Vector3 position;
    public final Vector3 velocity;
    public final Orientation orientation;
    public final Vector3 angularVelocity;
    public final double boost;
    public final CarHitBox carHitBox;
    public final WheelBox wheelBox;
    public final double elapsedSeconds;

    public CarData(rlbot.flat.PlayerInfo playerInfo, float elapsedSeconds) {
        this.position = new Vector3(playerInfo.physics().location());
        this.velocity = new Vector3(playerInfo.physics().velocity());
        this.boost = playerInfo.boost();
        this.orientation = Orientation.fromFlatbuffer(playerInfo);
        this.angularVelocity = new Vector3(playerInfo.physics().angularVelocity())
                .rotateToOrientationReferenceFrom(orientation)
                .scaled(-1)
                .rotateFromOrientationReferenceTo(orientation);
        this.carHitBox = new CarHitBox(position, playerInfo.hitboxOffset(), playerInfo.hitbox(), orientation.nose, orientation.roof);
        this.wheelBox = new OctaneWheelBox(position, orientation);
        this.elapsedSeconds = elapsedSeconds;
    }

    public CarData(CarData car, Vector3 newVelocity, Vector3 newAngularVelocity) {
        this.position = car.position;
        this.velocity = newVelocity;
        this.angularVelocity = newAngularVelocity;
        this.boost = car.boost;
        this.orientation = car.orientation;
        this.carHitBox = car.carHitBox;
        this.wheelBox = car.wheelBox;
        this.elapsedSeconds = car.elapsedSeconds;
    }

    public final Vector3 surfaceVelocity(final Vector3 normal) {
        return angularVelocity.crossProduct(normal).scaled(carHitBox.closestPointOnSurface(normal.scaled(200)).minus(carHitBox.centerPositionOfHitBox).magnitude());
    }
}

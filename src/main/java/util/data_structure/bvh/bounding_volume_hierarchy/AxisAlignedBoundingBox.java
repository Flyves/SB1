package util.data_structure.bvh.bounding_volume_hierarchy;

import util.data_structure.bvh.math.Vector3;

public class AxisAlignedBoundingBox {
    public Vector3 upperLeft;
    public Vector3 lowerRight;

    public AxisAlignedBoundingBox(Vector3 upperLeft, Vector3 lowerRight) {
        this.upperLeft =  upperLeft;
        this.lowerRight = lowerRight;
    }

    public AxisAlignedBoundingBox(AxisAlignedBoundingBox aabb1, AxisAlignedBoundingBox aabb2) {
        double smallestX = Math.min(aabb1.upperLeft.x,  aabb2.upperLeft.x);
        double biggestX  = Math.max(aabb1.lowerRight.x, aabb2.lowerRight.x);
        double smallestY = Math.min(aabb1.upperLeft.y,  aabb2.upperLeft.y);
        double biggestY  = Math.max(aabb1.lowerRight.y, aabb2.lowerRight.y);
        double smallestZ = Math.min(aabb1.upperLeft.z,  aabb2.upperLeft.z);
        double biggestZ  = Math.max(aabb1.lowerRight.z, aabb2.lowerRight.z);

        this.upperLeft  = new Vector3(smallestX, smallestY, smallestZ);
        this.lowerRight = new Vector3(biggestX,  biggestY, biggestZ);
    }

    public Boolean collidesWith(AxisAlignedBoundingBox that) {
        return     this.upperLeft.x < that.lowerRight.x
                && that.upperLeft.x < this.lowerRight.x

                && this.upperLeft.y < that.lowerRight.y
                && that.upperLeft.y < this.lowerRight.y

                && this.upperLeft.z < that.lowerRight.z
                && that.upperLeft.z < this.lowerRight.z;
    }
}

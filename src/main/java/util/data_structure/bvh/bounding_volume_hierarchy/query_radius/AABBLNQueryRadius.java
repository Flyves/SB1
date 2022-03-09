package util.data_structure.bvh.bounding_volume_hierarchy.query_radius;

import util.data_structure.bvh.binary.query_radius.LNQueryRadius;
import util.data_structure.bvh.bounding_volume_hierarchy.AxisAlignedBoundingBox;

import java.util.function.Function;

class AABBLNQueryRadius<T> extends LNQueryRadius<AxisAlignedBoundingBox, T> {
    public AABBLNQueryRadius(final T leafObject, final Function<T, AxisAlignedBoundingBox> aabbMapper) {
        super(aabbMapper.apply(leafObject), leafObject);
    }
}

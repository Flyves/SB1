package util.data_structure.bvh.morton_code;

import util.data_structure.bvh.bounding_volume_hierarchy.AxisAlignedBoundingBox;
import util.data_structure.bvh.math.Vector3;
import util.data_structure.bvh.math.Vector3Int;

import java.util.List;
import java.util.function.Function;

public class MortonScaleMapper {
    public static <T>Function<Vector3, Vector3Int> getFunctionInstance(
            final List<T> elements,
            final Function<T, AxisAlignedBoundingBox> leafObjectAabbMapper) {
        final AxisAlignedBoundingBox bigAabb = elements.stream()
                .map(leafObjectAabbMapper)
                .reduce(AxisAlignedBoundingBox::new)
                .orElse(new AxisAlignedBoundingBox(new Vector3(), new Vector3()));
        return v -> {
            final long maxSize = 0b100000000000000000000;
            final Vector3 size = bigAabb.upperLeft.minus(bigAabb.lowerRight);
            final Vector3 scale = new Vector3(maxSize/size.x, maxSize/size.y, maxSize/size.z);
            return new Vector3Int(v.minus(bigAabb.lowerRight).scaled(scale));
        };
    }
}

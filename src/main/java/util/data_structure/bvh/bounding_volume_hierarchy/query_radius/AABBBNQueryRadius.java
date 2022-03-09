package util.data_structure.bvh.bounding_volume_hierarchy.query_radius;

import util.data_structure.bvh.binary.query_radius.BNQueryRadius;
import util.data_structure.bvh.bounding_volume_hierarchy.AxisAlignedBoundingBox;
import util.data_structure.bvh.math.Vector3;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

class AABBBNQueryRadius<T> extends BNQueryRadius<AxisAlignedBoundingBox, T> {
    private final Map<Long, T> mortonMap;
    private final Function<T, Vector3> leafObjectPositionMapper;
    private final Function<T, AxisAlignedBoundingBox> leafObjectAabbMapper;

    public AABBBNQueryRadius(
            final List<Long> mortonCode,
            final Map<Long, T> mortonMap,
            final Function<T, Vector3> leafObjectPositionMapper,
            final Function<T, AxisAlignedBoundingBox> leafObjectAabbMapper) {
        super();
        this.mortonMap = mortonMap;
        this.leafObjectPositionMapper = leafObjectPositionMapper;
        this.leafObjectAabbMapper = leafObjectAabbMapper;
        super.setValue(buildBinaryHierarchy(mortonCode));
    }

    private AxisAlignedBoundingBox buildBinaryHierarchy(List<Long> mortonCodes) {
        final int biggestVaryingBitNumber = biggestVaryingBitNumber(mortonCodes);

        List<Long> rightCodes = mortonCodes.stream()
                .filter(value -> (value & (1L << biggestVaryingBitNumber)) > 0)
                .collect(Collectors.toList());
        List<Long> leftCodes = mortonCodes.stream()
                .filter(value -> (value & (1L << biggestVaryingBitNumber)) == 0)
                .collect(Collectors.toList());

        if(rightCodes.size() > 1) {
            super.setRight(new AABBBNQueryRadius<>(rightCodes, mortonMap, leafObjectPositionMapper, leafObjectAabbMapper));
        }
        else if(rightCodes.size() == 1) {
            super.setRight(new AABBLNQueryRadius<>(mortonMap.get(rightCodes.get(0)), leafObjectAabbMapper));
        }
        if(leftCodes.size() > 1) {
            super.setLeft(new AABBBNQueryRadius<>(leftCodes, mortonMap, leafObjectPositionMapper, leafObjectAabbMapper));
        }
        else if(leftCodes.size() == 1) {
            super.setLeft(new AABBLNQueryRadius<>(mortonMap.get(leftCodes.get(0)), leafObjectAabbMapper));
        }

        return new AxisAlignedBoundingBox(super.getRight().getValue(), super.getLeft().getValue());
    }

    private int biggestVaryingBitNumber(List<Long> values) {
        AtomicInteger biggestBitNumber = new AtomicInteger(0);
        AtomicLong lastValue = new AtomicLong(values.get(values.size()-1));

        values.forEach(value -> {
            final long difference = lastValue.get() ^ value;
            final int bitNumberOfDifference = biggestBitNumber(difference);
            if(biggestBitNumber.get() < bitNumberOfDifference) {
                biggestBitNumber.set(bitNumberOfDifference);
                lastValue.set(value);
            }
        });

        return biggestBitNumber.get();
    }

    private int biggestBitNumber(long value) {
        long shiftedValue = value;
        int shiftedAmount = 0;

        while(shiftedValue > 1) {
            shiftedValue >>= 1;
            shiftedAmount++;
        }

        return shiftedAmount;
    }
}

package util.rocket_league.playfield;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import util.data_structure.bvh.Bvh;
import util.data_structure.bvh.shape.Triangle3;
import util.file.ObjectStreaming;
import util.shape.Triangle3D;

import javax.xml.ws.Holder;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Standard {
    public static final File FILE_LOCATION = new File("src\\main\\resources\\maps\\standard_map_mesh.obj");
    public static final Bvh BVH = new Bvh(FILE_LOCATION);
    public static final List<Triangle3D> MESH_TRIANGLES = BVH.getLeaves().stream()
            .map(Triangle3::toShapeTriangle3D)
            .collect(Collectors.toList());

}

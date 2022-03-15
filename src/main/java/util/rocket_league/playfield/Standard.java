package util.rocket_league.playfield;

import util.data_structure.bvh.Bvh;

import java.io.File;

public class Standard {
    public static final File FILE_LOCATION = new File("src\\main\\resources\\maps\\standard_map_mesh.obj");
    public static final Bvh BVH = new Bvh(FILE_LOCATION);

}

package util.data_structure.bvh.file;

import util.data_structure.bvh.math.Vector3;
import util.data_structure.bvh.shape.Triangle3;

import java.util.ArrayList;
import java.util.List;

public class MeshBuilder {
    private final List<Vector3> vertexList = new ArrayList<>();
    private final List<Triangle3> triangleList = new ArrayList<>();

    public MeshBuilder() {
    }

    public MeshBuilder addVertex(Vector3 vertex) {
        vertexList.add(vertex);
        return this;
    }

    public MeshBuilder addTriangle(int vertexIndex0, int vertexIndex1, int vertexIndex2) {
        final Vector3 vertex0 = vertexList.get(vertexIndex0);
        final Vector3 vertex1 = vertexList.get(vertexIndex1);
        final Vector3 vertex2 = vertexList.get(vertexIndex2);
        final Triangle3 triangle = new Triangle3(vertex0, vertex1, vertex2);
        triangleList.add(triangle);
        return this;
    }

    public List<Triangle3> build() {
        return triangleList;
    }
}

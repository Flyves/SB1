package util.data_structure.bvh.file;

import util.data_structure.bvh.math.Vector3;
import util.data_structure.bvh.math.Vector3Int;
import util.data_structure.bvh.shape.Triangle3;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ObjFileReader {
    public static List<Triangle3> loadMeshFromFile(final File objFile) {
        final List<String> fileContent = IOFile.getFileContent(objFile).get();

        final MeshBuilder meshBuilder = new MeshBuilder();

        for(String fileLine: fileContent) {
            final List<String> lineParameters = getLineParameters(fileLine);
            if(isValidLineLength(lineParameters)) {
                if(isVertex(lineParameters)) {
                    final Vector3 vertex = parseVertex(lineParameters);
                    meshBuilder.addVertex(vertex);
                }
                else if(isTriangle(lineParameters)) {
                    final Vector3Int vertexReferences = parseTriangle(lineParameters);
                    final int vertexIndex0 = vertexReferences.x;
                    final int vertexIndex1 = vertexReferences.y;
                    final int vertexIndex2 = vertexReferences.z;
                    meshBuilder.addTriangle(vertexIndex0, vertexIndex1, vertexIndex2);
                }
            }
        }

        return meshBuilder.build();
    }

    private static List<String> getLineParameters(final String fileLine) {
        return Arrays.asList(fileLine.split(" "));
    }

    private static boolean isValidLineLength(final List<String> lineParameters) {
        return lineParameters.size() == 4;
    }

    private static boolean isVertex(final List<String> lineParameters) {
        return lineParameters.get(0).equals("v");
    }

    private static boolean isTriangle(final List<String> lineParameters) {
        return lineParameters.get(0).equals("f");
    }

    private static Vector3 parseVertex(final List<String> lineParameters) {
        final double positionX = -Double.parseDouble(lineParameters.get(2));
        final double positionY = Double.parseDouble(lineParameters.get(1));
        final double positionZ = Double.parseDouble(lineParameters.get(3));

        return new Vector3(positionX, positionY, positionZ);
    }

    private static Vector3Int parseTriangle(final List<String> lineParameters) {
        final int vertexReference0 = Integer.parseInt(lineParameters.get(1)) - 1;
        final int vertexReference1 = Integer.parseInt(lineParameters.get(2)) - 1;
        final int vertexReference2 = Integer.parseInt(lineParameters.get(3)) - 1;

        return new Vector3Int(vertexReference0, vertexReference1, vertexReference2);
    }
}

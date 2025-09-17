package scales.github.utils;

import scales.github.Main;
import scales.github.constructors.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ModelUtil {
    public static ModelFace[] getModelFaces(String obj) {
        try {
            ArrayList<Vec3> vertices = new ArrayList<>();
            ArrayList<Vec3> normals = new ArrayList<>();
            ArrayList<Vec2> textureCoordinates = new ArrayList<>();

            ArrayList<ModelFace> faces = new ArrayList<>();

            InputStream fileStream = Main.class.getResourceAsStream(obj);
            assert fileStream != null;

            BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileStream));

            for (String line; (line = fileReader.readLine()) != null;) {
                // some people format their objs with extra spaces ig.
                line = line.replaceAll("\\s+", " ");

                String[] lineSplit = line.split(" ");

                switch (lineSplit[0]) {
                    case "v" -> vertices.add(new Vec3(lineSplit[1], lineSplit[2], lineSplit[3]));
                    case "vt" -> textureCoordinates.add(new Vec2(lineSplit[1], lineSplit[2]));
                    case "vn" -> normals.add(new Vec3(lineSplit[1], lineSplit[2], lineSplit[3]));
                    case "f" -> {
                        int amountOfValues = lineSplit.length-1;

                        for (int i = 0; i < amountOfValues-1; i+=2) {
                            String[] vectorParts = new String[] {
                                    lineSplit[i + 1],
                                    lineSplit[((i + 1) % amountOfValues) + 1],
                                    lineSplit[((i + 2) % amountOfValues) + 1],
                            };

                            Triangle vertexTriangle = new Triangle(
                                    vertices.get(getFaceElement(vectorParts[0], 0)),
                                    vertices.get(getFaceElement(vectorParts[1], 0)),
                                    vertices.get(getFaceElement(vectorParts[2], 0))
                            );

                            Triangle textureCoordinatesTriangle = textureCoordinates.isEmpty() ? Triangle.EMPTY : new Triangle(
                                    textureCoordinates.get(getFaceElement(vectorParts[0], 1)),
                                    textureCoordinates.get(getFaceElement(vectorParts[1], 1)),
                                    textureCoordinates.get(getFaceElement(vectorParts[2], 1))
                            );

                            Triangle normalsTriangle = normals.isEmpty() ? Triangle.EMPTY : new Triangle(
                                    normals.get(getFaceElement(vectorParts[0], 2)),
                                    normals.get(getFaceElement(vectorParts[1], 2)),
                                    normals.get(getFaceElement(vectorParts[2], 2))
                            );

                            faces.add(new ModelFace(vertexTriangle, textureCoordinatesTriangle, normalsTriangle));
                        }
                    }
                }
            }

            return faces.toArray(ModelFace[]::new);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int getFaceElement(String face, int elementInt) {
        return Integer.parseInt(face.split("/")[elementInt]) - 1;
    }
}
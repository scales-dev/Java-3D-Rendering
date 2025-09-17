package scales.github.constructors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ModelFace {
    public Triangle triangle;
    public Triangle worldPos;
    public Triangle textureCoordinates;
    public Triangle normals;

    public Triangle getPoint(int i) {
        return switch (i) {
            case 0 -> new Triangle(triangle.pointA, textureCoordinates.pointA, normals.pointA);
            case 1 -> new Triangle(triangle.pointB, textureCoordinates.pointB, normals.pointB);
            default -> new Triangle(triangle.pointC, textureCoordinates.pointC, normals.pointC);
        };
    }

    public ModelFace(Vec3 pointA, Vec3 pointB, Vec3 pointC) {
        this.triangle = new Triangle(pointA, pointB, pointC);
        this.textureCoordinates = new Triangle(Vec3.ZERO,Vec3.ZERO,Vec3.ZERO);
        this.normals = new Triangle(Vec3.ZERO,Vec3.ZERO,Vec3.ZERO);
    }

    public ModelFace(Triangle triangle, Triangle textureCoordinates, Triangle normals) {
        this.triangle = triangle;
        this.textureCoordinates = textureCoordinates;
        this.normals = normals;
    }
}

package scales.github.constructors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Triangle {
    public Vec3 pointA;
    public Vec3 pointB;
    public Vec3 pointC;

    public Triangle(Vec2 pointA, Vec2 pointB, Vec2 pointC) {
        this.pointA = new Vec3(pointA.x, pointA.y, 0);
        this.pointB = new Vec3(pointB.x, pointB.y, 0);
        this.pointC = new Vec3(pointC.x, pointC.y, 0);
    }

    public static final Triangle EMPTY = new Triangle(new Vec3(0,0,0), new Vec3(0,0,0), new Vec3(0,0,0));

    public Vec3 getPoint(int i) {
        return switch (i) {
            case 0 -> pointA;
            case 1 -> pointB;
            default -> pointC;
        };
    }

    public void setPoint(int i, Vec3 value) {
        switch (i) {
            case 0 -> pointA = value;
            case 1 -> pointB = value;
            default -> pointC = value;
        };
    }

    public double minX() {
        return Math.min(Math.min(pointA.x, pointB.x), pointC.x);
    }
    public double maxX() {
        return Math.max(Math.max(pointA.x, pointB.x), pointC.x);
    }

    public double minY() {
        return Math.min(Math.min(pointA.y, pointB.y), pointC.y);
    }
    public double maxY() {
        return Math.max(Math.max(pointA.y, pointB.y), pointC.y);
    }

    public double minZ() {
        return Math.min(Math.min(pointA.z, pointB.z), pointC.z);
    }
    public double maxZ() {
        return Math.max(Math.max(pointA.z, pointB.z), pointC.z);
    }

    public Triangle lerp(Triangle otherTriangle, double lerpVal) {
        return new Triangle(
                this.pointA.lerp(otherTriangle.pointA, lerpVal),
                this.pointB.lerp(otherTriangle.pointB, lerpVal),
                this.pointC.lerp(otherTriangle.pointC, lerpVal)
        );
    }

    private double area() {
        return 0.5 * (pointA.x * (pointB.y - pointC.y)
                + pointB.x * (pointC.y - pointA.y)
                + pointC.x * (pointA.y - pointB.y)
        );
    }

    public Triangle divide(Vec3 vec3) {
        return new Triangle(
                pointA.divide(vec3.x),
                pointB.divide(vec3.y),
                pointC.divide(vec3.z)
        );
    }

    public Triangle multiply(Vec3 vec3) {
        return new Triangle(
                pointA.multiply(vec3.x),
                pointB.multiply(vec3.y),
                pointC.multiply(vec3.z)
        );
    }

    public Vec3 sum() {
        return pointA.add(pointB).add(pointC);
    }

    public Vec3 getPointInTriangle(Vec2 point) {
        double triangleABX = new Triangle(pointA, pointB, point.toVec3()).area();
        double triangleAXC = new Triangle(pointA, point.toVec3(), pointC).area();
        double triangleXBC = new Triangle(point.toVec3(), pointB, pointC).area();

        double sumOfTriangles = triangleABX + triangleAXC + triangleXBC;
        if (sumOfTriangles <= 0) return Vec3.ZERO;

        double inverseArea = 1 / sumOfTriangles;

        return new Vec3(triangleXBC * inverseArea, triangleAXC * inverseArea, triangleABX * inverseArea);
    }

    @Override
    public String toString() {
        return "new Triangle(new Vec3(" + pointA + "), new Vec3(" + pointB + "), new Vec3(" + pointC + "));";
    }
}
package scales.github.constructors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vec3 {
    public double x;
    public double y;
    public double z;

    public static final Vec3 ZERO = new Vec3(0,0,0);
    public static final Vec3 ONE = new Vec3(1,1,1);

    // string constructor used in ModelUtil.getModelFaces()
    public Vec3(String x, String y, String z) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.z = Double.parseDouble(z);
    }

    public Vec2 toVec2() {
        return new Vec2(x, y);
    }

    public double dot(Vec3 vec3) {
        return (x * vec3.x) + (y * vec3.y) + (z * vec3.z);
    }

    public Vec3 add(int n) {
        return new Vec3(x + n, y + n, z + n);
    }

    public Vec3 add(Vec3 vec3) {
        return new Vec3(x + vec3.x, y + vec3.y, z + vec3.z);
    }

    public Vec3 subtract(Vec3 vec3) {
        return new Vec3(x - vec3.x, y - vec3.y, z - vec3.z);
    }

    public Vec3 difference(Vec3 vec3) {
        return new Vec3(vec3.x - x, vec3.y - y, vec3.z - z);
    }
    public Vec3 difference(double n) {
        return new Vec3(n - x, n - y, n - z);
    }

    public Vec3 lerp(Vec3 other, double lerpVal) {
        return new Vec3(
                x + (other.x - x) * lerpVal,
                y + (other.y - y) * lerpVal,
                z + (other.z - z) * lerpVal
        );
    }


    public Vec3 multiply(Vec3 vec3) {
        return new Vec3(x * vec3.x, y * vec3.y, z * vec3.z);
    }

    public Vec3 multiply(double n) {
        return new Vec3(x * n, y * n, z * n);
    }

    public Vec3 divide(double n) {
        return new Vec3(x / n, y / n, z / n);
    }

    public Vec3 divide(Vec3 vec3) {
        return new Vec3(x / vec3.x, y / vec3.y, z / vec3.z);
    }

    public Vec3 inverse() {
        return new Vec3(1 / x, 1 / y, 1 / z);
    }

    public Vec3 abs() {
        return new Vec3(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public double sum() {
        return x + y + z;
    }

    public Vec3 clamp(double min, double max) {
        return new Vec3(
                Math.clamp(x, min, max),
                Math.clamp(y, min, max),
                Math.clamp(z, min, max)
        );
    }

    public Vec3 normalize() {
        double length = Math.sqrt(this.dot(this));
        return length == 0 ? Vec3.ZERO : new Vec3(x/length, y/length, z/length);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " z: " + z;
    }
}

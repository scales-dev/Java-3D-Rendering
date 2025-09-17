package scales.github.constructors;

import lombok.AllArgsConstructor;

// vector containing 2 doubles.
@AllArgsConstructor
public class Vec2 {
    public double x;
    public double y;

    public static final Vec2 ZERO = new Vec2(0,0);

    // string constructor used in ModelUtil.getModelFaces()
    public Vec2(String x, String y) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }

    public Vec3 toVec3() {
        return new Vec3(x, y, 0);
    }

    public Vec2 add(Vec2 vec2) {
        return new Vec2(x + vec2.x, y + vec2.y);
    }

    public Vec2 subtract(Vec2 vec2) {
        return new Vec2(x - vec2.x, y - vec2.y);
    }

    public Vec2 multiply(double n) {
        return new Vec2(x * n, y * n);
    }

    public Vec2 multiply(Vec2 vec2) {
        return new Vec2(x * vec2.x, y * vec2.y);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }
}

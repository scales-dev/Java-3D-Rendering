package scales.github.constructors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Matrix3x3 {
    // same constructor as triangle, but different name for code readability
    public Vec3 rowA;
    public Vec3 rowB;
    public Vec3 rowC;

    public Vec3 transform(Vec3 vec3) {
        return new Vec3(
                (vec3.x * rowA.x) + (vec3.y * rowA.y) + (vec3.z * rowA.z),
                (vec3.x * rowB.x) + (vec3.y * rowB.y) + (vec3.z * rowB.z),
                (vec3.x * rowC.x) + (vec3.y * rowC.y) + (vec3.z * rowC.z)
        );
    }

    public static final Matrix3x3 EMPTY = new Matrix3x3(new Vec3(1,0,0), new Vec3(0,1,0), new Vec3(0,0,1));
}

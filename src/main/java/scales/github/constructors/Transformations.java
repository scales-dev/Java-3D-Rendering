package scales.github.constructors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Transformations {
    // same constructor as triangle and matrix3x3, but different name for code readability
    public Vec3 pos;
    public Vec3 scale;

    // objects can have 3 degrees of rotation but the camera only has 2 because people can't move their mouse in 3d
    public Vec3 rotation;
}

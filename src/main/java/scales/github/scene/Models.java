package scales.github.scene;

import lombok.AllArgsConstructor;
import scales.github.constructors.ModelFace;
import scales.github.constructors.Vec3;
import scales.github.utils.ModelUtil;

@AllArgsConstructor
public enum Models {
    SUZANNE(ModelUtil.getModelFaces("/suzanne.obj")),
    DRAGON(ModelUtil.getModelFaces("/dragon.obj")),
    CUBE(ModelUtil.getModelFaces("/cube.obj")),

    TRIANGLE(new ModelFace[] {new ModelFace(new Vec3(0, 0, 0.5), new Vec3(0.5, 1, 0.5), new Vec3(1, 0, 0.5))}),
    SQUARE(new ModelFace[] {
            new ModelFace(new Vec3(0, 0, 0), new Vec3(1, 0, 0), new Vec3(1, 1, 0)),
            new ModelFace(new Vec3(0, 1, 0), new Vec3(0, 0, 0), new Vec3(1, 1, 0)),
    });

    public final ModelFace[] modelFaces;
}

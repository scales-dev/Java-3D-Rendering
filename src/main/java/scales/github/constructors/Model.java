package scales.github.constructors;

import lombok.AllArgsConstructor;
import scales.github.scene.Textures;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class Model {
    public ModelFace[] triangles;
    public BufferedImage texture;

    public Model(ModelFace[] triangles) {
        this.triangles = triangles;
        this.texture = Textures.WHITE.texture;
    }
}

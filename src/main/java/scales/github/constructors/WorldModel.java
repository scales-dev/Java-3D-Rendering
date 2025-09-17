package scales.github.constructors;

import lombok.AllArgsConstructor;
import scales.github.scene.Models;
import scales.github.scene.Textures;

@AllArgsConstructor
public class WorldModel {
    public Model model;
    public Transformations transformations;

    public WorldModel(Models model, Transformations transformations) {
        // default texture is pure white
        this.model = new Model(model.modelFaces);
        this.transformations = transformations;
    }

    public WorldModel(Models model, Vec3 position) {
        // default texture is pure white
        this.model = new Model(model.modelFaces);
        this.transformations = new Transformations(position, Vec3.ONE, Vec3.ZERO);
    }

    public WorldModel(Models model, Textures texture, Vec3 position) {
        this.model = new Model(model.modelFaces, texture.texture);
        this.transformations = new Transformations(position, Vec3.ONE, Vec3.ZERO);
    }

    public WorldModel(Models model, Textures texture, Transformations transformations) {
        this.model = new Model(model.modelFaces, texture.texture);
        this.transformations = transformations;
    }
}

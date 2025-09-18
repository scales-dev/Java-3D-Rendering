package scales.github.scene;

import scales.github.constructors.LightSource;
import scales.github.constructors.Vec3;
import scales.github.constructors.WorldModel;

import java.util.List;

public class World {
    public static final List<LightSource> lightSources = List.of(
            new LightSource(new Vec3(-9, 0, 0), new Vec3(1, 0, 0), 30),
            new LightSource(new Vec3(0, 9, 0), new Vec3(0, 1, 0), 30),
            new LightSource(new Vec3(0, 0, 9), new Vec3(0, 0, 1), 30)
            //new LightSource(new Vec3(0, 0, 0), new Vec3(1, 1, 1), 30)
    );

    public static final List<WorldModel> worldModels = List.of(
            new WorldModel(Models.SUZANNE, new Vec3(0,0,5)),
            new WorldModel(Models.DRAGON, new Vec3(-5,0,5)),
            new WorldModel(Models.CUBE, Textures.DICE, new Vec3(5,1,0))
    );

    public static void tick() {
        long time = (System.currentTimeMillis() / 10) % 360;

        // spinning suzanne
        World.worldModels.getFirst().transformations.rotation = new Vec3(time, time, time);
    }
}

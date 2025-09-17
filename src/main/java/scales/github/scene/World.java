package scales.github.scene;

import scales.github.constructors.LightSource;
import scales.github.constructors.Transformations;
import scales.github.constructors.Vec3;
import scales.github.constructors.WorldModel;

import java.util.List;

public class World {
    public static final List<LightSource> lightSources = List.of(
            new LightSource(new Vec3(9, 0, 0), new Vec3(1, 0, 0), 30),
            new LightSource(new Vec3(0, 9, 0), new Vec3(0, 1, 0), 30),
            new LightSource(new Vec3(0, 0, 9), new Vec3(0, 0, 1), 30)
            //new LightSource(new Vec3(0, 0, 0), new Vec3(1, 1, 1), 30)
    );

    public static final List<WorldModel> worldModels = List.of(
            new WorldModel(Models.SUZANNE, new Vec3(0,0,5)),
            new WorldModel(Models.DRAGON, new Vec3(-5,0,5)),
            new WorldModel(Models.CUBE, new Vec3(5,1,0)),

            new WorldModel(Models.CUBE, Textures.DICE, new Transformations(new Vec3(0, 0, 0), new Vec3(-20, -20, -20), Vec3.ZERO))
    );

    public static void tick() {
        long time = (System.currentTimeMillis() / 10) % 360;

        // spinning suzanne
        World.worldModels.getFirst().transformations.rotation = new Vec3(time, time, time);

        // moving light sources for epic rave
        double sinOfTheTime = Math.sin(Math.toRadians(time)) * 9d;
        double cosTime = Math.cos(Math.toRadians(time)) * 9d;

        World.lightSources.getFirst().position = new Vec3(sinOfTheTime, 0, cosTime);
        World.lightSources.get(1).position = new Vec3(0, sinOfTheTime, 0);
        World.lightSources.get(2).position = new Vec3(cosTime, 0, sinOfTheTime);
    }
}

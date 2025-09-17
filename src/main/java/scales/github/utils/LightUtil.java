package scales.github.utils;

import scales.github.constructors.LightSource;
import scales.github.constructors.Vec3;
import scales.github.scene.World;

import java.awt.*;

public class LightUtil {
    public static Color getLightColour(Color colour, Vec3 worldPos, Vec3 normal) {
        //if (lightSources.length == 0) return Color.BLACK;

        Vec3 colourMultiplier = Vec3.ZERO;

        for (LightSource lightSource : World.lightSources) {
            Vec3 distance = lightSource.position.subtract(worldPos);
            Vec3 lightDirection = distance.normalize();

            double lightStrength = Math.clamp(1 - (distance.abs().sum() / lightSource.radius), 0, 1);
            double lightValue = (normal.dot(lightDirection) + 1) / 2;

            Vec3 lightColour = lightSource.colour.multiply(lightValue).multiply(lightStrength);

            colourMultiplier = colourMultiplier.add(lightColour);
        }

        // clamp between 0 and 1 in case multiple light sources overlap in spots
        colourMultiplier = colourMultiplier.clamp(0, 1);

        return new Color(
                (int) (colour.getRed() * colourMultiplier.x),
                (int) (colour.getGreen() * colourMultiplier.y),
                (int) (colour.getBlue() * colourMultiplier.z)
        );
    }
}

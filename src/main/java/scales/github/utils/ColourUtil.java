package scales.github.utils;

import scales.github.constructors.Triangle;
import scales.github.constructors.Vec2;
import scales.github.constructors.Vec3;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColourUtil {
    public static int getPixelColour(Vec3 worldPos, Vec2 textureCoordinate, Vec3 normal, BufferedImage texture) {
        int u = (int) (Math.abs(textureCoordinate.x % 1) * (texture.getWidth() - 1));
        int v = (int) ((1 - Math.abs(textureCoordinate.y % 1)) * (texture.getHeight() - 1));

        Color textureColour = new Color(texture.getRGB(u, v));

        return LightUtil.getLightColour(textureColour, worldPos, normal).getRGB();
    }
}

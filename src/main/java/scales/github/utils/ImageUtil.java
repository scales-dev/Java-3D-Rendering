package scales.github.utils;

import scales.github.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtil {
    public static BufferedImage getImage(String name) {
        try {
            return ImageIO.read(Main.class.getResourceAsStream(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage getTexture(Color colour) {
        BufferedImage texture = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
        texture.setRGB(0, 0, colour.getRGB());
        return texture;
    }
}

package scales.github.scene;

import lombok.AllArgsConstructor;
import scales.github.utils.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

@AllArgsConstructor
public enum Textures {
    WHITE(ImageUtil.getTexture(Color.WHITE)),
    DICE(ImageUtil.getImage("/dice.png"));

    public final BufferedImage texture;
}

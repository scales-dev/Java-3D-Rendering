package scales.github.utils;

import scales.github.constructors.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// C for constants!!
public class C {
    public static final String PROJECT_NAME = "3D Rendering";

    public static final JFrame FRAME = new JFrame(PROJECT_NAME);
    public static final Dimension SCREEN_BOUNDS = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension FRAME_BOUNDS = new Dimension(SCREEN_BOUNDS.width / 2, SCREEN_BOUNDS.height / 2);

    // not actually final because there isnt an easier way to reset the image than just creating a new one.
    public static BufferedImage FRAME_IMAGE = new BufferedImage(FRAME_BOUNDS.width, FRAME_BOUNDS.height, BufferedImage.TYPE_INT_RGB);

    public static final Robot ROBOT;

    // I do NOT like that this requires a try catch.
    static {
        try {
            ROBOT = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}

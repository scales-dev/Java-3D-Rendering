package scales.github.utils;

import scales.github.listeners.KeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class FrameUtil {
    public static BufferStrategy createFrame() {
        C.FRAME.addKeyListener(new KeyListener());

        C.FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        C.FRAME.setSize(C.FRAME_BOUNDS.width, C.FRAME_BOUNDS.height);
        C.FRAME.setLocation(C.SCREEN_BOUNDS.width / 2 - C.FRAME.getWidth() / 2, C.SCREEN_BOUNDS.height / 2 - C.FRAME.getHeight() / 2);

        C.FRAME.setBackground(new Color(0,0,0));
        C.FRAME.setUndecorated(true);

        C.FRAME.setVisible(true);

        C.FRAME.createBufferStrategy(2);
        return C.FRAME.getBufferStrategy();
    }
}

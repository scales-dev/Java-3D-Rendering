package scales.github;

import scales.github.constructors.Vec3;
import scales.github.scene.World;
import scales.github.utils.Camera;
import scales.github.utils.FrameUtil;
import scales.github.utils.RenderUtil;

import java.awt.image.BufferStrategy;

public class Main {
    public static void main(String[] args) {
        BufferStrategy bs = FrameUtil.createFrame();

        while (true) {
            RenderUtil.beginFrame();

            // tick movement then tick world
            Camera.tick();
            World.tick();

            RenderUtil.drawWorld();

            RenderUtil.finishFrame(bs);
        }
    }
}
package scales.github.utils;

import scales.github.listeners.KeyListener;
import scales.github.constructors.Vec2;
import scales.github.constructors.Vec3;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Camera {
    public static Vec3 pos = new Vec3(0, 0, 0);
    /// x = yaw because its used for x rotation, although y for yaw makes maybe more sense
    public static Vec2 rotation = new Vec2(0, 0);

    private static long lastTickTime = System.currentTimeMillis();

    private static final double MOVEMENT_SPEED = 0.005;
    private static final double ROTATION_SPEED = 0.1;

    public static boolean lockMouse = false;

    public static void tick() {
        lockMouse = !KeyListener.isKeyDown(KeyEvent.VK_ESCAPE);

        if (lockMouse) tickRotation();
        tickMovement();

        lastTickTime = System.currentTimeMillis();
    }

    private static void tickRotation() {
        float screenCentreX = C.SCREEN_BOUNDS.width / 3f;
        float screenCentreY = C.SCREEN_BOUNDS.height / 3f;

        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        rotation.x -= (mousePos.x - screenCentreX) * ROTATION_SPEED;
        rotation.y -= (mousePos.y - screenCentreY) * ROTATION_SPEED;

        rotation.x %= 360;
        rotation.y = Math.clamp(rotation.y, -90, 90);

        C.ROBOT.mouseMove((int) screenCentreX, (int) screenCentreY);
    }

    private static void tickMovement() {
        double forward = (KeyListener.isKeyDown(KeyEvent.VK_S) ? -1 : 0) + (KeyListener.isKeyDown(KeyEvent.VK_W) ? 1 : 0);
        double sideways = (KeyListener.isKeyDown(KeyEvent.VK_A) ? -1 : 0) + (KeyListener.isKeyDown(KeyEvent.VK_D) ? 1 : 0);
        double up = (KeyListener.isKeyDown(KeyEvent.VK_SPACE) ? 1 : 0) + (KeyListener.isKeyDown(KeyEvent.VK_SHIFT) ? -1 : 0);

        double speedMultiplier = (System.currentTimeMillis() - lastTickTime) * MOVEMENT_SPEED;

        double rotationToRadians = Math.toRadians(rotation.x);

        pos.x += (Math.sin(-rotationToRadians) * forward + Math.cos(-rotationToRadians) * sideways) * (speedMultiplier);
        pos.y += up * speedMultiplier;
        pos.z += (Math.cos(-rotationToRadians) * forward - Math.sin(-rotationToRadians) * sideways) * (speedMultiplier);
    }
}

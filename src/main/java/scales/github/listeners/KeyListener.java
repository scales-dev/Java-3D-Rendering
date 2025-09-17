package scales.github.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyListener extends KeyAdapter {
    private static final ArrayList<Integer> keysPressed = new ArrayList<>();

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!keysPressed.contains(keyEvent.getKeyCode())) {
            keysPressed.add(keyEvent.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keysPressed.remove((Integer) keyEvent.getKeyCode());
    }

    public static boolean isKeyDown(int keyCode) {
        return keysPressed.contains(keyCode);
    }
}

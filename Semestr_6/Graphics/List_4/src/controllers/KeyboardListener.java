package controllers;

import elements.PosterElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyboardListener implements KeyListener {
    private final List<Runnable> callbacks;

    private PosterElement selectedElement;
    private Runnable moveUpCallback;
    private Runnable moveDownCallback;

    public KeyboardListener() {
        this.selectedElement = null;
        this.callbacks = new ArrayList<>();
    }

    private void moveLeft() {
        selectedElement.move(-1, 0);
        runCallbacks();
    }

    private void moveRight() {
        selectedElement.move(1, 0);
        runCallbacks();
    }

    private void moveUp() {
        selectedElement.move(0, -1);
        runCallbacks();
    }

    private void moveDown() {
        selectedElement.move(0, 1);
        runCallbacks();
    }

    private void rotateLeft() {
        selectedElement.rotate(-Math.toRadians(1));
        runCallbacks();
    }

    private void rotateRight() {
        selectedElement.rotate(Math.toRadians(1));
        runCallbacks();
    }

    private void scaleUp() {
        selectedElement.scale(1.05, 1.05);
        runCallbacks();
    }

    private void scaleDown() {
        selectedElement.scale(0.95, 0.95);
        runCallbacks();
    }

    public void setSelectedElement(PosterElement element) {
        this.selectedElement = element;
    }

    public void addCallback(Runnable callback) {
        this.callbacks.add(callback);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (selectedElement == null) return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                moveRight();
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                moveUp();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                moveDown();
                break;
            case KeyEvent.VK_Q:
                rotateLeft();
                break;
            case KeyEvent.VK_E:
                rotateRight();
                break;
            case KeyEvent.VK_Z:
                scaleUp();
                break;
            case KeyEvent.VK_X:
                scaleDown();
                break;
            case KeyEvent.VK_CLOSE_BRACKET:
                if (moveUpCallback != null) {
                    moveUpCallback.run();
                }
                break;
            case KeyEvent.VK_OPEN_BRACKET:
                if (moveDownCallback != null) {
                    moveDownCallback.run();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void runCallbacks() {
        for (Runnable callback : callbacks) {
            callback.run();
        }
    }

    public void setMoveUpCallback(Runnable moveUpCallback) {
        this.moveUpCallback = moveUpCallback;
    }

    public void setMoveDownCallback(Runnable moveDownCallback) {
        this.moveDownCallback = moveDownCallback;
    }
}

package com.socket;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/* 리스너 호출용 static class */
public class EventHandling {
    public static Resize getResizeListener(EventFireGui frame) {
        return new Resize(frame);
    }

    public static Keyhandle getKeyhandleListener(JTextField inputField) {
        return new Keyhandle(inputField);
    }
}

/* frame의 resize 이벤트를 처리 */
class Resize extends ComponentAdapter {
    EventFireGui frame;

    public Resize(EventFireGui frame) {
        this.frame = frame;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println(e.getComponent().getClass());
    }
}

/* input textfield의 키 이벤트를 처리 */
class Keyhandle implements KeyListener {
    JTextField inputField;

    public Keyhandle(JTextField inputField) {
        this.inputField = inputField;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            System.out.println(inputField.getText());
        }
    }
}

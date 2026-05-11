package org.example.misc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CenteredSplash {
    private static final int FADE_STEP_MS = 20;   // шаг анимации (мс)
    private static final float FADE_STEP = 0.02f; // изменение прозрачности за шаг

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: java CenteredSplash <image-path> [--auto-close ms]");
            System.exit(1);
        }

        String imagePath = args[0];
        int autoCloseMs;
        if (args.length >= 3 && "--auto-close".equals(args[1])) {
            autoCloseMs = Integer.parseInt(args[2]);
        } else {
            autoCloseMs = -1;
        }

        // Создаём окно без рамок
        JWindow window = new JWindow();
        window.setLayout(new BorderLayout());

        // Загружаем изображение
        ImageIcon icon = new ImageIcon(imagePath);
        JLabel label = new JLabel(icon);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fadeOutAndClose(window);
            }
        });
        window.getContentPane().add(label, BorderLayout.CENTER);

        window.pack();
        window.setLocationRelativeTo(null);

        // Делаем окно полупрозрачным (0% видимости)
        setOpaqueSafe(window, 0f);
        window.setVisible(true);
        window.toFront();

        // Плавное появление
        fadeIn(window, () -> {
            // После полного появления — либо ждём авто-закрытие, либо держим открытым
            if (autoCloseMs > 0) {
                Timer closeTimer = new Timer(autoCloseMs, e -> fadeOutAndClose(window));
                closeTimer.setRepeats(false);
                closeTimer.start();
            }
        });
    }

    /**
     * Безопасно устанавливает прозрачность окна (если поддерживается)
     */
    private static void setOpaqueSafe(Window window, float opacity) {
        if (window.isDisplayable()) {
            window.setOpacity(opacity);
        }
    }

    /**
     * Плавное появление от 0 до 1
     */
    private static void fadeIn(Window window, Runnable onFinished) {
        Timer timer = new Timer(FADE_STEP_MS, null);
        timer.addActionListener(e -> {
            float opacity = window.getOpacity();
            if (opacity >= 0.99f) {
                timer.stop();
                window.setOpacity(1.0f);
                if (onFinished != null) onFinished.run();
            } else {
                window.setOpacity(Math.min(opacity + FADE_STEP, 1.0f));
            }
        });
        timer.start();
    }

    /**
     * Плавное исчезновение до 0 и закрытие окна
     */
    private static void fadeOutAndClose(Window window) {
        Timer timer = new Timer(FADE_STEP_MS, null);
        timer.addActionListener(e -> {
            float opacity = window.getOpacity();
            if (opacity <= 0.01f) {
                timer.stop();
                window.dispose();
            } else {
                window.setOpacity(Math.max(opacity - FADE_STEP, 0f));
            }
        });
        timer.start();
    }
}
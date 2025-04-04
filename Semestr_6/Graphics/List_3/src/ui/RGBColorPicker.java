package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class RGBColorPicker extends JPanel {
    private final Controller controller;

    private final JPanel colorDisplay;
    private final JTextField redField;
    private final JTextField greenField;
    private final JTextField blueField;
    private Color color;

    public RGBColorPicker(Controller controller) {
        setLayout(new BorderLayout());

        this.controller = controller;

        colorDisplay = new JPanel();
        colorDisplay.setPreferredSize(new Dimension(32, 32));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 5, 5));

        redField = createColorTextField();
        greenField = createColorTextField();
        blueField = createColorTextField();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(colorDisplay);
        mainPanel.add(new JLabel("Red:"));
        mainPanel.add(redField);
        mainPanel.add(new JLabel("Green:"));
        mainPanel.add(greenField);
        mainPanel.add(new JLabel("Blue:"));
        mainPanel.add(blueField);

        add(mainPanel, BorderLayout.CENTER);

        updateColor();
    }

    private JTextField createColorTextField() {
        JTextField textField = new JTextField("0", 5);
        textField.addKeyListener(new KeyAdapter() {
            private String lastValidValue = "0";

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    int value = Integer.parseInt(textField.getText());
                    if (value < 0 || value > 255) {
                        throw new NumberFormatException();
                    }
                    lastValidValue = textField.getText();
                    updateColor();
                } catch (NumberFormatException ex) {
                    SwingUtilities.invokeLater(() -> textField.setText(lastValidValue));
                }
            }
        });
        return textField;
    }

    private void updateColor() {
        try {
            int red = Integer.parseInt(redField.getText());
            int green = Integer.parseInt(greenField.getText());
            int blue = Integer.parseInt(blueField.getText());
            color = new Color(red, green, blue);
            controller.setColor(color);
            colorDisplay.setBackground(color);
        } catch (NumberFormatException ignored) {
        }
    }

    public Color getColor() {
        return color;
    }
}
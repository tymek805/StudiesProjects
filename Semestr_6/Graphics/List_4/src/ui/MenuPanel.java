package ui;

import elements.PosterElement;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MenuPanel extends JMenuBar {
    private final PosterPanel posterPanel;
    private Consumer<String> addImagesCallback;

    public MenuPanel(PosterPanel posterPanel) {
        this.posterPanel = posterPanel;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.LIGHT_GRAY);
        setupMenus();
    }

    private void setupMenus() {
        JMenu fileMenu = new JMenu("File");
        JMenu imagesMenu = new JMenu("Images");

        fileMenu.add(createMenuItem("Open", e -> loadElements()));
        fileMenu.add(createMenuItem("Save", e -> saveElements()));
        fileMenu.add(createMenuItem("Export", e -> exportImage()));
        fileMenu.add(createMenuItem("Exit", e -> System.exit(0)));
        imagesMenu.add(createMenuItem("Add Images", e -> addImages()));

        add(fileMenu);
        add(imagesMenu);
    }

    private JMenuItem createMenuItem(String title, ActionListener action) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(action);
        return item;
    }


    private void exportImage() {
        JDialog dialog = createDialog("Export");

        JTextField filePathField = new JTextField(20);
        JButton selectButton = new JButton("Select File");
        selectButton.addActionListener(e -> chooseExportFile(filePathField, dialog));

        JTextField widthField = createNumberField(posterPanel.getWidth());
        JTextField heightField = createNumberField(posterPanel.getHeight());

        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sizePanel.add(new JLabel("Width:"));
        sizePanel.add(widthField);
        sizePanel.add(new JLabel("Height:"));
        sizePanel.add(heightField);

        JPanel filePanel = new JPanel();
        filePanel.add(filePathField);
        filePanel.add(selectButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("OK", e -> exportToFile(dialog, filePathField.getText(), widthField, heightField)));
        buttonPanel.add(createButton("Cancel", e -> dialog.dispose()));

        dialog.add(sizePanel, BorderLayout.NORTH);
        dialog.add(filePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void chooseExportFile(JTextField filePathField, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
        fileChooser.setCurrentDirectory(new File("./."));
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (!path.toLowerCase().endsWith(".png")) path += ".png";
            filePathField.setText(path);
        }
    }

    private void exportToFile(JDialog dialog, String path, JTextField widthField, JTextField heightField) {
        if (path.isEmpty()) return;
        try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            BufferedImage image = posterPanel.getImage(width, height);
            javax.imageio.ImageIO.write(image, "png", new File(path));
            showMessage("Image saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            showMessage("Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        dialog.dispose();
    }


    public void saveElements() {
        List<PosterElement> elements = posterPanel.getPosterElements();
        if (elements == null || elements.isEmpty()) {
            showMessage("There are no elements to save.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = createFileChooser("Save Elements", "Poster Files", "poster");
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = ensureExtension(fileChooser.getSelectedFile(), ".poster");
            try (PrintWriter writer = new PrintWriter(path)) {
                for (PosterElement element : elements) {
                    writer.println(element.serialize());
                }
                showMessage("Elements saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                showMessage("Error saving elements: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void loadElements() {
        JFileChooser fileChooser = createFileChooser("Load Elements", "Poster Files", "poster");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            List<PosterElement> elements = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        PosterElement element = PosterElement.deserialize(line);
                        elements.add(element);
                    } catch (Exception ex) {
                        System.out.println("Error deserializing: " + ex.getMessage());
                    }
                }
                if (elements.isEmpty()) {
                    showMessage("No valid elements found in the file.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    posterPanel.setPosterElements(elements);
                    showMessage("Elements loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                showMessage("Error loading elements: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void addImages() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Images");
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File("./."));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif", "bmp"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            for (File file : fileChooser.getSelectedFiles()) {
                if (addImagesCallback != null) addImagesCallback.accept(file.getAbsolutePath());
                else System.out.println("Selected file: " + file.getAbsolutePath());
            }
        }
    }


    private JDialog createDialog(String title) {
        JDialog dialog = new JDialog((Frame) null, title, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);
        return dialog;
    }

    private JTextField createNumberField(int defaultValue) {
        return new JTextField(String.valueOf(defaultValue), 5);
    }

    private JFileChooser createFileChooser(String title, String desc, String ext) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(title);
        chooser.setFileFilter(new FileNameExtensionFilter(desc, ext));
        chooser.setCurrentDirectory(new File("./."));
        return chooser;
    }

    private String ensureExtension(File file, String ext) {
        String path = file.getAbsolutePath();
        return path.toLowerCase().endsWith(ext) ? path : path + ext;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void showMessage(String msg, String title, int type) {
        JOptionPane.showMessageDialog(null, msg, title, type);
    }

    public void setAddImagesCallback(Consumer<String> callback) {
        this.addImagesCallback = callback;
    }
}

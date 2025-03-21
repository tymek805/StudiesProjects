import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Utils {
    static double intensity(double d, double w) {
        double intensity = 128 * (Math.sin(Math.PI * d / w) + 1);
        return Math.max(0, Math.min(255, intensity));
    }

    static void saveToFile(RenderedImage image, String path) {
        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        try {
            ImageIO.write(image, "bmp", new File(outputDir, path));
            System.out.println("Image created successfully");
        } catch (IOException e) {
            System.out.println("The image cannot be stored");
        }
    }

    static BufferedImage imageFromPath(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("The image cannot be read");
            return null;
        }
    }
}

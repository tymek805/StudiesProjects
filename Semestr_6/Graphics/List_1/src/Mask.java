import java.awt.*;
import java.awt.image.BufferedImage;

public class Mask {

    public static void main(String[] args) {
        BufferedImage image1 = Utils.imageFromPath("pwr_logo.bmp");

        String pattern = "grid"; // [chessboard, grid, rings]
        String filename = "mask_image.bmp";

        int xRes = image1.getWidth();
        int yRes = image1.getHeight();

        BufferedImage mask = Mask.buildMask(pattern, xRes, yRes);
        BufferedImage outputImage = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < yRes; y++) {
            for (int x = 0; x < xRes; x++) {
                Color color = new Color(mask.getRGB(x, y));
                outputImage.setRGB(x, y, color.equals(Color.WHITE) ? image1.getRGB(x, y) : mask.getRGB(x, y));
            }
        }
        Utils.saveToFile(outputImage, filename);
        Utils.saveToFile(outputImage, filename);
    }

    static BufferedImage buildMask(String pattern, int xRes, int yRes) throws IllegalStateException {
        return switch (pattern) {
            case "chessboard" -> Chessboard.fromParams(xRes, yRes, 25, new Color(0, 0, 0), new Color(255, 255, 255));
            case "grid" -> Grid.fromParams(xRes, yRes, 5, 10, 10, new Color(0, 0, 0), new Color(255, 255, 255));
            case "rings" -> Rings.fromParams(xRes, yRes, 15, false);
            default -> throw new IllegalStateException("Mask could not be created, use chessboard, grid, rings");
        };
    }
}

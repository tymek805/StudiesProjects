import java.awt.Color;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

public class ProceduralPatterns {
    public static void main(String[] args) {
        int xRes = 200;
        int yRes = 200;

        BufferedImage circles = buildCircles(xRes, yRes, 15, 30);
        Utils.saveToFile(circles, "circles.bmp");

        BufferedImage rings = buildConvergingRings(xRes, yRes);
        Utils.saveToFile(rings, "converging_rings.bmp");

        BufferedImage targets = buildTargets(xRes, yRes, 75);
        Utils.saveToFile(targets, "targets.bmp");

        BufferedImage tiltedChessboard = buildTiltedChessboard(xRes, yRes, 50);
        Utils.saveToFile(tiltedChessboard, "tilted_chessboard.bmp");

        BufferedImage rays = buildRays(xRes, yRes, 11);
        Utils.saveToFile(rays, "rays.bmp");
    }

    static BufferedImage buildCircles(int xRes, int yRes, int spacing, int radius) {
        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        int gridSize = 2 * radius + spacing;
        int x_c = (xRes / 2);
        int y_c = (yRes / 2);

        for (int y = 0; y < yRes; y++) {
            for (int x = 0; x < xRes; x++) {
                int relativeY = Math.abs(y - y_c) % gridSize;
                int relativeX = Math.abs(x - x_c) % gridSize;
                if (relativeX > radius + spacing)
                    relativeX = 2 * radius + spacing - relativeX;
                if (relativeY > radius + spacing)
                    relativeY = 2 * radius + spacing - relativeY;

                double distance = Math.sqrt(Math.pow(relativeX, 2) + Math.pow(relativeY, 2));
                if (distance < radius) {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        return image;
    }

    static BufferedImage buildTiltedChessboard(int xRes, int yRes, int squareSize) {
        if (squareSize <= 1) {
            throw new IllegalArgumentException("Invalid diagonal");
        }

        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);
        int x_c = (xRes / 2);
        int y_c = (yRes / 2);

        for (int y = 0; y < yRes; y++) {
            for (int x = 0; x < xRes; x++) {
                int relativeX = Math.abs(x_c - x) % squareSize;
                int relativeY = Math.abs(y_c - y) % squareSize;
                if (relativeX > squareSize / 2) {
                    relativeX = squareSize - relativeX;
                }
                if (relativeY > squareSize / 2) {
                    relativeY = squareSize - relativeY;
                }
                if (relativeX > relativeY) {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        return image;
    }

    static BufferedImage buildConvergingRings(int xRes, int yRes) {
        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);
        int x_c = xRes / 2;
        int y_c = yRes / 2;

        double minWidth = 1.0;
        double maxWidth = 10.0;
        double growthFactor = 0.005;

        for (int y = 0; y < yRes; y++) {
            for (int x = 0; x < xRes; x++) {
                double d = Math.sqrt((y - y_c) * (y - y_c) + (x - x_c) * (x - x_c));
                double width = minWidth + (maxWidth - minWidth) * (1 - Math.exp(-growthFactor * d));
                int r = (int) (d / width);
                if (r % 2 == 0) {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        return image;
    }

    static BufferedImage buildTargets(int xRes, int yRes, int size) {
        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);
        int x_c = xRes / 2;
        int y_c = yRes / 2;
        if (size < 10) {
            throw new InvalidParameterException("Size must be at least 10");
        }
        int width = size / 10;
        for (int y = 0; y < yRes; y++) {
            for (int x = 0; x < xRes; x++) {
                int relativeY = Math.abs(y - y_c) % size;
                int relativeX = Math.abs(x - x_c) % size;
                if (relativeX > size / 2)
                    relativeX = size - relativeX;
                if (relativeY > size / 2)
                    relativeY = size - relativeY;
                double distance = Math.sqrt(Math.pow(relativeX, 2) + Math.pow(relativeY, 2));

                int r = (int) distance / width;

                if (r % 2 == 0) {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                }

            }
        }

        return image;
    }

    static BufferedImage buildRays(int xRes, int yRes, int count) {
        if (count < 2) {
            throw new InvalidParameterException("Count must be at least 2");
        }

        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);
        int x_c = xRes / 2;
        int y_c = yRes / 2;
        int changeAngle = (int) 360 / count;
        for (int y = 0; y < yRes; y++) {
            for (int x = 0; x < xRes; x++) {
                double angleDegrees = Math.atan2(y_c - y, x_c - x) * 180 / Math.PI;
                if (angleDegrees < 0) {
                    angleDegrees += 360;
                }
                if ((int) angleDegrees / changeAngle % 2 == 0) {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        return image;
    }
}

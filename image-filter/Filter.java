import java.awt.Color;

/**
 * A class that contains static methods on ImageManager objects
 * to apply simple filters to objects
 */
class Filter {

    /**
     * Identity filter. Keeps every pixel the same color.
     */
    public static void identical(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                image.setColor(x, y, image.getColor(x, y));
            }
        }

        image.write();
    }

    /**
     * Make each pixel brigter, using java.awt.Color.brighter
     */
    public static void brighter(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                image.setColor(x, y, image.getColor(x, y).brighter());
            }
        }

        image.write();
    }

    /**
     * Make each pixel darker, using java.awt.Color.darker
     */
    public static void darker(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                image.setColor(x, y, image.getColor(x, y).darker());
            }
        }

        image.write();
    }

    /**
     * Boost the red level of each pixel
     */
    public static void redder(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = image.getColor(x, y);
                image.setColor(
                    x,
                    y,
                    new Color(
                        Math.min(c.getRed() + 20, 255),
                        c.getGreen(),
                        c.getBlue()
                    )
                );
            }
        }

        image.write();
    }

    /**
     * Boost the green level of each pixel
     */
    public static void greener(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = image.getColor(x, y);
                image.setColor(
                    x,
                    y,
                    new Color(
                        c.getRed(),
                        Math.min(c.getGreen() + 20, 255),
                        c.getBlue()
                    )
                );
            }
        }

        image.write();
    }

    /**
     * Boost the blue level of each pixel
     */
    public static void bluer(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = image.getColor(x, y);
                image.setColor(
                    x,
                    y,
                    new Color(
                        c.getRed(),
                        c.getGreen(),
                        Math.min(c.getBlue() + 20, 255)
                    )
                );
            }
        }

        image.write();
    }

    /**
     * Re-expose the image on a log-scale of brightness
     *  i.e. 3-6 is a bigger difference than 100-103. Outputs greyscale
     *  but is faster than logExposure.
     */
    public static void logExposureGreyscale(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = image.getColor(x, y);
                double exposure = (
                        c.getRed() + c.getGreen() + c.getBlue()
                        ) / 3.0 / 255.0;
                int logExposure = (int)(Math.sqrt(exposure) * 255);
                image.setColor(
                    x,
                    y,
                    new Color(
                        logExposure,
                        logExposure,
                        logExposure
                    )
                );
            }
        }

        image.write();
    }

    // Helper function for logExposure
    public static int logExposeColor(int colorValue) {
        double exposure = colorValue / 255.0;
        int logExposure = (int) (Math.sqrt(exposure) * 255);
        return logExposure;
    }

    /**
     * Re-expose the image on a log-scale of brightness
     *  i.e. 3-6 is a bigger difference than 100-103. Slower than
     *  logExposureGreyscale but preserves color. This still needs work
     *  to de-emphasize the greens, which is more exaggerated to the human eye.
     */
    public static void logExposure(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = image.getColor(x, y);
                image.setColor(
                    x,
                    y,
                    new Color(
                        Filter.logExposeColor(c.getRed()),
                        Filter.logExposeColor(c.getGreen()),
                        Filter.logExposeColor(c.getBlue())
                    )
                );
            }
        }

        image.write();
    }

    /**
     * Flatten each RGB value to be either on or off. Full contrast.
     */
    public static void contrasty(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = image.getColor(x, y);
                image.setColor(
                    x,
                    y,
                    new Color(
                        c.getRed() < 127 ? 0 : 255,
                        c.getGreen() < 127 ? 0 : 255,
                        c.getBlue() < 127 ? 0 : 255
                    )
                );
            }
        }

        image.write();
    }

    /**
     * Saturate or de-saturate the image by a given amount.
     *
     * 0 is complete greyscale
     * 1 is normal
     * > 1 is saturated / boosted
     */
    public static void saturate(ImageManager image, double amount) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = image.getColor(x, y);
                double red = c.getRed();
                double green = c.getGreen();
                double blue = c.getBlue();
                double average = (red + green + blue) / 3;

                int newRed = (int) Math.min(average + (red - average) * amount, 255);
                int newGreen = (int) Math.min(average + (green - average) * amount, 255);
                int newBlue = (int) Math.min(average + (blue - average) * amount, 255);

                image.setColor(
                    x,
                    y,
                    new Color(
                        Math.max(newRed, 0),
                        Math.max(newGreen, 0),
                        Math.max(newBlue, 0)
                    )
                );
            }
        }

        image.write();
    }

    /**
     * Average each pixel's RGB value to make the image greyscale on mean
     */
    public static void greyscale(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = image.getColor(x, y);
                int average = (int) (
                    c.getRed()
                    + c.getGreen()
                    + c.getBlue()
                ) / 3;
                image.setColor(
                    x,
                    y,
                    new Color(
                        average,
                        average,
                        average
                    )
                );
            }
        }

        image.write();
    }

    /**
     * Average each pixel's RGB value to make the image greyscale on geometric mean
     */
    public static void greyscaleGeom(ImageManager image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = image.getColor(x, y);
                int average = (int) Math.pow(
                    c.getRed()
                    * c.getGreen()
                    * c.getBlue(),
                    1.0 / 3.0
                );
                image.setColor(
                    x,
                    y,
                    new Color(
                        average,
                        average,
                        average
                    )
                );
            }
        }

        image.write();
    }

    /**
     * Translate the image in the 2D plane by given pixels
     */
    public static void translate(ImageManager image, int horiz, int vert) {
        int width = image.getWidth();
        int height = image.getHeight();

        // make a temporary clone source
        ImageManager source = image.clone("x.jpg");

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = source.getColor(x - horiz, y - vert);
                image.setColor(x, y, c);
            }
        }

        image.write();
    }

    /**
     * Rotate the image clockwise by [degrees] degrees, about the center point
     *
     * Using:
     * newX = x cos(t) - y sin(t)
     * newY = x sin(t) + y cos(t)
     */
    public static void rotate(ImageManager image, double degrees) {
        int width = image.getWidth();
        int height = image.getHeight();
        int ww = width / 2;
        int hh = height / 2;
        double radians = -1.0 * degrees * Math.PI / 180;

        double cosine = Math.cos(radians);
        double sine = Math.sin(radians);

        // make a temporary clone source
        ImageManager source = image.clone("x.jpg");

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                int newX = (int) (((x - ww) * cosine - (y - hh) * sine) + ww);
                int newY = (int) (((x - ww) * sine + (y - hh) * cosine) + hh);

                Color c = source.getColor(newX, newY);
                image.setColor(x, y, c);
            }
        }

        image.write();
    }

    /**
     * Scale the image by a given factor around the center. 
     */
    public static void scale(ImageManager image, double factor) {
        int width = image.getWidth();
        int height = image.getHeight();

        int ww = width / 2;
        int hh = height / 2;

        // make a temporary clone source
        ImageManager source = image.clone("x.jpg");

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                Color c = source.getColor((int) ((x - ww) / factor) + ww, (int) ((y - hh) / factor) + hh);
                image.setColor(x, y, c);
            }
        }

        image.write();
    }

}

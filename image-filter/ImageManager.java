import java.io.File;
import java.io.IOException;

// to hold all the bytes
import java.awt.image.BufferedImage;
// to use colors without doing fancy math
import java.awt.Color;
// to perform read and write operations
import javax.imageio.ImageIO;

/**
 * A class that acts as an abstration for an image file,
 * that allows you to forget the actual image file and
 * simply adjust pixel color values and get size.
 */
class ImageManager {

    private int width;
    private int height;
    
    private boolean writable;

    private String filePath;
    private File file;
    private BufferedImage image;

    /**
     * Readable / source image
     */
    public ImageManager(String path) {
        this.filePath = path;

        this.writable = false;

        try {
            this.file = new File(this.filePath);
            this.image = ImageIO.read(this.file);

            this.height = image.getHeight();
            this.width = image.getWidth();
        } catch (IOException e) {
            this.height = 0;
            this.width = 0;

            System.out.println("Error: Couldn't read file correctly!" + e.toString());
        }
    }

    /**
     * Readable AND Writable / target image
     */
    public ImageManager(String path, int width, int height) {
        this.width = width;
        this.height = height;
        this.filePath = path;

        this.writable = true;

        this.file = new File(this.filePath);
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Make a eadable AND Writable / target image, cloned from a source image
     */
    public ImageManager clone(String path) {
        ImageManager clone = new ImageManager(path, this.getWidth(), this.getHeight());

        int width = clone.getWidth();
        int height = clone.getHeight();

        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y ++) {
                clone.setColor(x, y, this.getColor(x, y));
            }
        }

        return clone;
    }

    /**
     * Get the Color value at a given width/height
     */
    public Color getColor(int width, int height) {
        if (
            width >= this.width
            || height >= this.height
            || width < 0
            || height < 0
        ) {
            return new Color(0, 0, 0);
        }

        return new Color(this.image.getRGB(width, height));
    }

    /**
     * Set the pixel value at an image to be a Color value
     */
    public void setColor(int width, int height, Color c) {
        if (
            width >= this.width
            || height >= this.height
            || width < 0
            || height < 0
        ) {
            System.out.println("Error: attempted to setColor outside of the image bounds.");
            System.out.println("Erroneous coordinates: " + width + ", " + height);
        }

        if (!this.writable) {
            System.out.println("Error: attempted to setColor to a writable file.");
        }

        this.image.setRGB(width, height, c.getRGB());
    }

    /**
     * Get the width in pixels
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height in pixels
     */
    public int getHeight() {
        return height;
    }

    /**
     * Write all the pending changes to the image to the image's file.
     */
    public void write() {
        try {
            ImageIO.write(this.image, "jpg", this.file);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

}

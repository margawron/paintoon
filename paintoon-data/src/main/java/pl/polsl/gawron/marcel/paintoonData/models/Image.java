package pl.polsl.gawron.marcel.paintoonData.models;


import java.util.Arrays;

/**
 * Class storing image created by user
 * <p>Bitmap is stored in BGR memory layout (Blue,Green,Red)</p>
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class Image {
    private byte[] bitmap;
    private int size;

    /**
     * Parametrized constructor
     * creates bitmap size*size*3 (BGR)
     *
     * @param size size of the image
     */
    public Image(int size) {
        if (size <= 0) {
            throw new NegativeArraySizeException();
        }
        bitmap = new byte[3 * size * size];
        Arrays.fill(bitmap, (byte) 0b11111111);
        this.size = size;
    }

    /**
     * Debug function should not be used
     * Sets pixel to a certain color
     *
     * @param x     x coordinate of a pixel
     * @param y     y coordinate of a pixel
     * @param color compounded color components
     */
    public void setPixel(int x, int y, Color color) {
        int index = 3 * (x + size * y);
        for (var component : color) {
            bitmap[index] = component;
            ++index;
        }
    }

    /**
     * Getter for size of an image
     *
     * @return size of an image
     */
    public int getSize() {
        return size;
    }

    /**
     * Getter for byte array of an image
     *
     * @return byte array of an image
     */
    public byte[] getBitmap() {
        return bitmap;
    }

    /**
     * Gets color of a single pixel
     *
     * @param x horizontal positon of a pixel
     * @param y vertical position of a pixel
     * @return color components of a pixel
     */
    public Color getPixelColor(int x, int y) {
        Color color = new Color();
        int index = 3 * (x + size * y);
        // BGR pixel component memory layout
        color.setBlue(bitmap[index]);
        color.setGreen(bitmap[index + 1]);
        color.setRed(bitmap[index + 2]);
        return color;
    }
}

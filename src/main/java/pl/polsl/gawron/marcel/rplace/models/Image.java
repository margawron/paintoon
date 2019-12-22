package pl.polsl.gawron.marcel.rplace.models;

import java.util.ArrayList;
import java.util.List;

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
    private List<PixelHistory> history;
    /**
     * Default constructor
     * Creates bitmap 1000*1000*3 (BGR)
     */
    public Image() {
        bitmap = new byte[3 * 1000 * 1000];
        for (int i = 0; i < bitmap.length; i++) {
            bitmap[i] = (byte) 0b11111111;
        }
        size = 1000;
        //history = new ArrayList<>();
    }

    /**
     * Parametrized constructor
     * creates bitmap size*size*3 (BGR)
     *
     * @param size
     */
    public Image(int size) {
        if(size <= 0){
            throw new NegativeArraySizeException();
        }
        bitmap = new byte[3 * size * size];
        for (int i = 0; i < bitmap.length; i++) {
            bitmap[i] = (byte) 0b11111111;
        }
        this.size = size;
        //history = new ArrayList<>();
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
        for(var component: color){
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
     * @param x horizontal positon of a pixel
     * @param y vertical position of a pixel
     * @return color components of a pixel
     */
    public Color getPixelColor(int x, int y){
        Color color = new Color();
        int index= 3 * (x + size*y);
        // BGR pixel component memory layout
        color.setBlue(bitmap[index]);
        color.setGreen(bitmap[index+1]);
        color.setRed(bitmap[index+2]);
        return color;
    }
}

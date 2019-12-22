package pl.polsl.gawron.marcel.rplace.controllers;

import pl.polsl.gawron.marcel.rplace.models.Color;
import pl.polsl.gawron.marcel.rplace.models.Image;
import pl.polsl.gawron.marcel.rplace.views.ImageView;

/**
 * Controller for a bitmap
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ImageController {

    private ImageView imageView;
    private Image image;

    /**
     * Constructs controller with image of a size 100
     */
    public ImageController() {
        this.image = new Image(100);
        this.imageView = new ImageView();
    }

    /**
     * Constructs controller with user provided Image
     *
     * @param image user provided image
     */
    public ImageController(Image image) {
        this.image = image;
        this.imageView = new ImageView();
    }

    /**
     * Wrapper around Image setPixel model
     * 0,0 position of a pixel begins in left top corner
     *
     * @param xPos  horizontal position of a pixel
     * @param yPos  vertical position of a pixel
     * @param red   red component of a pixel
     * @param green green component of a pixel
     * @param blue  blue component of a pixel
     */
    public void setPixel(int xPos, int yPos, byte red, byte green, byte blue) {
        image.setPixel(xPos, yPos, new Color(red, green, blue));
    }

    /**
     * Wrapper around Image model method setPixel
     * with compounded color components into Color class
     *
     * @param xPos  horizontal position of a pixel
     * @param yPos  vertical position of a pixel
     * @param color compounded pixel components into Color class
     */
    public void setPixel(int xPos, int yPos, Color color) {
        image.setPixel(xPos, yPos, color);
    }

    /**
     * Wrapper around Image model method setPixel
     * with compounded color components into Color class
     * and view update
     *
     * @param xPos  horizontal position of a pixel
     * @param yPos  vertical position of a pixel
     * @param color compounded pixel components into Color class
     */
    public void setPixelAndUpdate(int xPos, int yPos, Color color) {
        image.setPixel(xPos, yPos, color);
        updateBuffer();
    }

    /**
     * Update imageView bufferedImage object (improves performance)
     */
    public void updateBuffer() {
        imageView.fromByteArrayToBufferedImage(image);
    }

    /**
     * Saves image as file
     * <b>Debug only</b>
     *
     * @param filename file name of image
     */
    @Deprecated
    public void saveAsFile(String filename) {
        updateBuffer();
        imageView.saveAsImage(filename);
    }
}
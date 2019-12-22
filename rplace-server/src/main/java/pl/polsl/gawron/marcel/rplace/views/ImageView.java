package pl.polsl.gawron.marcel.rplace.views;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import pl.polsl.gawron.marcel.rplace.models.Image;

/**
 * Class for representing Image as Bitmap
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ImageView {

    private BufferedImage bufferedImage;

    /**
     * Default constructor
     */
    public ImageView() {
    }

    /**
     * Convert raw array to image
     *
     * @return image created by users
     */
    public BufferedImage fromByteArrayToBufferedImage(Image image) {
        bufferedImage = new BufferedImage(image.getSize(), image.getSize(), BufferedImage.TYPE_3BYTE_BGR);
        bufferedImage.setData(Raster.createRaster(bufferedImage.getSampleModel(), new DataBufferByte(image.getBitmap(), 3 * image.getSize() * image.getSize()), new Point()));

        return bufferedImage;
    }

    public BufferedImage getImage() {
        return bufferedImage;
    }

    /**
     * Saves image as png file with provided filename
     * Debug only/Deprecated
     *
     * @param filename name of a saved image
     */
    @Deprecated
    public void saveAsImage(String filename) {
        File file = new File(filename + ".png");
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves image as png file with provided filename
     * Debug only/Deprecated
     *
     * @param filename name of a saved image
     * @param image    buffered image object of a image
     */
    @Deprecated
    public void saveAsImage(String filename, BufferedImage image) {
        File file = new File(filename + ".png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

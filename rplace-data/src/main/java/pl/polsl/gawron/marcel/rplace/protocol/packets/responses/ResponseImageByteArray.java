package pl.polsl.gawron.marcel.rplace.protocol.packets.responses;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.Serializable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

/**
 * Representation of a image byte array request
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ResponseImageByteArray implements Serializable {
    private byte[] bitmapByteArray;
    private int size;

    /**
     * Get image byte array
     * need to be converted to BufferedImage
     *
     * @return image in byte array form
     */
    public byte[] getBitmapByteArray() {
        return bitmapByteArray;
    }

    /**
     * Setter for image byte array
     *
     * @param bitmapByteArray image in byte array form
     */
    public void setBitmapByteArray(byte[] bitmapByteArray) {
        this.bitmapByteArray = bitmapByteArray;
    }

    /**
     * Getter for a size of image (image is a square)
     *
     * @return size of one side from square image
     */
    public int getSize() {
        return size;
    }

    /**
     * Setter for size of image (image is a square)
     * @param size new size of image
     */
    public void setSize(int size){
        this.size = size;
    }
    /**
     * Get BufferedImage directly from byte array
     *
     * @return image stored in BufferedImage object
     */
    public BufferedImage getBufferedImage() {
        BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_3BYTE_BGR);
        bufferedImage.setData(Raster.createRaster(bufferedImage.getSampleModel(), new DataBufferByte(bitmapByteArray, 3 * size * size), new Point()));
        return bufferedImage;
    }

    /**
     * Serializes class object to JSON
     *
     * @return JSON class representation
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, ResponseImageByteArray.class);
    }

    /**
     * Deserializes class object from JSON
     *
     * @param json JSON class representation
     * @return class instance
     */
    public static ResponseImageByteArray deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ResponseImageByteArray.class);
    }
}

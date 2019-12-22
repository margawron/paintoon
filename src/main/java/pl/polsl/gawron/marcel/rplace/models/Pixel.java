package pl.polsl.gawron.marcel.rplace.models;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Class representing a single pixel
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class Pixel {
    private byte componentRed;
    private byte componentGreen;
    private byte componentBlue;
    private User currentlyModifiedBy;
    private Calendar timeOfLastModification;

    /**
     * Default constructor
     *
     * Sets pixel values to 255 (unsigned)
     * so they become white
     */
    public Pixel() {
        componentRed = Byte.MIN_VALUE;
        componentGreen = Byte.MIN_VALUE;
        componentBlue = Byte.MIN_VALUE;
        currentlyModifiedBy = null;
        timeOfLastModification = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    }

    /**
     * Setter for pixel color values
     * @param red red component of a pixel
     * @param green green component of a pixel
     * @param blue blue component of a pixel
     */
    public void setPixelColor(byte red, byte green, byte blue) {
        componentRed = red;
        componentGreen = green;
        componentBlue = blue;
    }

    /**
     * Returns RGB array of pixel colors
     * array is size of 3 and colors - from 0 to 2 are RGB
     * @return pixel color components 0 - R, 1 - G, 2 - B
     */
    public byte[] getPixelColor() {
        byte[] rgb = new byte[3];
        rgb[0] = componentRed;
        rgb[1] = componentGreen;
        rgb[2] = componentBlue;
        return rgb;
    }

    /**
     * Get latest user who modified the pixel
     * @return user who was the latest to modify the pixel
     */
    public User getCurrentlyModifiedBy() {
        return currentlyModifiedBy;
    }

    /**
     * Set the new user who modified the pixel
     * @param user new user who modified the pixel
     */
    public void setCurrentlyModifiedBy(User user) {
        currentlyModifiedBy = user;
    }

    /**
     * Getter for time of last modification
     * @return time of last modification
     */
    public Calendar getTimeOfLastModification() {
        return timeOfLastModification;
    }

    /**
     * Setter for the time of last modification
     * @param time new time of last modification
     */
    public void setTimeOfLastModification(Calendar time) {
        timeOfLastModification = time;
    }
}

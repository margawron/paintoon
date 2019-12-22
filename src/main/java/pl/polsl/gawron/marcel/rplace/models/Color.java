package pl.polsl.gawron.marcel.rplace.models;

/**
 * POJO for storing single color
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class Color {

    private byte red;
    private byte green;
    private byte blue;

    /**
     * Default constructor
     * sets color to white
     */
    public Color() {
        // Necessary casts
        // despite the value -128 still fits
        // byte primitive (0b1111_1111)
        this.red = (byte) -128;
        this.green = (byte) -128;
        this.blue = (byte) -128;
    }

    /**
     * Constructs color object with provided values
     * <b>Values are casted to a byte to avoid constant casting</b>
     *
     * @param red   red component
     * @param green green component
     * @param blue  blue component
     */
    public Color(int red, int green, int blue) {
        this.red = (byte) red;
        this.green = (byte) green;
        this.blue = (byte) blue;
    }

    /**
     * Group setter for all the components
     *
     * @param red   red component
     * @param green green component
     * @param blue  blue component
     */
    public void setColor(int red, int green, int blue) {
        this.red = (byte) red;
        this.green = (byte) green;
        this.blue = (byte) blue;
    }

    /**
     * Getter for color
     *
     * @return RGB 3 sized array with color components
     */
    public byte[] getColor() {
        byte[] rgb = new byte[3];
        rgb[0] = red;
        rgb[1] = green;
        rgb[2] = blue;
        return rgb;
    }

    /**
     * Getter for singular color component
     *
     * @return red component
     */
    public byte getRed() {
        return red;
    }

    /**
     * Setter for singular color component
     *
     * @param red new red component value
     */
    public void setRed(byte red) {
        this.red = red;
    }

    /**
     * Getter for singular color component
     *
     * @return green component
     */
    public byte getGreen() {
        return green;
    }

    /**
     * Setter for singular color component
     *
     * @param green new green component value
     */
    public void setGreen(byte green) {
        this.green = green;
    }

    /**
     * Getter for singular color component
     *
     * @return blue component
     */
    public byte getBlue() {
        return blue;
    }

    /**
     * Setter for singular color component
     *
     * @param blue new blue component value
     */
    public void setBlue(byte blue) {
        this.blue = blue;
    }
}

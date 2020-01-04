package pl.polsl.gawron.marcel.rplaceServer.models;

/**
 * Model for set pixel client request
 * @author Marcel Gawron
 * @version 1.0
 */
public class SetPixelModel {
    private int x;
    private int y;
    private int red;
    private int green;
    private int blue;

    /**
     * Getter for the x coordinate of the pixel
     * @return x coordinate of the pixel
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for the x coordinate of the pixel
     * @param x coordinate of the pixel
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for the y coordinate of the pixel
     * @return y coordinate of the pixel
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for the y coordinate of the pixel
     * @param y coordinate of the pixel
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter for the red component of the pixel
     * @return red compoment of the pixel
     */
    public int getRed() {
        return red;
    }

    /**
     * Setter for the red component of the pixel
     * @param red component of the pixel
     */
    public void setRed(int red) {
        this.red = red;
    }

    /**
     * Getter for the red component of the pixel
     * @return green component of the pixel
     */
    public int getGreen() {
        return green;
    }

    /**
     * Setter for the red component of the pixel
     * @param green component of the pixel
     */
    public void setGreen(int green) {
        this.green = green;
    }

    /**
     * Getter for the red component of the pixel
     * @return blue component of the pixel
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Setter for the red component of the pixel
     * @param blue component of the pixel
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }
}

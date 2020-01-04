package pl.polsl.gawron.marcel.rplaceServer.models;


/**
 * Message model for WebSocket broadcast connection
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class Message {
    private String username;
    private int x;
    private int y;
    private int red;
    private int green;
    private int blue;

    /**
     * Default constructor
     */
    public Message() {

    }

    /**
     * Getter for the username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username
     *
     * @param username new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the x coordinate
     *
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for the x coordinate
     *
     * @param x new x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for the y coordinate
     *
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for the y coordinate
     *
     * @param y new y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter for the red color component
     *
     * @return red color component
     */
    public int getRed() {
        return red;
    }

    /**
     * Setter for the red color component
     *
     * @param red new red color component
     */
    public void setRed(int red) {
        this.red = red;
    }

    /**
     * Getter for the green color component
     *
     * @return green color component
     */
    public int getGreen() {
        return green;
    }

    /**
     * Setter for the green color component
     *
     * @param green new green color component
     */
    public void setGreen(int green) {
        this.green = green;
    }

    /**
     * Getter for the blue color component
     *
     * @return blue color component
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Setter for the blue color component
     *
     * @param blue new blue color component
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }
}

package pl.polsl.gawron.marcel.rplace.models;

import java.time.LocalDateTime;

/**
 * Class representing a single pixel history
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class HistoryEntry {
    private int x;
    private int y;
    private Color color;
    private User userWhoModifiedPixel;
    private LocalDateTime timeOfModification;

    /**
     * Default constructor
     */
    public HistoryEntry() {
    }

    /**
     * Getter for horizontal position of pixel
     *
     * @return horizontal position of pixel
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for horizontal position of pixel
     *
     * @param x horizontal position of pixel
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for vertical position of pixel
     *
     * @return vertical position of pixel
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for vertical position of pixel
     *
     * @param y vertical position of pixel
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter of pixel color
     *
     * @return color of pixel
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for pixel color
     *
     * @param color new pixel color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter of user who modified the pixel
     *
     * @return user instance who modified the pixel
     */
    public User getUserWhoModifiedPixel() {
        return userWhoModifiedPixel;
    }

    /**
     * Setter for user who modified the pixel
     *
     * @param userWhoModifiedPixel user who modified the pixel
     */
    public void setUserWhoModifiedPixel(User userWhoModifiedPixel) {
        this.userWhoModifiedPixel = userWhoModifiedPixel;
    }

    /**
     * Getter for time of pixel modification
     *
     * @return time of pixel modification
     */
    public LocalDateTime getTimeOfModification() {
        return timeOfModification;
    }

    /**
     * Setter for pixel time of modification
     *
     * @param timeOfModification pixel time of modification
     */
    public void setTimeOfModification(LocalDateTime timeOfModification) {
        this.timeOfModification = timeOfModification;
    }
}

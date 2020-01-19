package pl.polsl.gawron.marcel.rplaceData.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class representing a single pixel history
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Entity
@Table(name = "historyEntries", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "timeOfModification"})
})
public class HistoryEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false)
    private long id;
    private int x;
    private int y;
    @Column(name = "red")
    private byte redComponent;
    @Column(name = "green")
    private byte greenComponent;
    @Column(name = "blue")
    private byte blueComponent;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User userWhoModifiedPixel;
    private LocalDateTime timeOfModification;

    /**
     * Default constructor
     */
    public HistoryEntry() {
    }

    /**
     * Getter for id field
     *
     * @return id field of an object
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the id field
     *
     * @param id id that should be set
     */
    public void setId(long id) {
        this.id = id;
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
     * Getter for the red component of a pixel
     *
     * @return red component of a pixel
     */
    public byte getRedComponent() {
        return redComponent;
    }

    /**
     * Setter for the red component of a pixel
     *
     * @param redComponent new red component
     */
    public void setRedComponent(int redComponent) {
        this.redComponent = (byte) redComponent;
    }

    /**
     * Getter for the green component of a pixel
     *
     * @return green component of a pixel
     */
    public byte getGreenComponent() {
        return greenComponent;
    }

    /**
     * Setter for the green component of a pixel
     *
     * @param greenComponent new green component
     */
    public void setGreenComponent(int greenComponent) {
        this.greenComponent = (byte) greenComponent;
    }

    /**
     * Getter for the blue component of a pixel
     *
     * @return blue component of a pixel
     */
    public byte getBlueComponent() {
        return blueComponent;
    }

    /**
     * Setter for the blue component of a pixel
     *
     * @param blueComponent new blue component
     */
    public void setBlueComponent(int blueComponent) {
        this.blueComponent = (byte) blueComponent;
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

package pl.polsl.gawron.marcel.rplaceServer.models;

import pl.polsl.gawron.marcel.rplaceData.models.HistoryEntry;

import java.time.LocalDateTime;

/**
 * History entry response model class
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class HistoryEntryResponseModel {

    private long id;
    private int x;
    private int y;
    private int redComponent;
    private int greenComponent;
    private int blueComponent;
    private long userId;
    private String userName;
    private LocalDateTime timeOfModification;


    /**
     * Constructor for creating history response model from history entry
     *
     * @param historyEntry the history entry
     */
    public HistoryEntryResponseModel(HistoryEntry historyEntry){
        this.id = historyEntry.getId();
        this.x = historyEntry.getX();
        this.y = historyEntry.getY();
        this.redComponent = historyEntry.getRedComponent();
        this.greenComponent = historyEntry.getGreenComponent();
        this.blueComponent = historyEntry.getBlueComponent();
        this.userId = historyEntry.getUserWhoModifiedPixel().getId();
        this.userName = historyEntry.getUserWhoModifiedPixel().getName();
        this.timeOfModification = historyEntry.getTimeOfModification();
    }

    /**
     * Getter for history entry id field
     *
     * @return history entry id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for id field
     *
     * @param id new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for x coord
     *
     * @return x coord
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for x coord
     *
     * @param x new x coord
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for y coord
     *
     * @return y coord
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for y coord
     *
     * @param y new y coord
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter for the red component
     *
     * @return red component
     */
    public int getRedComponent() {
        return redComponent;
    }

    /**
     * Setter for the red component
     *
     * @param redComponent new red component
     */
    public void setRedComponent(int redComponent) {
        this.redComponent = redComponent;
    }

    /**
     * Getter for the green component
     *
     * @return green component
     */
    public int getGreenComponent() {
        return greenComponent;
    }

    /**
     * Setter for the green component
     *
     * @param greenComponent new red component
     */
    public void setGreenComponent(int greenComponent) {
        this.greenComponent = greenComponent;
    }

    /**
     * Getter for the blue component
     *
     * @return blue component
     */
    public int getBlueComponent() {
        return blueComponent;
    }

    /**
     * Setter for the blue component
     *
     * @param blueComponent new red component
     */
    public void setBlueComponent(int blueComponent) {
        this.blueComponent = blueComponent;
    }

    /**
     * Getter for the user id field
     *
     * @return user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Setter for the user id
     *
     * @param userId new user id field
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Getter for the user name
     *
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the user name
     *
     * @param userName new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    /**
     * Gets time of modification.
     *
     * @return the time of modification
     */
    public LocalDateTime getTimeOfModification() {
        return timeOfModification;
    }


    /**
     * Sets time of modification.
     *
     * @param timeOfModification the time of modification
     */
    public void setTimeOfModification(LocalDateTime timeOfModification) {
        this.timeOfModification = timeOfModification;
    }
}

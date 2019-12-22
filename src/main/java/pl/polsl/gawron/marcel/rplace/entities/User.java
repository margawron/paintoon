package pl.polsl.gawron.marcel.rplace.entities;

import pl.polsl.gawron.marcel.rplace.entities.HistoryEntry;

import java.util.List;
import java.util.Random;

/**
 * Class representing single user of an application
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class User {
    private Long id;
    private String name;
    private String password;
    private List<HistoryEntry> userPixelModificationHistory;

    /**
     * Default class constructor
     * randomizes Id
     * sets name as undefined
     */
    public User() {
        this.id = new Random().nextLong();
        this.name = "undefined";
    }

    /**
     * Constructor with name parameter
     *
     * @param name name to be set as member value name
     */
    public User(String name){
        this();
        setName(name);
    }

    /**
     * Getter for ID of a user
     * @return user id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the user ID
     * @param id new user ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for username
     * @return username
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for username
     * @param name new username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for user password
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for user password
     * @param password new user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter of user pixel modification history
     * @return List of user pixel modification history
     */
    public List<HistoryEntry> getUserPixelModificationHistory() {
        return userPixelModificationHistory;
    }

    /**
     * Setter for new list of user pixel modification history
     * @param userPixelModificationHistory new list of user pixel modification history
     */
    public void setUserPixelModificationHistory(List<HistoryEntry> userPixelModificationHistory) {
        this.userPixelModificationHistory = userPixelModificationHistory;
    }
}

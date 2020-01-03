package pl.polsl.gawron.marcel.rplaceData.models;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private String token;
    private LocalDateTime tokenExpiryServerTime;
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
    public User(String name) {
        this();
        setName(name);
    }

    /**
     * Getter for ID of a user
     *
     * @return user id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the user ID
     *
     * @param id new user ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for username
     *
     * @return username
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for username
     *
     * @param name new username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for user password
     *
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for user password
     *
     * @param password new user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for user login hash
     *
     * @return user hash
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for user hash
     *
     * @param token user hash
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Generates user token and sets expiry time
     */
    public void generateUserTokenAndSetExpiryTime() {
        generateToken();
        setExpiryDateWeekFromNow();
    }

    /**
     * Generates random token
     */
    public void generateToken() {
        this.token = RandomStringUtils.randomAlphanumeric(30);
    }

    /**
     * Sets expiry date week from current time
     */
    public void setExpiryDateWeekFromNow() {
        Instant now = Instant.now();
        LocalDateTime expiryDate = LocalDateTime.ofInstant(now, ZoneOffset.UTC).plusWeeks(1);
        this.tokenExpiryServerTime = expiryDate;
    }

    /**
     * Getter for user hash expiry date
     *
     * @return user hash expiry date
     */
    public LocalDateTime getTokenExpiryServerTime() {
        return tokenExpiryServerTime;
    }

    /**
     * Setter for user hash expiry date
     *
     * @param tokenExpiryServerTime user hash expiry date
     */
    public void setTokenExpiryServerTime(LocalDateTime tokenExpiryServerTime) {
        this.tokenExpiryServerTime = tokenExpiryServerTime;
    }

    public boolean isTokenValid(String token){
        // New user - no token
        if(this.token == null){
            return false;
        }
        if(!this.token.equals(token)){
            return false;
        }
        Instant now = Instant.now();
        LocalDateTime currentTime = LocalDateTime.ofInstant(now, ZoneOffset.UTC);
        return currentTime.isBefore(this.tokenExpiryServerTime);
    }

    /**
     * Getter of user pixel modification history
     *
     * @return List of user pixel modification history
     */
    public List<HistoryEntry> getUserPixelModificationHistory() {
        return userPixelModificationHistory;
    }

    /**
     * Setter for new list of user pixel modification history
     *
     * @param userPixelModificationHistory new list of user pixel modification history
     */
    public void setUserPixelModificationHistory(List<HistoryEntry> userPixelModificationHistory) {
        this.userPixelModificationHistory = userPixelModificationHistory;
    }
}

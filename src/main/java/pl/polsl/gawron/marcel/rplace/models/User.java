package pl.polsl.gawron.marcel.rplace.models;

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
    private List<PixelHistory> userHistory;

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
     * Setter for the name property
     * @param name new name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

}

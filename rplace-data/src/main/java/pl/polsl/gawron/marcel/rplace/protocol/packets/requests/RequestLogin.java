package pl.polsl.gawron.marcel.rplace.protocol.packets.requests;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.Serializable;

/**
 * Representation of login request packet
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class RequestLogin implements Serializable {
    private String name;
    private String password;

    /**
     * Getter for a password property
     *
     * @return password of a user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for a password property
     *
     * @param password new user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for a name property
     *
     * @return username
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for a name property
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Serializes classes to JSON
     *
     * @return JSON class representation
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, RequestLogin.class);
    }

    /**
     * Deserializes class from JSON
     *
     * @param json JSON representation
     * @return class instance
     */
    public static RequestLogin deserialize(String json) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(json, RequestLogin.class);
    }
}

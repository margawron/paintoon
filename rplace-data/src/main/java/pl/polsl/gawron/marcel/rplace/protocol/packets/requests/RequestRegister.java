package pl.polsl.gawron.marcel.rplace.protocol.packets.requests;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.Serializable;

/**
 * Representation of register request packet
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class RequestRegister implements Serializable {
    private String name;
    private String password;

    /**
     * Getter for the name of the user
     *
     * @return name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the user
     *
     * @param name new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the password of the user
     *
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password of the user
     *
     * @param password password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Serializes class to JSON
     *
     * @return JSON class representation
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, RequestRegister.class);
    }

    /**
     * Deserializes class from JSON
     *
     * @param json JSON class representation
     * @return class instance
     */
    public static RequestRegister deserialize(String json) throws Exception{
        Gson gson = new Gson();
        return gson.fromJson(json, RequestRegister.class);
    }
}

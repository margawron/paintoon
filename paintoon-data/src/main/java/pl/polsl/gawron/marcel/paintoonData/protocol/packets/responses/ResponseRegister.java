package pl.polsl.gawron.marcel.paintoonData.protocol.packets.responses;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.paintoonData.protocol.Serializable;

/**
 * Representation of register response
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ResponseRegister implements Serializable {
    private boolean registrationSuccessful;

    /**
     * Deserializes class from JSON
     *
     * @param json JSON class representation
     * @return class instance
     */
    public static ResponseRegister deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ResponseRegister.class);
    }

    /**
     * Getter for the flag if registration went successfully
     *
     * @return did registration went successfully
     */
    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }

    /**
     * Setter for the flag if registration went successfully
     *
     * @param registrationSuccessful did registration went successfully
     */
    public void setRegistrationSuccessful(boolean registrationSuccessful) {
        this.registrationSuccessful = registrationSuccessful;
    }

    /**
     * Serializes class to JSON
     *
     * @return JSON class representation
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, ResponseRegister.class);
    }
}

package pl.polsl.gawron.marcel.rplace.protocol.packets.responses;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.Serializable;

/**
 * Representation of register response
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ResponseRegister implements Serializable {
    private boolean isOk;

    /**
     * Did registration went successfully
     *
     * @return did registration went successfully
     */
    public boolean isOk() {
        return isOk;
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

    /**
     * Deserializes class from JSON
     * @param json JSON class representation
     * @return class instance
     */
    public static ResponseRegister deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ResponseRegister.class);
    }
}

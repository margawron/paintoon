package pl.polsl.gawron.marcel.paintoonData.protocol.packets.responses;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.paintoonData.protocol.Serializable;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
public class ResponseInvalidRequest implements Serializable {
    private String message;

    /**
     * Deserializes received class
     *
     * @param json JSON object representation
     * @return class instance
     */
    public static ResponseInvalidRequest deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ResponseInvalidRequest.class);
    }

    /**
     * Getter for error message
     *
     * @return message from server
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for error message
     *
     * @param message message to send
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Serializes class to be sent
     *
     * @return Serialized class as JSON
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, ResponseInvalidRequest.class);
    }
}

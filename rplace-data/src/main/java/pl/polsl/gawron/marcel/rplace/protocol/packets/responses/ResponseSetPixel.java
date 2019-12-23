package pl.polsl.gawron.marcel.rplace.protocol.packets.responses;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.Serializable;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
public class ResponseSetPixel implements Serializable {

    private boolean requestAccepted;
    private String errorMessage;

    /**
     * Getter for checking if request was accepted
     *
     * @return if true request was accepted
     */
    public boolean isRequestAccepted() {
        return requestAccepted;
    }

    /**
     * Setter for setting if request was accepted
     *
     * @param requestAccepted was request proper
     */
    public void setRequestAccepted(boolean requestAccepted) {
        this.requestAccepted = requestAccepted;
    }

    /**
     * Getter for error message
     *
     * @return human readable error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Setter for error message
     *
     * @param errorMessage human readable error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Serializes class to JSON
     *
     * @return JSON class representation
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, ResponseSetPixel.class);
    }

    /**
     * Deserializes class from JSON
     *
     * @param json JSON class representation
     * @return class instance
     */
    public static ResponseSetPixel deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ResponseSetPixel.class);
    }
}

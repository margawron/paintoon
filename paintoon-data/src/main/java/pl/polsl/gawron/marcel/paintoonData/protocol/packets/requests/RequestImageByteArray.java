package pl.polsl.gawron.marcel.paintoonData.protocol.packets.requests;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.paintoonData.protocol.Serializable;

/**
 * Representation of a image byte array request
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class RequestImageByteArray implements Serializable {
    /**
     *  Body not needed
     */

    /**
     * Deserializes received class
     *
     * @param json JSON object representation
     * @return class instance
     */
    public static RequestImageByteArray deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, RequestImageByteArray.class);
    }

    /**
     * Serializes class to be sent
     *
     * @return Serialized class as JSON
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, RequestImageByteArray.class);
    }
}

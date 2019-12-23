package pl.polsl.gawron.marcel.rplace.protocol.packets.requests;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.PacketBody;

/**
 * Representation of a image byte array request
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class RequestImageByteArray implements PacketBody {
    /**
     *  Body not needed
     */

    /**
     * Serializes class to be sent
     * @return Serialized class as JSON
     */
    @Override
    public String serialize(){
        Gson gson = new Gson();
        return gson.toJson(this,RequestImageByteArray.class);
    }

    /**
     * Deserializes received class
     * @param json JSON object representation
     * @return class object
     */
    @Override
    public RequestImageByteArray deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,RequestImageByteArray.class);
    }
}

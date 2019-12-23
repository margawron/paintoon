package pl.polsl.gawron.marcel.rplace.protocol.packets.responses;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.PacketBody;
import pl.polsl.gawron.marcel.rplace.protocol.packets.requests.RequestImageByteArray;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
public class ResponseInvalidRequest implements PacketBody {

    /**
     * Serializes class to be sent
     * @return Serialized class as JSON
     */
    @Override
    public String serialize(){
        Gson gson = new Gson();
        return gson.toJson(this, ResponseInvalidRequest.class);
    }

    /**
     * Deserializes received class
     * @param json JSON object representation
     * @return class object
     */
    @Override
    public ResponseInvalidRequest deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,ResponseInvalidRequest.class);
    }
}

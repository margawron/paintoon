package pl.polsl.gawron.marcel.rplace.protocol.packets.responses;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.Serializable;

import java.time.ZonedDateTime;

/**
 * Representation of login response packet
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ResponseLogin implements Serializable {
    private String hash;
    private ZonedDateTime hashExpiryGMT;

    /**
     * Getter of a hash returned by server
     *
     * @return hash from server
     */
    public String getHash() {
        return hash;
    }

    /**
     * Setter for a hash to be returned to client
     *
     * @param hash hash to send to client
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Getter for expiry date of a hash
     *
     * @return expiry date of hash in GMT
     */
    public ZonedDateTime getHashExpiryGMT() {
        return hashExpiryGMT;
    }

    /**
     * Setter for expiry date of a hash
     *
     * @param hashExpiryGMT expiry date of a hash
     */
    public void setHashExpiryGMT(ZonedDateTime hashExpiryGMT) {
        this.hashExpiryGMT = hashExpiryGMT;
    }

    /**
     * Serializes class to JSON
     *
     * @return JSON class representation
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, ResponseLogin.class);
    }

    /**
     * Deserializes class from JSON
     *
     * @param json JSON class representation
     * @return class instance
     */
    public static ResponseLogin deserialize(String json) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(json, ResponseLogin.class);
    }
}

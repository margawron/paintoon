package pl.polsl.gawron.marcel.rplace.protocol.packets.responses;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.Serializable;

import java.time.LocalDateTime;

/**
 * Representation of login response packet
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ResponseLogin implements Serializable {
    private String token;
    private LocalDateTime tokenExpiryServerTime;

    /**
     * Deserializes class from JSON
     *
     * @param json JSON class representation
     * @return class instance
     */
    public static ResponseLogin deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ResponseLogin.class);
    }

    /**
     * Getter of a hash returned by server
     *
     * @return hash from server
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for a hash to be returned to client
     *
     * @param token hash to send to client
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter for expiry date of a hash
     *
     * @return expiry date of hash in GMT
     */
    public LocalDateTime getTokenExpiryServerTime() {
        return tokenExpiryServerTime;
    }

    /**
     * Setter for expiry date of a hash
     *
     * @param tokenExpiryServerTime expiry date of a hash
     */
    public void setTokenExpiryServerTime(LocalDateTime tokenExpiryServerTime) {
        this.tokenExpiryServerTime = tokenExpiryServerTime;
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
}

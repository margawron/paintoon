package pl.polsl.gawron.marcel.rplace.protocol.packets.requests;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.Serializable;

/**
 * Representation of pixel history request packet
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class RequestPixelHistory implements Serializable {
    private Integer toYoungestPixelNumber;
    private Integer fromOldestPixelNumber;

    /**
     * Setter for which youngest pixel should history start
     *
     * @return youngest pixel position in history registry
     */
    public Integer getToYoungestPixelNumber() {
        return toYoungestPixelNumber;
    }

    /**
     * Getter for which youngest pixel should sent history start
     *
     * @param toYoungestPixelNumber youngest pixel position in history registry
     */
    public void setToYoungestPixelNumber(Integer toYoungestPixelNumber) {
        this.toYoungestPixelNumber = toYoungestPixelNumber;
    }

    /**
     * Getter for which oldest pixel should sent history end
     *
     * @return oldest pixel position in history registry
     */
    public Integer getFromOldestPixelNumber() {
        return fromOldestPixelNumber;
    }

    /**
     * Setter for which oldest pixel should sent history end
     *
     * @param fromOldestPixelNumber oldest pixel position in history registry
     */
    public void setFromOldestPixelNumber(Integer fromOldestPixelNumber) {
        this.fromOldestPixelNumber = fromOldestPixelNumber;
    }

    /**
     * Serialize class object to JSON
     *
     * @return JSON class representation
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, RequestPixelHistory.class);
    }

    /**
     * Deserialize class from JSON
     *
     * @param json JSON class representation
     * @return class instance
     */
    public static RequestPixelHistory deserialize(String json)  {
        Gson gson = new Gson();
        return gson.fromJson(json, RequestPixelHistory.class);
    }
}

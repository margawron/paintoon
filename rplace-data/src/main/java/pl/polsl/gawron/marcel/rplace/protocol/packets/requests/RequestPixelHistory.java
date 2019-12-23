package pl.polsl.gawron.marcel.rplace.protocol.packets.requests;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.protocol.PacketBody;

/**
 * Representation of pixel history request packet
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class RequestPixelHistory implements PacketBody {
    private int fromYoungestPixelNumber;
    private int toOldestPixelNumber;

    /**
     * Setter for which youngest pixel should history start
     * @return youngest pixel position in history registry
     */
    public int getFromYoungestPixelNumber() {
        return fromYoungestPixelNumber;
    }

    /**
     * Getter for which youngest pixel should sent history start
     * @param fromYoungestPixelNumber youngest pixel position in history registry
     */
    public void setFromYoungestPixelNumber(int fromYoungestPixelNumber) {
        this.fromYoungestPixelNumber = fromYoungestPixelNumber;
    }

    /**
     * Getter for which oldest pixel should sent history end
     * @return oldest pixel position in history registry
     */
    public int getToOldestPixelNumber() {
        return toOldestPixelNumber;
    }

    /**
     * Setter for which oldest pixel should sent history end
     * @param toOldestPixelNumber oldest pixel position in history registry
     */
    public void setToOldestPixelNumber(int toOldestPixelNumber) {
        this.toOldestPixelNumber = toOldestPixelNumber;
    }

    /**
     * Serialize class object to JSON
     * @return JSON class representation
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, RequestPixelHistory.class);
    }

    /**
     * Deserialize class from JSON
     * @param json JSON class representation
     * @return class object
     */
    @Override
    public PacketBody deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, RequestPixelHistory.class);
    }
}

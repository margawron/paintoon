package pl.polsl.gawron.marcel.rplace.protocol.packets.responses;

import com.google.gson.Gson;
import pl.polsl.gawron.marcel.rplace.models.HistoryEntry;
import pl.polsl.gawron.marcel.rplace.protocol.Serializable;

import java.util.List;

/**
 * Representation of pixel history response packet
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class ResponsePixelHistory implements Serializable {
    private List<HistoryEntry> historyEntryList;

    /**
     * Deserializes class from JSON
     *
     * @param json JSON class representation
     * @return class instance
     */
    public static ResponsePixelHistory deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ResponsePixelHistory.class);
    }

    /**
     * Getter for history entry list
     *
     * @return list with history entry objects
     */
    public List<HistoryEntry> getHistoryEntryList() {
        return historyEntryList;
    }

    /**
     * Setter for history entry list
     *
     * @param historyEntryList new list of history entry objects
     */
    public void setHistoryEntryList(List<HistoryEntry> historyEntryList) {
        this.historyEntryList = historyEntryList;
    }

    /**
     * Serializes class to JSON
     *
     * @return JSON class representation
     */
    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this, ResponsePixelHistory.class);
    }
}

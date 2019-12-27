package pl.polsl.gawron.marcel.rplaceServer.tcpServer.repositories;

import pl.polsl.gawron.marcel.rplaceData.models.HistoryEntry;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory database of pixel changes
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class HistoryEntryRepository {
    private Map<Integer, HistoryEntry> historyEntries;

    /**
     * Default constructor
     * initializes member objects
     */
    public HistoryEntryRepository() {
        historyEntries = new HashMap();
    }

    /**
     * Adds history entry
     *
     * @param entry history entry to be added
     */
    public void addHistoryEntry(HistoryEntry entry) {
        historyEntries.put(historyEntries.size(), entry);
    }

    /**
     * Searches and returns individual history entry
     *
     * @param id history entry id
     * @return history entry
     */
    public HistoryEntry getHistoryEntry(int id) {
        return historyEntries.get(id);
    }
}

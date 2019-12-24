package pl.polsl.gawron.marcel.rplace.repositories;

import pl.polsl.gawron.marcel.rplace.models.HistoryEntry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * In-memory database of pixel changes
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class HistoryEntryRepository {
    private Map<Integer,HistoryEntry> historyEntries;

    public HistoryEntryRepository(){
        historyEntries = new HashMap();
    }

    public void addHistoryEntry(HistoryEntry entry){
        historyEntries.put(historyEntries.size(), entry);
    }

    public HistoryEntry getHistoryEntry(int id){
       return historyEntries.get(id);
    }
}

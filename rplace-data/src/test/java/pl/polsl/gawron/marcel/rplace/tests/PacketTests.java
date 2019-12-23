package pl.polsl.gawron.marcel.rplace.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.gawron.marcel.rplace.models.Color;
import pl.polsl.gawron.marcel.rplace.models.HistoryEntry;
import pl.polsl.gawron.marcel.rplace.protocol.packets.responses.ResponsePixelHistory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
public class PacketTests {

    /**
     * Contains a lists
     */
    @Test
    public void ResponsePixelHistorySerializationTest(){
        /**
         * Given
         */
        ResponsePixelHistory response = new ResponsePixelHistory();
        List<HistoryEntry> historyEntries = new ArrayList<>();
        // Entry 1
        HistoryEntry entry1 = new HistoryEntry();
        entry1.setX(3); entry1.setY(5); entry1.setColor(new Color(0,0,255));
        Instant now = Instant.now();
        entry1.setTimeOfLastModification(LocalDateTime.ofInstant(now,ZoneId.systemDefault()));
        // Entry 1
        historyEntries.add(entry1);
        // Entry 2
        HistoryEntry entry2 = new HistoryEntry();
        entry2.setX(3); entry2.setY(5); entry2.setColor(new Color(0,0,255));
        Instant now2 = Instant.now();
        entry2.setTimeOfLastModification(LocalDateTime.ofInstant(now2,ZoneId.systemDefault()));
        // Entry 2
        historyEntries.add(entry2);
        // Set list
        response.setHistoryEntryList(historyEntries);
        /**
         * When
         */
        String serialized = response.serialize();
        ResponsePixelHistory deserialized = (ResponsePixelHistory) response.deserialize(serialized);
        /**
         * Then
         */
        assertEquals(response.getHistoryEntryList().get(0).getX(),deserialized.getHistoryEntryList().get(0).getX(),"Elements of array should be equal (x coordinate)");
        assertEquals(response.getHistoryEntryList().get(0).getY(),deserialized.getHistoryEntryList().get(0).getY(),"Elements of array should be equal (y coordinate)");
        assertEquals(response.getHistoryEntryList().get(0).getUserWhoModifiedPixel(),deserialized.getHistoryEntryList().get(0).getUserWhoModifiedPixel(),"Elements of array should be equal (user ID coordinate)");
    }
}

package pl.polsl.gawron.marcel.rplace.tests;

import org.junit.jupiter.api.Test;
import pl.polsl.gawron.marcel.rplaceData.models.Color;
import pl.polsl.gawron.marcel.rplaceData.models.HistoryEntry;
import pl.polsl.gawron.marcel.rplaceData.protocol.packets.requests.RequestImageByteArray;
import pl.polsl.gawron.marcel.rplaceData.protocol.packets.responses.ResponsePixelHistory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
public class PacketTests {

    @Test
    public void RequestImageByteArraySerialization() {
        /**
         * Given
         */
        RequestImageByteArray request = new RequestImageByteArray();
        /**
         * When
         */
        String serialized = request.serialize();
        // Then
        assertEquals("{}", serialized, "Serialized RequestImageByteArray should be equal to \"{}\"");
    }

    /**
     * Contains a lists
     */
    @Test
    public void ResponsePixelHistorySerializationTest() {
        /**
         * Given
         */
        ResponsePixelHistory response = new ResponsePixelHistory();
        List<HistoryEntry> historyEntries = new ArrayList<>();
        // Entry 1
        HistoryEntry entry1 = new HistoryEntry();
        entry1.setX(3);
        entry1.setY(5);
        entry1.setColor(new Color(0, 0, 255));
        Instant now = Instant.now();
        entry1.setTimeOfModification(LocalDateTime.ofInstant(now, ZoneId.systemDefault()));
        // Entry 1
        historyEntries.add(entry1);
        // Entry 2
        HistoryEntry entry2 = new HistoryEntry();
        entry2.setX(3);
        entry2.setY(5);
        entry2.setColor(new Color(0, 0, 255));
        Instant now2 = Instant.now();
        entry2.setTimeOfModification(LocalDateTime.ofInstant(now2, ZoneId.systemDefault()));
        // Entry 2
        historyEntries.add(entry2);
        // Set list
        response.setHistoryEntryList(historyEntries);
        /**
         * When
         */
        String serialized = response.serialize();
        ResponsePixelHistory deserialized = null;
        try {
            deserialized = (ResponsePixelHistory) response.deserialize(serialized);
        } catch (Exception e) {
        }
        /**
         * Then
         */
        assertNotNull(deserialized, "Deserialized data should not be empty");
        assertEquals(response.getHistoryEntryList().get(0).getX(), deserialized.getHistoryEntryList().get(0).getX(), "Elements of array should be equal (x coordinate)");
        assertEquals(response.getHistoryEntryList().get(0).getY(), deserialized.getHistoryEntryList().get(0).getY(), "Elements of array should be equal (y coordinate)");
        assertEquals(response.getHistoryEntryList().get(0).getUserWhoModifiedPixel(), deserialized.getHistoryEntryList().get(0).getUserWhoModifiedPixel(), "Elements of array should be equal (user ID coordinate)");
    }
}

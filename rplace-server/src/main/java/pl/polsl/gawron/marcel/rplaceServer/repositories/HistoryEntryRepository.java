package pl.polsl.gawron.marcel.rplaceServer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.gawron.marcel.rplaceData.models.HistoryEntry;


import java.util.List;

/**
 * In-memory database of pixel changes
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Repository
public interface HistoryEntryRepository extends CrudRepository<HistoryEntry, Long> {

    /*
    public List<HistoryEntry> getLatestPixelChangeForEachPixel() {
    public List<HistoryEntry> getHistoryEntries() {
    public long getCountOfUserPixelChanges(long userId){
    public HistoryEntry getHistoryEntry(int id) {
    public void addHistoryEntry(HistoryEntry entry) {

*/

    /**
     * Get count of all user pixel changes
     * @param userId user id
     * @return count of pixel user changes
     */
    @Query("SELECT COUNT(h) FROM HistoryEntry h WHERE h.userWhoModifiedPixel.id = ?1")
    Long getCountOfUserPixelChanges(long userId);

    //SELECT * from historyEntries h1 INNER JOIN " +
    //                    "(SELECT x,y, MAX(id) as highest from historyEntries GROUP BY x, y ) AS h2 " +
    //                    "ON h1.x = h2.x AND h1.y = h2.y AND h1.id = h2.highest

    /**
     * Get list of last change for each pixel
     * @return List of last changes for each pixel
     */
    @Query("Select h1, h2.x, h2.y, Max(h2.id) from HistoryEntry h1 inner join HistoryEntry h2 ON h1.x = h2.x WHERE h1.x = h2.x AND h1.y = h2.y AND h1.id = h2.id GROUP BY h2.x, h2.y")
    List<HistoryEntry> getLatestPixelChangeForEachPixel();

    /**
     * Get all history pixel changes
     * @return all history pixel changes
     */
    List<HistoryEntry> findAll();
}

package pl.polsl.gawron.marcel.rplaceServer.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.polsl.gawron.marcel.rplaceData.models.HistoryEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory database of pixel changes
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Component
public class HistoryEntryRepository {

    private JdbcTemplate template;
    private UserRepository userRepository;

    /**
     * Creates table for history entries
     *
     * @param template       jdbc connection template - filled by spring context
     * @param userRepository user database connection class - filled by spring context
     */
    public HistoryEntryRepository(JdbcTemplate template, UserRepository userRepository) {
        this.template = template;
        this.userRepository = userRepository;
//        template.execute("DROP TABLE IF EXISTS historyEntries");
//        template.execute("CREATE TABLE historyEntries(" +
//                "id INTEGER AUTO_INCREMENT PRIMARY KEY," +
//                "x INTEGER NOT NULL," +
//                "y INTEGER NOT NULL," +
//                "red INTEGER NOT NULL," +
//                "green INTEGER NOT NULL," +
//                "blue INTEGER NOT NULL," +
//                "userId INTEGER NOT NULL ," +
//                "timeOfModification INTEGER NOT NULL," +
//                "FOREIGN KEY (userId) REFERENCES users(id) " +
//                "ON DELETE SET NULL)");
    }

    /**
     * Adds history entry to database
     *
     * @param entry history entry to be added
     */
    public void addHistoryEntry(HistoryEntry entry) {
        template.update("INSERT INTO historyEntries(x,y,red,green,blue,userId,timeOfModification)" +
                        " VALUES (?,?,?,?,?,?,?)",
                entry.getX(), entry.getY(),
                entry.getRedComponent(), entry.getGreenComponent(), entry.getGreenComponent(),
                entry.getUserWhoModifiedPixel().getId(), entry.getTimeOfModification().atZone(ZoneId.systemDefault()).toEpochSecond());
    }

    /**
     * Searches and returns individual history entry
     *
     * @param id history entry id
     * @return history entry
     */
    public HistoryEntry getHistoryEntry(int id) {
        HistoryEntry historyEntry = null;
        try {
            historyEntry = template.queryForObject("SELECT * FROM historyEntries WHERE id = ?", new Object[]{id}, new HistoryEntryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            historyEntry = null;
        }
        return historyEntry;
    }

    /**
     * Query for number of pixel changes of a user
     *
     * @param userId user id
     * @return count of bitmap changes
     */
    public long getCountOfUserPixelChanges(long userId) {
        Long userPixelChangesCount;
        try {
            userPixelChangesCount = template.queryForObject("SELECT COUNT(*) FROM historyEntries WHERE userId = ?", new Object[]{userId}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            userPixelChangesCount = 0L;
        }
        if (userPixelChangesCount == null) {
            userPixelChangesCount = 0L;
        }
        return userPixelChangesCount;
    }

    /**
     * Gets all of the history entries in the database
     *
     * @return List of history entries
     */
    public List<HistoryEntry> getHistoryEntries() {
        List<HistoryEntry> historyEntries = null;
        try {
            historyEntries = template.query("SELECT * FROM historyEntries", new HistoryEntryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            historyEntries = null;
        }
        return historyEntries;
    }

    public List<HistoryEntry> getLatestPixelChangeForEachPixel() {
        List<HistoryEntry> historyEntries = null;
        try {
            historyEntries = template.query("SELECT * from historyEntries h1 INNER JOIN " +
                    "(SELECT x,y, MAX(id) as highest from historyEntries GROUP BY x, y ) AS h2 " +
                    "ON h1.x = h2.x AND h1.y = h2.y AND h1.id = h2.highest", new HistoryEntryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            historyEntries = new ArrayList(0);
        }
        return historyEntries;
    }

    /**
     * Class implementing interface of a row mapper
     * for HistoryEntry class
     *
     * @author Marcel Gawron
     * @version 1.0
     */
    public final class HistoryEntryRowMapper implements RowMapper<HistoryEntry> {
        @Override
        public HistoryEntry mapRow(ResultSet resultSet, int i) throws SQLException {
            HistoryEntry historyEntry = new HistoryEntry();
            historyEntry.setId(resultSet.getLong("id"));
            historyEntry.setX(resultSet.getInt("x"));
            historyEntry.setY(resultSet.getInt("y"));
            historyEntry.setRedComponent(resultSet.getInt("red"));
            historyEntry.setGreenComponent(resultSet.getInt("green"));
            historyEntry.setBlueComponent(resultSet.getInt("blue"));
            historyEntry.setUserWhoModifiedPixel(userRepository.findUser(resultSet.getInt("userId")));
            historyEntry.setTimeOfModification(LocalDateTime.ofEpochSecond(resultSet.getInt("timeOfModification"), 0, ZoneOffset.UTC));
            return historyEntry;
        }
    }
}
/*
    public List<HistoryEntry> getLatestPixelChangeForEachPixel() {
    public List<HistoryEntry> getHistoryEntries() {
    public long getCountOfUserPixelChanges(long userId){
    public HistoryEntry getHistoryEntry(int id) {
    public void addHistoryEntry(HistoryEntry entry) {

*/
package pl.polsl.gawron.marcel.rplaceServer.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.polsl.gawron.marcel.rplaceData.models.Color;
import pl.polsl.gawron.marcel.rplaceData.models.HistoryEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
     * Default constructor
     * initializes member objects
     */
    public HistoryEntryRepository(JdbcTemplate template, UserRepository userRepository) {
        this.template = template;
        this.userRepository = userRepository;
        template.execute("DROP TABLE IF EXISTS historyEntries");
        template.execute("CREATE TABLE historyEntries(" +
                "id INTEGER AUTO_INCREMENT PRIMARY KEY," +
                "x INTEGER NOT NULL," +
                "y INTEGER NOT NULL," +
                "red INTEGER NOT NULL," +
                "green INTEGER NOT NULL," +
                "blue INTEGER NOT NULL," +
                "userId INTEGER NOT NULL ," +
                "timeOfModification INTEGER NOT NULL," +
                "FOREIGN KEY (userId) REFERENCES users(id))");
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
                entry.getColor().getRed(), entry.getColor().getGreen(), entry.getColor().getBlue(),
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

    public List<HistoryEntry> getHistoryEntries() {
        List<HistoryEntry> historyEntries = null;
        try {
            historyEntries = template.query("SELECT * FROM historyEntries", new HistoryEntryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            historyEntries = null;
        }
        return historyEntries;
    }

    public final class HistoryEntryRowMapper implements RowMapper<HistoryEntry> {
        @Override
        public HistoryEntry mapRow(ResultSet resultSet, int i) throws SQLException {
            HistoryEntry historyEntry = new HistoryEntry();
            historyEntry.setId(resultSet.getLong("id"));
            historyEntry.setX(resultSet.getInt("x"));
            historyEntry.setY(resultSet.getInt("y"));
            historyEntry.setColor(new Color(
                    resultSet.getInt("red"),
                    resultSet.getInt("green"),
                    resultSet.getInt("blue")
            ));
            historyEntry.setUserWhoModifiedPixel(userRepository.findUser(resultSet.getInt("userId")));
            historyEntry.setTimeOfModification(LocalDateTime.ofEpochSecond(resultSet.getInt("timeOfModification"), 0, ZoneOffset.UTC));
            return historyEntry;
        }
    }
}

package pl.polsl.gawron.marcel.rplaceServer.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.polsl.gawron.marcel.rplaceData.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;


/**
 * Database handler for registered users
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Component
public class UserRepository {
    private JdbcTemplate template;

    /**
     * Default constructor
     *
     * @param template jdbc connection template - filled by spring context
     */
    public UserRepository(JdbcTemplate template) {
        this.template = template;
//        template.execute("DROP TABLE IF EXISTS users");
//        template.execute("CREATE TABLE users(" +
//                "id INTEGER AUTO_INCREMENT PRIMARY KEY," +
//                "name TEXT NOT NULL," +
//                "password TEXT NOT NULL," +
//                "tokenExpiryServerTime INTEGER," +
//                "token TEXT)");
    }

    /**
     * Adds user to database
     *
     * @param name     user name
     * @param password user password
     * @return true if user was added to database
     */
    public boolean addUser(String name, String password) {
        if (findUser(name) != null) {
            return false;
        } else {
            template.update("INSERT INTO users(name, password) VALUES (?,?)", name, password);
            return true;
        }
    }

    /**
     * Searches user in the database
     *
     * @param name username
     * @return null if user was not found, user instance otherwise
     */
    public User findUser(String name) {
        User toReturn;
        try {
            toReturn = template.queryForObject("Select * from users where name = ?", new Object[]{name}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            toReturn = null;
        }
        return toReturn;
    }

    /**
     * Query for user with specific id
     *
     * @param id id of a user to query for
     * @return User data from database
     */
    public User findUser(int id) {
        User toReturn;
        try {
            toReturn = template.queryForObject("Select * from users where id = ?", new Object[]{id}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            toReturn = null;
        }
        return toReturn;
    }

    /**
     * Updates user entry in the database
     *
     * @param user user entry
     * @return true if update passed, false otherwise
     */
    public boolean updateUser(User user) {
        try {
            template.update("UPDATE users SET name = ?, password = ?, token = ?, tokenExpiryServerTime = ? WHERE id = ?",
                    user.getName(), user.getPassword(), user.getToken(), user.getTokenExpiryServerTime().atZone(ZoneId.systemDefault()).toEpochSecond(), user.getId());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    /**
     * Returns list of users from database
     *
     * @return list of users
     */
    public List<User> getAllUsers() {
        List<User> toReturn = null;
        try {
            toReturn = template.query("SELECT * FROM users", new UserRowMapper());
        } catch (EmptyResultDataAccessException ignored) {
        }
        return toReturn;
    }

    /**
     * Class implementing interaface of a row mapper
     * for User class
     *
     * @author Marcel Gawron
     * @version 1.0
     */
    public final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setToken(resultSet.getString("token"));
            user.setTokenExpiryServerTime(LocalDateTime.ofEpochSecond(resultSet.getInt("tokenExpiryServerTime"), 0, ZoneOffset.UTC));
            return user;
        }
    }
}


package pl.polsl.gawron.marcel.paintoonServer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.gawron.marcel.paintoonData.models.User;

import java.util.List;


/**
 * Database handler for registered users
 *
 * @author Marcel Gawron
 * @version 1.0
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Get user by user name
     *
     * @param name user name
     * @return user instance from database
     */
    User findUserByName(String name);

    /**
     * Get all users from database
     *
     * @return list of all users
     */
    List<User> findAll();

    /**
     * Save user to database
     *
     * @param object user object
     * @return saved user object
     */
    User save(User object);
}


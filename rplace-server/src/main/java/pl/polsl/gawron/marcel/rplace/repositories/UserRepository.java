package pl.polsl.gawron.marcel.rplace.repositories;

import pl.polsl.gawron.marcel.rplace.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * In-memory database of registered users
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class UserRepository {
    private Set<User> users;

    /**
     * Default constructor
     * initializes Set of users
     */
    public UserRepository(){
        users = new HashSet<>();
    }

    /**
     * Adds user to database
     * @param name user name
     * @param password user password
     * @return true if user was added to database
     */
    public boolean addUser(String name, String password){
        if(findUser(name) != null){
            return false;
        } else {
            User newUser = new User();
            newUser.setId((long)users.size());
            newUser.setName(name);
            newUser.setPassword(password);
            newUser.setToken(null);
            newUser.setTokenExpiryServerTime(null);
            newUser.setUserPixelModificationHistory(new ArrayList<>(10));
            users.add(newUser);
            return true;
        }
    }

    public User findUser(String name){
        User soughtUser = null;
        for(User user : users){
            if(user.getName().equals(name)){
                soughtUser = user;
                break;
            }
        }
        return soughtUser;
    }
}

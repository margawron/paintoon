package pl.polsl.gawron.marcel.rplaceServer.models;

import pl.polsl.gawron.marcel.rplaceData.models.User;

/**
 * User response model class
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class UserResponseModel {
    private long id;
    private String name;

    /**
     * Constructor for UserResponseModel
     * @param user user from which to copy the data
     */
    public UserResponseModel(User user){
        this.id = user.getId();
        this.name = user.getName();
    }

    /**
     * Getter for the id field
     * @return id field
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the id field
     * @param id new id field
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the name field
     * @return name field
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name field
     * @param name new name field
     */
    public void setName(String name) {
        this.name = name;
    }
}

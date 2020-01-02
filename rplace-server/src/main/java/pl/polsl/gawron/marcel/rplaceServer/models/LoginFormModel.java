package pl.polsl.gawron.marcel.rplaceServer.models;

/**
 * Data model for the login form
 * @author Marcel Gawron
 * @version 1.0
 */
public class LoginFormModel {
    private String name;
    private String password;

    /**
     * Getter for the username
     * @return login form entered username
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the username in login form
     * @param name login form username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the password in login form
     * @return login form entered password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password in login form
     * @param password login form password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

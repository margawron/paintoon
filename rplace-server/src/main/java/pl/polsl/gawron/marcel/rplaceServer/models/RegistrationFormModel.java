package pl.polsl.gawron.marcel.rplaceServer.models;

/**
 * Data model for registration form
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class RegistrationFormModel {
    private String name;
    private String passwordFirst;
    private String passwordSecond;

    /**
     * Getter for the name
     *
     * @return name from model
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name
     *
     * @param name set user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the first password input value
     *
     * @return password from first input
     */
    public String getPasswordFirst() {
        return passwordFirst;
    }

    /**
     * Setter for the first password
     *
     * @param passwordFirst set first input password
     */
    public void setPasswordFirst(String passwordFirst) {
        this.passwordFirst = passwordFirst;
    }

    /**
     * Getter for the second password input value
     *
     * @return password from second input
     */
    public String getPasswordSecond() {
        return passwordSecond;
    }

    /**
     * Setter for the second password
     *
     * @param passwordSecond set second input password
     */
    public void setPasswordSecond(String passwordSecond) {
        this.passwordSecond = passwordSecond;
    }
}

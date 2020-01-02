package pl.polsl.gawron.marcel.rplaceServer.models;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
public class RegistrationFormModel {
    private String name;
    private String passwordFirst;
    private String passwordSecond;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordFirst() {
        return passwordFirst;
    }

    public void setPasswordFirst(String passwordFirst) {
        this.passwordFirst = passwordFirst;
    }

    public String getPasswordSecond() {
        return passwordSecond;
    }

    public void setPasswordSecond(String passwordSecond) {
        this.passwordSecond = passwordSecond;
    }
}

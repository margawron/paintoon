package pl.polsl.gawron.marcel.rplace.utils;

import pl.polsl.gawron.marcel.rplace.models.User;

/**
 * Constants used in program
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class Constants {
    // Made as in
    // private java.util.Calendar calendar;
    public enum Strings {
        PORT_NUMBER_COMMAND_FLAG("-p"),
        TEST_USER_NAME("test_user");

        private String name;

        Strings(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    public static class OBJECTS {
        public static final User TEST_USER = new User(Strings.TEST_USER_NAME.getName());
    }

}

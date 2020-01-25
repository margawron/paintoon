package pl.polsl.gawron.marcel.paintoonData.utils;

/**
 * pl.polsl.gawron.marcel.rplace.utils.Constants used in program
 *
 * @author Marcel Gawron
 * @version 1.0
 */
public class Constants {
    public static final int MINUTES_TO_WAIT_AFTER_SET_PIXEL = 10;
    public static final int MAX_PIXEL_HISTORY_LENGTH = 20;

    // Made as in
    // private java.util.Calendar calendar;
    public enum Strings {
        PORT_NUMBER_COMMAND_FLAG("-p"),
        TEST_USER_NAME("test_user");

        private String value;

        Strings(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


}
